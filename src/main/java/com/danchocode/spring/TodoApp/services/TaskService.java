package com.danchocode.spring.TodoApp.services;

import com.danchocode.spring.TodoApp.dto.Task.TaskDTO;
import com.danchocode.spring.TodoApp.dto.Task.TaskResponseDTO;
import com.danchocode.spring.TodoApp.dto.Task.TaskUpdateDTO;
import com.danchocode.spring.TodoApp.models.Person;
import com.danchocode.spring.TodoApp.models.Task;
import com.danchocode.spring.TodoApp.models.TaskStatus;
import com.danchocode.spring.TodoApp.repositories.PeopleRepository;
import com.danchocode.spring.TodoApp.repositories.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final PeopleRepository peopleRepository;
    private final ModelMapper modelMapper;

    public TaskService(TaskRepository taskRepository, PeopleRepository peopleRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.peopleRepository = peopleRepository;
        this.modelMapper = modelMapper;
    }

    public TaskResponseDTO createTask(TaskDTO dto, String username) {
        Person owner = peopleRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(TaskStatus.TODO)
                .owner(owner)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        taskRepository.save(task);
        return modelMapper.map(task, TaskResponseDTO.class);
    }

    public List<TaskResponseDTO> getTasks(String username, Optional<TaskStatus> statusFilter) {
        List<Task> tasks = statusFilter
                .map(status -> taskRepository.findAllByOwnerUsernameAndStatus(username, status))
                .orElseGet(() -> taskRepository.findAllByOwnerUsername(username));

        return tasks.stream()
                .map(task -> modelMapper.map(task, TaskResponseDTO.class))
                .collect(Collectors.toList());
    }

    public TaskResponseDTO updateTask(int taskId, TaskUpdateDTO dto, String username) throws AccessDeniedException {
        Task task = getOwnedTaskOrThrow(taskId, username);

        if (dto.getTitle() != null) task.setTitle(dto.getTitle());
        if (dto.getDescription() != null) task.setDescription(dto.getDescription());
        if (dto.getStatus() != null) task.setStatus(dto.getStatus());

        task.setUpdatedAt(LocalDateTime.now());
        taskRepository.save(task);

        return modelMapper.map(task, TaskResponseDTO.class);
    }

    public void deleteTask(int taskId, String username) throws AccessDeniedException {
        Task task = getOwnedTaskOrThrow(taskId, username);
        taskRepository.delete(task);
    }

    private Task getOwnedTaskOrThrow(int taskId, String username) throws AccessDeniedException {
        return taskRepository.findById(taskId)
                .filter(task -> task.getOwner().getUsername().equals(username))
                .orElseThrow(() -> new AccessDeniedException("Not your task"));
    }
}
