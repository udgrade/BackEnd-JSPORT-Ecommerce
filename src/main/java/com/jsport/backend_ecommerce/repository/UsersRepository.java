package com.jsport.backend_ecommerce.repository;

import com.jsport.backend_ecommerce.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    // Este método es el que usará el CustomUserDetailsService
    // para encontrar al administrador de JS-PORT durante el login.
    Optional<Users> findByEmail(String email);
}