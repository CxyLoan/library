package ru.coxey.library.service.book;

import ru.coxey.library.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks(String year, String yearSort, Boolean available, String availableSort);
    BookDto getBookById(Long id);
    BookDto createBook(BookDto bookDto);
    BookDto updateBook(Long id, BookDto bookDto);
    void deleteBook(Long id);
    List<BookDto> searchBookByParams(String bookName, String author, String isbn);
}
