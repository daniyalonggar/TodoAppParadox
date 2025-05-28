package com.danchocode.spring.TodoApp.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.danchocode.spring.TodoApp.dto.Task.TaskDTO;
import com.danchocode.spring.TodoApp.dto.Task.TaskResponseDTO;
import com.danchocode.spring.TodoApp.dto.Task.TaskUpdateDTO;
import com.danchocode.spring.TodoApp.models.TaskStatus;
import com.danchocode.spring.TodoApp.security.PersonDetails;
import com.danchocode.spring.TodoApp.services.TaskService;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/todo/tasks")
public class TaskController {

    private final TaskService taskService;


    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/addTask")
    public ResponseEntity<?> createTask(@RequestBody @Valid TaskDTO taskDTO,
                                        BindingResult bindingResult,
                                        @AuthenticationPrincipal PersonDetails personDetails) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("errors", bindingResult.getFieldErrors()));
        }
        if (personDetails == null) {
            return ResponseEntity.status(401).body("Пользователь не аутентифицирован");
        }

        TaskResponseDTO responseDTO = taskService.createTask(taskDTO, personDetails.getUsername());
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<?> getTasks(@RequestParam Optional<String> status,
                                      @AuthenticationPrincipal PersonDetails personDetails) {
        if (personDetails == null) {
            return ResponseEntity.status(401).body("Пользователь не аутентифицирован");
        }

        Optional<TaskStatus> statusFilter = status.map(s -> {
            try {
                return TaskStatus.valueOf(s.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        });

        if (statusFilter.isPresent() && statusFilter.get() == null) {
            return ResponseEntity.badRequest().body("Некорректный статус задачи");
        }

        List<TaskResponseDTO> tasks = taskService.getTasks(personDetails.getUsername(), statusFilter);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable int taskId,
                                        @RequestBody TaskUpdateDTO taskUpdateDTO,
                                        @AuthenticationPrincipal PersonDetails personDetails) {
        if (personDetails == null) {
            return ResponseEntity.status(401).body("Пользователь не аутентифицирован");
        }
        try {
            TaskResponseDTO updatedTask = taskService.updateTask(taskId, taskUpdateDTO, personDetails.getUsername());
            return ResponseEntity.ok(updatedTask);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(403).body("Нет доступа к задаче");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка обновления задачи");
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable int taskId,
                                        @AuthenticationPrincipal PersonDetails personDetails) {
        if (personDetails == null) {
            return ResponseEntity.status(401).body("Пользователь не аутентифицирован");
        }
        try {
            taskService.deleteTask(taskId, personDetails.getUsername());
            return ResponseEntity.ok("Задача удалена");
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(403).body("Нет доступа к задаче");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка удаления задачи");
        }
    }
}