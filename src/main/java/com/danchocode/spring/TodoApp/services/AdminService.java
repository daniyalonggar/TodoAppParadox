package com.danchocode.spring.TodoApp.services;

import com.danchocode.spring.TodoApp.dto.Person.PersonCreateDTO;
import com.danchocode.spring.TodoApp.dto.Person.PersonDTO;
import com.danchocode.spring.TodoApp.dto.Person.PersonUpdateDTO;
import com.danchocode.spring.TodoApp.models.Person;
import com.danchocode.spring.TodoApp.repositories.PeopleRepository;
import com.danchocode.spring.TodoApp.util.PersonNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final PeopleRepository peopleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminService(PeopleRepository peopleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<PersonDTO> getAllUsers() {
        return peopleRepository.findAll().stream()
                .map(user -> modelMapper.map(user, PersonDTO.class))
                .collect(Collectors.toList());
    }

    public PersonDTO getUserById(int id) {
        Person person = peopleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(person, PersonDTO.class);
    }

    public PersonDTO createUser(PersonCreateDTO dto) {
        Person person = modelMapper.map(dto, Person.class);
        person.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Если роль не указана, ставим по умолчанию
        if (person.getRole() == null || person.getRole().isBlank()) {
            person.setRole("ROLE_USER");
        }

        peopleRepository.save(person);
        return modelMapper.map(person, PersonDTO.class);
    }

    public PersonDTO updateUser(int id, PersonUpdateDTO dto) {
        Person person = peopleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.getUsername() != null) person.setUsername(dto.getUsername());
        if (dto.getYearOfBirth() != null) person.setYearOfBirth(dto.getYearOfBirth());
        if (dto.getRole() != null && !dto.getRole().isBlank()) person.setRole(dto.getRole());
        if (dto.getPassword() != null) person.setPassword(passwordEncoder.encode(dto.getPassword()));

        peopleRepository.save(person);
        return modelMapper.map(person, PersonDTO.class);
    }

    public void deleteUser(int id) {
        Person person = peopleRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Пользователь не найден"));
        peopleRepository.delete(person);
    }
}
