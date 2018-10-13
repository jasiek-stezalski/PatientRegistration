package com.patientregistration.system.service;

import com.patientregistration.system.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    List<User> findAllUsers();

    Set<User> findUsersByIdDoctor(Long idDoctor);

    User findUserById(Long idUser);

    User findUserByUsername(String username);

    User saveOrUpdate(User user);

    void delete(Long idUser);

    List<User> findUsersByRole(String role);

}
