package ru.coxey.library.service.reader;

import ru.coxey.library.dto.ReaderDto;

import java.util.List;

public interface ReaderService {

    List<ReaderDto> getAllReaders();
    ReaderDto getReaderById(Long id);
    ReaderDto createReader(ReaderDto readerDto);
    ReaderDto updateReader(Long id, ReaderDto readerDto);
    void deleteReader(Long id);
}
