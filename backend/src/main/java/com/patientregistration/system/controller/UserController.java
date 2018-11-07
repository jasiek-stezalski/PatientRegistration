package com.patientregistration.system.controller;

import com.patientregistration.system.domain.User;
import com.patientregistration.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public Principal user(Principal principal) {
        logger.info("User logged " + principal);
        return principal;
    }

    @GetMapping("/doctor/{idDoctor}")
    public Set<User> getUsersByIdDoctor(@PathVariable Long idDoctor) {
        return userService.findUsersByIdDoctor(idDoctor);
    }

    @GetMapping("/role/{role}")
    public List<User> getUsersByRole(@PathVariable String role) {
        return userService.findUsersByRole(role);
    }

    @GetMapping("/{idUser}")
    public User getUserById(@PathVariable Long idUser) {
        return userService.findUserById(idUser);
    }

    @PostMapping("/")
    public User createUser(@RequestBody User newUser) {
        return userService.create(newUser);
    }

}
