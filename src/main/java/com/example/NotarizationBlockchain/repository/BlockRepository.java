package com.example.NotarizationBlockchain.repository;


import com.example.NotarizationBlockchain.repository.entities.BlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BlockRepository extends JpaRepository<BlockEntity, Integer> {
    // Puedes agregar métodos personalizados si los necesitas
}

