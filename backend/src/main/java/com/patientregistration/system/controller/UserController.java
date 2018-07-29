package com.patientregistration.system.controller;

import com.patientregistration.system.domain.User;
import com.patientregistration.system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") Long idUser) {
        return userService.findUserById(idUser);
    }

    @GetMapping("/users/username")
    public User getUserByUsername(@RequestParam String username) {
        return userService.findUserByUsername(username);
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long idUser) {
        userService.delete(idUser);

        return ResponseEntity.ok().build();
    }

}
