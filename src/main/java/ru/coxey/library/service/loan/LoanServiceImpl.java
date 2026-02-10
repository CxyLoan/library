package ru.coxey.library.service.loan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.coxey.library.audit.AuditService;
import ru.coxey.library.dto.BookLoanDto;
import ru.coxey.library.dto.BookLoanRequest;
import ru.coxey.library.entity.Book;
import ru.coxey.library.entity.BookLoan;
import ru.coxey.library.entity.Reader;
import ru.coxey.library.enums.LoanStatus;
import ru.coxey.library.exceptions.*;
import ru.coxey.library.mapper.BookLoanMapper;
import ru.coxey.library.repository.BookRepository;
import ru.coxey.library.repository.LoanRepository;
import ru.coxey.library.repository.ReaderRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private static final Integer MAX_BOOK_LOAN_DAYS = 7;

    private final LoanRepository loanRepository;
    private final BookLoanMapper bookLoanMapper;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final AuditService auditService;

    @Override
    public BookLoanDto getBookForReader(BookLoanRequest request) {
        validateReturnDate(request.getReturnDate());
        final var book = getBookById(request.getBookId());
        final var reader = getReaderById(request.getReaderId());
        validateEqualLoan(reader, book);
        validateAvailableBookCopies(book);
        updateBookCopies(book);
        auditService.getBookForReader(book, request.getReaderId());
        return bookLoanMapper.toBookLoanDto(loanRepository.save(createBookLoan(request.getReturnDate(), book, reader)));
    }

    @Override
    public List<BookLoanDto> getHistoryByReaderId(Long id) {
        return bookLoanMapper.toBookLoanDtoList(loanRepository.findByReaderId(id));
    }

    @Override
    public List<BookLoanDto> getOverdueBooks() {
        return bookLoanMapper.toBookLoanDtoList(getOverdueBooks(loanRepository.findAll()));
    }

    @Override
    public void returnBook(Long id) {
        final var loan = getLoanById(id);
        validateLoanStatus(loan);
        final var book = loan.getBook();
        updateBook(book);
        auditService.returnBook(book);
        updateLoan(loan);
    }

    private List<BookLoan> getOverdueBooks(List<BookLoan> loans) {
        final List<BookLoan> result = new ArrayList<>();
        for (var loan : loans) {
            if (loan.getDueDate().isBefore(LocalDate.now()) && Objects.isNull(loan.getReturnDate())) {
                loan.setStatus(LoanStatus.OVERDUE);
                result.add(loan);
            }
        }
        return result;
    }

    private BookLoan getLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException(String.format("Заема с id [%s] не существует", id)));
    }

    private void validateLoanStatus(BookLoan loan) {
        if (LoanStatus.RETURNED == loan.getStatus()) {
            throw new BookReturnedException("Данная книга уже была возвращена");
        }
    }

    private void updateBook(Book book) {
        final var currentAvailableCopies = book.getAvailableCopies() + 1;
        if (currentAvailableCopies > book.getTotalCopies()) {
            throw new IncorrectAvailableCopiesCountException("Доступных копий не может быть больше, чем общего количества копий");
        }
        book.setAvailableCopies(currentAvailableCopies);
    }

    private void updateLoan(BookLoan loan) {
        loan.setStatus(LoanStatus.RETURNED);
        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);
    }

    private void validateReturnDate(LocalDate date) {
        LocalDate today = LocalDate.now();
        LocalDate maxDueDate = today.plusDays(MAX_BOOK_LOAN_DAYS);
        if (date.isBefore(today)) {
            throw new IncorrectDateException(String.format("Дата возврата должна быть не позже - %s", today));
        }
        if (date.isAfter(maxDueDate)) {
            throw new IncorrectDateException("Дата возврата не должна быть больше, чем 7 дней с момента взятия книги");
        }
    }

    private Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(String.format("Книги с id {%s} не существует", id)));
    }

    private Reader getReaderById(Long id) {
        return readerRepository.findById(id)
                .orElseThrow(() -> new ReaderNotFoundException(String.format("Читатель с id {%s} не найден", id)));
    }

    private void validateAvailableBookCopies(Book book) {
        if (book.getAvailableCopies() == 0) {
            throw new IncorrectAvailableCopiesCountException(String.format("Свободных экземпляров книг {%s} не осталось", book.getBookName()));
        }
    }

    private void updateBookCopies(Book book) {
        book.setAvailableCopies(book.getAvailableCopies() - 1);
    }

    private BookLoan createBookLoan(LocalDate returnDate, Book book, Reader reader) {
        final var loan = new BookLoan();
        loan.setBook(book);
        loan.setReader(reader);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(returnDate);
        loan.setStatus(LoanStatus.ISSUED);
        return loan;
    }

    private void validateEqualLoan(Reader reader, Book book) {
        final List<BookLoan> result = loanRepository.findByReaderAndBookAndStatusIn(reader, book, List.of(LoanStatus.ISSUED, LoanStatus.OVERDUE));
        if (!result.isEmpty()) {
            throw new BookIssuedException("Книга уже была выдана данному читателю");
        }
    }
}
