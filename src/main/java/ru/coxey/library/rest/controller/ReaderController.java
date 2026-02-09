package ru.coxey.library.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.coxey.library.dto.ReaderDto;
import ru.coxey.library.rest.handler.reader.ReaderExceptionHandler;
import ru.coxey.library.service.reader.ReaderService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/readers/")
@RequiredArgsConstructor
@ReaderExceptionHandler
public class ReaderController {

    private final ReaderService service;

    @GetMapping()
    public ResponseEntity<List<ReaderDto>> getAllReaders() {
        return ResponseEntity.ok(service.getAllReaders());
    }

    @GetMapping("{id}")
    public ResponseEntity<ReaderDto> getReaderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getReaderById(id));
    }

    @PostMapping()
    public ResponseEntity<ReaderDto> createReader(@RequestBody @Validated ReaderDto readerDto) {
        return ResponseEntity.created(URI.create("/api/v1/readers/")).body(service.createReader(readerDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<ReaderDto> updateReader(@PathVariable("id") Long id,
                                                  @RequestBody @Validated ReaderDto readerDto) {
        return ResponseEntity.ok(service.updateReader(id, readerDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteReader(@PathVariable("id") Long id) {
        service.deleteReader(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
