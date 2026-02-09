package ru.coxey.library.service.book;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.coxey.library.dto.BookDto;
import ru.coxey.library.entity.Book;
import ru.coxey.library.exceptions.BookNotFoundException;
import ru.coxey.library.exceptions.InvalidBookCopiesException;
import ru.coxey.library.exceptions.IsbnNotUniqueException;
import ru.coxey.library.mapper.BookMapper;
import ru.coxey.library.repository.BookRepository;
import ru.coxey.library.utils.BookSpecificationUtils;

import java.util.List;
import java.util.Objects;

import static ru.coxey.library.utils.MapperUtils.map;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final BookMapper mapper;

    @Override
    public List<BookDto> getAllBooks(String year, String yearSort, Boolean available, String availableSort) {
        if (Objects.isNull(year) && Objects.isNull(available)) {
            return mapper.toListDto(repository.findAll());
        }
        final var specification = getSpecificationForFindAllBooks(year, available);
        final var sort = getSortForFindAllBooks(year, yearSort, available, availableSort);
        return mapper.toListDto(repository.findAll(specification, sort));
    }

    @Override
    public BookDto getBookById(Long id) {
        return mapper.modelToDto(findBookById(id));
    }

    @Override
    @Transactional
    public BookDto createBook(BookDto bookDto) {
        isUniqueIsbn(bookDto.getIsbn());
        return mapper.modelToDto(repository.save(
                mapper.dtoToModel(bookDto)));
    }

    @Override
    @Transactional
    public BookDto updateBook(Long id, BookDto bookDto) {
        isUniqueIsbn(bookDto.getIsbn());
        final var bookFromDb = findBookById(id);
        validateCopies(bookFromDb, bookDto);
        mapping(bookDto, bookFromDb);
        repository.save(bookFromDb);
        return mapper.modelToDto(bookFromDb);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return;
        }
        throw new BookNotFoundException(String.format("Книги с id {%s} не существует", id));
    }

    @Override
    public List<BookDto> searchBookByParams(String bookName, String author, String isbn) {
        return mapper.toListDto(repository.findAll(getSpecificationForSearchBookByParams(bookName, author, isbn)));
    }

    private Book findBookById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(String.format("Книги с id {%s} не существует", id)));
    }

    private void isUniqueIsbn(String isbn) {
        if (repository.existsBookByIsbn(isbn)) {
            throw new IsbnNotUniqueException(String.format("Книга с ISBN - {%s} уже существует", isbn));
        }
    }

    private void validateCopies(Book bookFromDb, BookDto bookDto) {
        if (bookFromDb.getTotalCopies() - bookFromDb.getAvailableCopies() != bookDto.getTotalCopies() - bookDto.getAvailableCopies()) {
            throw new InvalidBookCopiesException(String.format("Разница между общим количеством копий и доступных копий должно быть равно %d", bookFromDb.getTotalCopies() - bookFromDb.getAvailableCopies()));
        }
    }

    private void mapping(BookDto bookDto, Book bookFromDb) {
        bookFromDb.setBookName(map(bookFromDb::getBookName, bookDto::getBookName));
        bookFromDb.setAuthor(map(bookFromDb::getAuthor, bookDto::getAuthor));
        bookFromDb.setIsbn(map(bookFromDb::getIsbn, bookDto::getIsbn));
        bookFromDb.setPublicationYear(map(bookFromDb::getPublicationYear, bookDto::getPublicationYear));
        bookFromDb.setTotalCopies(map(bookFromDb::getTotalCopies, bookDto::getTotalCopies));
        bookFromDb.setAvailableCopies(map(bookFromDb::getAvailableCopies, bookDto::getAvailableCopies));
    }

    private Specification<Book> getSpecificationForSearchBookByParams(String bookName, String author, String isbn) {
        Specification<Book> result = Specification.unrestricted();
        if (Objects.nonNull(bookName)) {
            result = result.and(BookSpecificationUtils.hasBookName(bookName));
        }
        if (Objects.nonNull(author)) {
            result = result.and(BookSpecificationUtils.hasAuthor(author));
        }
        if (Objects.nonNull(isbn)) {
            result = result.and(BookSpecificationUtils.hasIsbn(isbn));
        }
        return result;
    }

    private Specification<Book> getSpecificationForFindAllBooks(String year, Boolean available) {
        Specification<Book> result = Specification.unrestricted();
        if (Objects.nonNull(year)) {
            result = result.and(BookSpecificationUtils.hasYear(year));
        }
        if (Objects.nonNull(available)) {
            result = result.and(BookSpecificationUtils.hasAvailable(available));
        }
        return result;
    }

    private Sort getSortForFindAllBooks(String year, String yearSort, Boolean available, String availableSort) {
        var result = Sort.unsorted();
        if (Objects.nonNull(year)) {
            result = result.and(Sort.by(getDirection(yearSort), "publicationYear"));
        }
        if (Objects.nonNull(available)) {
            result = result.and(Sort.by(getDirection(availableSort), "availableCopies"));
        }
        return result;
    }

    private Sort.Direction getDirection(String value) {
        try {
            return Sort.Direction.fromOptionalString(value).get();
        } catch (Exception e) {
            return Sort.Direction.ASC;
        }
    }
}
