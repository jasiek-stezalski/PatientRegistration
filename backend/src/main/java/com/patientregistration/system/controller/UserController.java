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
public class UserController {

    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("account/register")
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        if (userService.findUserByUsername(newUser.getUsername()) != null) {
            logger.error("username Already exist " + newUser.getUsername());
            return new ResponseEntity<>(
                    new ResourceNotFoundException("user with username " + newUser.getUsername() + "already exist "),
                    HttpStatus.CONFLICT);
        }
        newUser.setRole("USER");
        newUser.setLastName("patient2");
        return new ResponseEntity<>(userService.saveOrUpdate(newUser), HttpStatus.CREATED);
    }

    // this is the login api/service
    @GetMapping("account/login")
    public Principal user(Principal principal) {
        logger.info("user logged " + principal);
        return principal;
    }


    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(value = "id") Long idUser) {
        return userService.findUserById(idUser);
    }

    @GetMapping("/username")
    public User getUserByUsername(@RequestParam String username) {
        return userService.findUserByUsername(username);
    }

//    @PostMapping("/users")
//    public User createUser(@Valid @RequestBody User user) {
//        return userService.saveOrUpdate(user);
//    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long idUser) {
        userService.delete(idUser);

        return ResponseEntity.ok().build();
    }

}
