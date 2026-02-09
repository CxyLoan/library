package ru.coxey.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.coxey.library.entity.Book;
import ru.coxey.library.entity.BookLoan;
import ru.coxey.library.entity.Reader;
import ru.coxey.library.enums.LoanStatus;

import java.util.List;

public interface LoanRepository extends JpaRepository<BookLoan, Long> {

    List<BookLoan> findByReaderId(Long readerId);
    List<BookLoan> findByReaderAndBookAndStatusIn(Reader reader, Book book, List<LoanStatus> statuses);
}
