package ru.coxey.library.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookLoanRequest {

    @NotNull(message = "id книги не должно быть пустым")
    private Long bookId;
    @NotNull(message = "id читателя не должно быть пустым")
    private Long readerId;
    @NotNull(message = "Дата возврата не должна быть пустой")
    private LocalDate returnDate;
}
