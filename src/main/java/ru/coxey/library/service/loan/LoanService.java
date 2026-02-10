package ru.coxey.library.service.loan;

import org.springframework.dao.TransientDataAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;
import ru.coxey.library.dto.BookLoanDto;
import ru.coxey.library.dto.BookLoanRequest;

import java.util.List;

public interface LoanService {

    @Retryable(retryFor = {TransientDataAccessException.class},
                maxAttempts = 4,
                backoff = @Backoff(delay = 1500, multiplier = 2.0))
    @Transactional
    BookLoanDto getBookForReader(BookLoanRequest request);
    List<BookLoanDto> getHistoryByReaderId(Long id);
    @Transactional
    List<BookLoanDto> getOverdueBooks();
    @Transactional
    void returnBook(Long id);
}
