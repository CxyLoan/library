package ru.coxey.library.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.coxey.library.dto.BookDto;
import ru.coxey.library.rest.handler.book.BookExceptionHandler;
import ru.coxey.library.service.book.BookService;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@BookExceptionHandler
@RequestMapping("/api/v1/books/")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping()
    public ResponseEntity<List<BookDto>> getAllBooks(@RequestParam(required = false) String year,
                                                     @RequestParam(required = false) String yearSort,
                                                     @RequestParam(required = false) Boolean available,
                                                     @RequestParam(required = false) String availableSort) {
        return ResponseEntity.ok(bookService.getAllBooks(year, yearSort, available, availableSort));
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping()
    public ResponseEntity<BookDto> createBook(@RequestBody @Validated BookDto bookDto) {
        return ResponseEntity.created(URI.create("/api/v1/books")).body(bookService.createBook(bookDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id,
                                             @RequestBody @Validated BookDto bookDto) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDto));
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity<Object> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("search")
    public ResponseEntity<List<BookDto>> searchBookByParams(@RequestParam(required = false) String bookName,
                                                      @RequestParam(required = false) String author,
                                                      @RequestParam(required = false) String isbn) {
        final List<BookDto> result = bookService.searchBookByParams(bookName, author, isbn);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }
}
