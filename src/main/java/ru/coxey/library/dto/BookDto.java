package ru.coxey.library.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BookDto {

    @NotNull(message = "Название книги не должно быть пустым")
    private String bookName;

    @NotNull(message = "Имя автора не должно быть пустым")
    private String author;

    @NotNull(message = "ISBN не должен быть пустым")
    @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISBN должен состоять из 10 или 13 цифр")
    private String isbn;

    @NotNull(message = "Дата публикации не должна быть пустой")
    private String publicationYear;

    @NotNull(message = "Количество копий не должно быть пустым")
    private Integer totalCopies;

    @NotNull(message = "Количество доступных копий не должно быть пустым")
    private Integer availableCopies;
}
