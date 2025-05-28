package com.danchocode.spring.TodoApp.services;

import org.springframework.stereotype.Service;
import com.danchocode.spring.TodoApp.models.Person;
import com.danchocode.spring.TodoApp.repositories.PeopleRepository;

import java.util.Optional;

@Service
public class PersonService {

    private final PeopleRepository peopleRepository;

    public PersonService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public Optional<Person> findByUsername(String username) {
        return peopleRepository.findByUsername(username);
    }
}