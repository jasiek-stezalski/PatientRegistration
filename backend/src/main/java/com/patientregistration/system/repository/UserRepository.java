package com.patientregistration.system.repository;

import com.patientregistration.system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
