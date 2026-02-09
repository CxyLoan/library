package ru.coxey.library.audit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.coxey.library.dto.ReaderDto;
import ru.coxey.library.entity.Book;
import ru.coxey.library.logging.MaskingService;
import ru.coxey.library.mapper.BookMapper;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuditService {

    private final BookMapper bookMapper;
    private final MaskingService maskingService;

    public void createReader(ReaderDto dto) {
        log.info("AUDIT CRITICAL. Время: {}. Был создан читатель: {}", LocalDateTime.now(), maskingService.masking(dto));
    }

    public void getBookForReader(Book book, Long id) {
        final var dto = bookMapper.modelToDto(book);
        log.info("AUDIT CRITICAL. Время: {}. Была выдана книга: {}, читателю с id: {}", LocalDateTime.now(), dto, id);
    }

    public void returnBook(Book book) {
        final var dto = bookMapper.modelToDto(book);
        log.info("AUDIT CRITICAL. Время: {}. Была возвращена книга: {}", LocalDateTime.now(), dto);
    }
}
