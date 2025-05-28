package com.danchocode.spring.TodoApp.controllers;

import com.danchocode.spring.TodoApp.dto.Person.PersonCreateDTO;
import com.danchocode.spring.TodoApp.dto.Person.PersonDTO;
import com.danchocode.spring.TodoApp.dto.Person.PersonUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.danchocode.spring.TodoApp.services.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllUsers() {
        List<PersonDTO> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getUserById(@PathVariable int id) {
        PersonDTO user = adminService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> createUser(@RequestBody @Valid PersonCreateDTO dto) {
        PersonDTO createdUser = adminService.createUser(dto);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updateUser(@PathVariable int id, @RequestBody PersonUpdateDTO dto) {
        PersonDTO updatedUser = adminService.updateUser(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok().body("Пользователь удалён");
    }
}