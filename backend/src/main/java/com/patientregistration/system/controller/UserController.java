package com.patientregistration.system.controller;

import com.patientregistration.system.domain.User;
import com.patientregistration.system.exception.ResourceNotFoundException;
import com.patientregistration.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        if (userService.findUserByUsername(newUser.getUsername()) != null) {
            logger.error("username Already exist " + newUser.getUsername());
            return new ResponseEntity<>(
                    new ResourceNotFoundException("User with username " + newUser.getUsername() + "already exist "),
                    HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.saveOrUpdate(newUser), HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public Principal user(Principal principal) {
        logger.info("user logged " + principal);
        return principal;
    }

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") Long idUser) {
        return userService.findUserById(idUser);
    }

    @GetMapping("/username")
    public User getUserByUsername(@RequestParam String username) {
        return userService.findUserByUsername(username);
    }

    @GetMapping("/role")
    public List<User> getUsersByRole(@RequestParam String role) {
        return userService.findUsersByRole(role);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long idUser) {
        userService.delete(idUser);

        return ResponseEntity.ok().build();
    }

}
