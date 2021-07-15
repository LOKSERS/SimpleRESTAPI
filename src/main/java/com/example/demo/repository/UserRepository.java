package com.example.demo.repository;

import com.example.demo.Entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<user, Long> {

    Optional<user> findByUsername(String username);

    Optional<user> findUserByEmail(String email);

    Optional<user> findUserById(Long id);
}