package com.example.NotarizationBlockchain.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blocks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String hash; // Este campo no puede ser nulo

    @Column(name = "previous_hash", nullable = false)
    private String previousHash; // Asegúrate de que tampoco sea nulo

    @Column(nullable = false)
    private String data;

    @Column(name = "document_type")
    private String documentType;

    @Column(nullable = false)
    private String owner;

    @Column(nullable = false)
    private long timestamp;
}


