package com.patientregistration.system.service.Impl;

import com.patientregistration.system.controller.UserController;
import com.patientregistration.system.domain.Person;
import com.patientregistration.system.domain.User;
import com.patientregistration.system.exception.DataConflictException;
import com.patientregistration.system.exception.DataNotAcceptableException;
import com.patientregistration.system.exception.ResourceNotFoundException;
import com.patientregistration.system.repository.PersonRepository;
import com.patientregistration.system.repository.UserRepository;
import com.patientregistration.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserRepository userRepository;
    private PersonRepository personRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Set<User> findUsersByIdDoctor(Long idDoctor) {
        List<User> patients = userRepository.findAllByIdDoctor(idDoctor);
        return new HashSet<>(patients);
    }

    @Override
    public List<User> findUsersByRole(String role) {
        return userRepository.findAllByRole(role);
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

    private User findUserByPesel(String pesel) {
        return userRepository.findByPesel(pesel)
                .orElse(null);
    }

    @Override
    public User create(User user) {
        if (findUserByUsername(user.getUsername()) != null) {
            logger.error("Username already exist " + user.getUsername());
            throw new DataConflictException("User with username " + user.getUsername() + "already exist ");
        }
        if (findUserByPesel(user.getPesel()) != null) {
            logger.error("Pesel already exist " + user.getPesel());
            throw new DataNotAcceptableException("User with pesel " + user.getPesel() + "already exist ");
        }

        Person person = personRepository.findPersonByFirstNameAndLastNameAndPesel(user.getFirstName(), user.getLastName(), user.getPesel());
        if (person == null) {
            logger.error("Person not exist in external database " + user.getUsername());
            throw new ResourceNotFoundException("Person with username " + user.getUsername() + " not exist in external database");
        }

        if (person.getSpecialization() != null) {
            user.setRole("DOCTOR");
            user.setSpecialization(person.getSpecialization());
            System.out.println("To lekarz");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return save(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

}
