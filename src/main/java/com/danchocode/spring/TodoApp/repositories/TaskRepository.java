package com.danchocode.spring.TodoApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.danchocode.spring.TodoApp.models.Task;
import com.danchocode.spring.TodoApp.models.TaskStatus;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByOwnerUsername(String username);

    List<Task> findAllByOwnerUsernameAndStatus(String username, TaskStatus status);

}
