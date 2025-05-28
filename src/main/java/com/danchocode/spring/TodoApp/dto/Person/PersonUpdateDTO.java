package com.danchocode.spring.TodoApp.dto.Person;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PersonUpdateDTO {

    @Size(min = 3, max = 100, message = "Username должен быть от 3 до 100 символов")
    private String username;

    @Size(min = 6, message = "Пароль должен быть минимум 6 символов")
    private String password;

    private Integer yearOfBirth;

    private String role; // например, "ROLE_ADMIN" или "ROLE_USER"

    // Геттеры и сеттеры или Lombok @Data
}