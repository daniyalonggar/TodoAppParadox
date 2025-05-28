package com.danchocode.spring.TodoApp.dto.Task;

import com.danchocode.spring.TodoApp.models.TaskStatus;
import lombok.Data;

@Data
public class TaskUpdateDTO {
    private String title;
    private String description;
    private TaskStatus status;
}