package com.danchocode.spring.TodoApp.dto.Person;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PersonCreateDTO {

    @NotBlank(message = "Username не должен быть пустым")
    @Size(min = 3, max = 100, message = "Username должен быть от 3 до 100 символов")
    private String username;

    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 6, message = "Пароль должен быть минимум 6 символов")
    private String password;

    private Integer yearOfBirth;

    // Опционально, можно не передавать — тогда в сервисе по умолчанию "ROLE_USER"
    private String role;

    // Геттеры и сеттеры или Lombok @Data
}