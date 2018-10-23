package com.patientregistration.system.service.Impl;

import com.patientregistration.system.domain.User;
import com.patientregistration.system.exception.ResourceNotFoundException;
import com.patientregistration.system.repository.UserRepository;
import com.patientregistration.system.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Set<User> findUsersByIdDoctor(Long idDoctor) {
        List<User> patients = userRepository.findAllByIdDoctor(idDoctor);
        return new HashSet<>(patients);
    }

    @Override
    public User findUserById(Long idUser) {
        return userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException(idUser.toString()));
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }

    @Override
    public User saveOrUpdate(User user) {
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void delete(Long idUser) {
        userRepository.deleteById(idUser);
    }

    @Override
    public List<User> findUsersByRole(String role) {
        return userRepository.findAllByRole(role);
    }

}
