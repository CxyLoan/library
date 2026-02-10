package ru.coxey.library.service.reader;

import org.springframework.transaction.annotation.Transactional;
import ru.coxey.library.dto.ReaderDto;

import java.util.List;

public interface ReaderService {

    List<ReaderDto> getAllReaders();
    ReaderDto getReaderById(Long id);
    @Transactional
    ReaderDto createReader(ReaderDto readerDto);
    @Transactional
    ReaderDto updateReader(Long id, ReaderDto readerDto);
    @Transactional
    void deleteReader(Long id);
}
