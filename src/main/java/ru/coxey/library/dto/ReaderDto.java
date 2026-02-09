package ru.coxey.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ReaderDto {

    @NotNull(message = "Имя должно быть заполнено")
    private String firstName;

    @NotNull(message = "Фамилия должна быть заполнена")
    private String lastName;

    @NotNull(message = "Адрес электронный почты должен быть заполнен")
    @Email(message = "Адрес электронный почты должен быть в формате test123@test.ru")
    private String email;

    @NotNull(message = "Номер телефона должен быть указан")
    @Pattern(message = "Номер телефона должен быть указан в формате 88001112233", regexp = "^8\\d{10}$")
    private String phoneNumber;
}
