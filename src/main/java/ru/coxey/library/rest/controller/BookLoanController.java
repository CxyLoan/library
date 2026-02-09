package ru.coxey.library.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.coxey.library.dto.BookLoanDto;
import ru.coxey.library.dto.BookLoanRequest;
import ru.coxey.library.rest.handler.loan.BookLoanExceptionHandler;
import ru.coxey.library.service.loan.LoanService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans/")
@RequiredArgsConstructor
@BookLoanExceptionHandler
public class BookLoanController {

    private final LoanService service;

    @PostMapping
    public ResponseEntity<BookLoanDto> getBookForReader(@RequestBody @Validated BookLoanRequest request) {
        return ResponseEntity.ok(service.getBookForReader(request));
    }

    @PutMapping("{id}/return")
    public ResponseEntity<Object> returnBook(@PathVariable("id") Long id) {
        service.returnBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("reader/{readerId}")
    public ResponseEntity<List<BookLoanDto>> getHistoryByReaderId(@PathVariable("readerId") Long id) {
        final List<BookLoanDto> result = service.getHistoryByReaderId(id);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("overdue")
    public ResponseEntity<List<BookLoanDto>> getOverdueBooks() {
        return ResponseEntity.ok(service.getOverdueBooks());
    }
}
