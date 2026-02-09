package ru.coxey.library.dto;

import lombok.Data;
import ru.coxey.library.enums.LoanStatus;

import java.time.LocalDate;

@Data
public class BookLoanDto {

    private String bookName;
    private String author;
    private String publicationYear;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private LoanStatus status;
}
