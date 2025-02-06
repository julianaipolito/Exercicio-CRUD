package com.joaoju.Users_API.controllers;

import java.util.UUID;

import java.util.Optional;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joaoju.Users_API.dtos.UsersDTO;
import com.joaoju.Users_API.models.UsersModel;
import com.joaoju.Users_API.repositories.UsersRepository;

import jakarta.validation.Valid;

@RestController
public class UsersController {

    @Autowired
    UsersRepository usersRepository;

    @PostMapping("/users")
    public ResponseEntity<UsersModel> saveUser(@RequestBody @Valid UsersDTO usersDto){
        var usersModel = new UsersModel();
        BeanUtils.copyProperties(usersDto, usersModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(usersRepository.save(usersModel));
    }
    
     @GetMapping("/users")
    public ResponseEntity<List<UsersModel>> getAllUsers() {
        List<UsersModel> users = usersRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/users/search")
    public ResponseEntity<List<UsersModel>> searchUsersByName(@RequestParam String nome) {
        List<UsersModel> users = usersRepository.findByNomeContainingIgnoreCase(nome);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UsersModel> editarCliente(@PathVariable UUID id, @RequestBody UsersModel updatedUser) {
        Optional<UsersModel> existingUser = usersRepository.findById(id);
        if (existingUser.isPresent()) {
            UsersModel user = existingUser.get();
            user.setNome(updatedUser.getNome());
            user.setEmail(updatedUser.getEmail());
            user.setTelefone(updatedUser.getTelefone());
            usersRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        Optional<UsersModel> existingUser = usersRepository.findById(id);
        if (existingUser.isPresent()) {
            usersRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }
    }
}
