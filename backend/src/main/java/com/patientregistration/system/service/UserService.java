package com.patientregistration.system.service;

import com.patientregistration.system.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    Set<User> findUsersByIdDoctor(Long idDoctor);

    List<User> findUsersByRole(String role);

    User findUserById(Long idUser);

    User findUserByUsername(String username);

    User create(User user);
}
