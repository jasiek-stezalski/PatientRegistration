package com.patientregistration.system.repository;

import com.patientregistration.system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query(nativeQuery = true,
            value = "SELECT u.* " +
                    "FROM users u " +
                    "JOIN visit v ON u.id = v.id_user " +
                    "JOIN visit_model vm ON v.id_visit_model = vm.id " +
                    "WHERE vm.id_doctor =:idDoctor")
    List<User> findAllByIdDoctor(@Param("idDoctor") Long idDoctor);

}
