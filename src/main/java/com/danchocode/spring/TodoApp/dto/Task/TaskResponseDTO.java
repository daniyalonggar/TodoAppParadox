package com.danchocode.spring.TodoApp.dto.Task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.danchocode.spring.TodoApp.models.TaskStatus;

import java.time.LocalDateTime;

@Data

@AllArgsConstructor
@Builder
public class TaskResponseDTO {
    private Integer id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer ownerId;
    public TaskResponseDTO() {
    }
}
