package ru.coxey.library.service.book;

import org.springframework.transaction.annotation.Transactional;
import ru.coxey.library.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks(String year, String yearSort, Boolean available, String availableSort);
    BookDto getBookById(Long id);
    @Transactional
    BookDto createBook(BookDto bookDto);
    @Transactional
    BookDto updateBook(Long id, BookDto bookDto);
    @Transactional
    void deleteBook(Long id);
    List<BookDto> searchBookByParams(String bookName, String author, String isbn);
}
