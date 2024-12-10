package com.example.NotarizationBlockchain.repository;

import com.example.NotarizationBlockchain.model.User;
import com.example.NotarizationBlockchain.repository.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
