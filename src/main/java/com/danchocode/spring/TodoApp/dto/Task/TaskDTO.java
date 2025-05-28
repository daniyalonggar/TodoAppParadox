package com.danchocode.spring.TodoApp.dto.Task;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskDTO {
    @NotBlank(message = "Title обязательно")
    private String title;
    private String description;
}