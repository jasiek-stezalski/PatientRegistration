package com.patientregistration.system.repository;

import com.patientregistration.system.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotEmpty;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findPersonByFirstNameAndLastNameAndPesel(@NotEmpty String firstName, @NotEmpty String lastName, String pesel);

    Person findPersonByPesel(String pesel);
}
