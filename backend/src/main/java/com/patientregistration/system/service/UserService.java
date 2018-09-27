package com.patientregistration.system.service;

import com.patientregistration.system.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAllUsers();

    User findUserById(Long idUser);

    User findUserByUsername(String username);

    User saveOrUpdate(User user);

    void delete(Long idUser);

}
