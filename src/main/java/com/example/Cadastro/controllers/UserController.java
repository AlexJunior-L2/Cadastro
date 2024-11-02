package com.example.Cadastro.controllers;

import com.example.Cadastro.models.User;
import com.example.Cadastro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> allUsers = repository.findAll();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping(value = "/findbyid")
    public ResponseEntity<Optional<User>> findUserById(@RequestParam UUID id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/findbyname")
    public ResponseEntity<Optional<User>> findUserByName(@RequestParam String name) {
        Optional<User> user = repository.findByName(name);
        if (user.isPresent()) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<User> postUser(@RequestBody User user){
        User newUser = repository.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity deleteUser(@RequestParam UUID id) {
        Optional<User> userToDelete = repository.findById(id);
        if (userToDelete.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestParam UUID id, @RequestBody User user){
        boolean userExist = repository.existsById(id);
        if(userExist){
            Optional<User> actualUserOptional = repository.findById(id);

            User actualUser = actualUserOptional.get();
            actualUser.setName(user.getName());
            actualUser.setActive(user.isActive());
            actualUser.setLogin(user.getLogin());
            actualUser.setPassword(user.getPassword());

            System.out.println(actualUser.getId());

            repository.save(actualUser);
            return new ResponseEntity<>(actualUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
