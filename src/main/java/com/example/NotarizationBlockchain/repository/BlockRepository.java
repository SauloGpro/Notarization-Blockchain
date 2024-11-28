package com.example.NotarizationBlockchain.repository;

import com.example.NotarizationBlockchain.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BlockRepository extends JpaRepository<Block, Integer> {
    // Puedes agregar métodos personalizados si los necesitas
}

