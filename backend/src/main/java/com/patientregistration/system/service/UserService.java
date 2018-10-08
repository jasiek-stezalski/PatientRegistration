package com.patientregistration.system.service;

import com.patientregistration.system.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.IOException;
import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAllUsers();

    User findUserById(Long idUser);

    User findUserByUsername(String username);

    User saveOrUpdate(User user);

    void delete(Long idUser);

    List<User> findUsersByRole(String role);

    void sendSimpleMessage() throws MessagingException, IOException;
}
