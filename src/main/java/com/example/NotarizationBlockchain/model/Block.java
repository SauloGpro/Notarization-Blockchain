package com.example.NotarizationBlockchain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "block")
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "block_index") // Cambiar el nombre de la columna para evitar conflictos
    private int index;

    private long timestamp;

    @Column(name = "previous_hash") // Hash del bloque anterior
    private String previousHash;

    private String hash;

    @Lob
    private String data; // Datos registrados (puede ser texto o un hash de archivo)

    @Column(name = "document_type") // Tipo de documento registrado (e.g., contrato, factura, etc.)
    private String documentType;

    @Column(name = "owner") // Propietario del bloque/documento
    private String owner;

    // Constructor para crear nuevos bloques con índice explícito
    public Block(int index, String previousHash, String data) {
        this.index = index;
        this.timestamp = System.currentTimeMillis();
        this.previousHash = previousHash;
        this.data = data;
        this.documentType = "default"; // Tipo de documento por defecto
        this.owner = "unknown";       // Propietario por defecto
        this.hash = calculateHash();
    }

    // Constructor para crear bloques con todos los campos
    public Block(String previousHash, String data, String documentType, String owner) {
        this.timestamp = System.currentTimeMillis();
        this.previousHash = previousHash;
        this.data = data;
        this.documentType = documentType;
        this.owner = owner;
        this.hash = calculateHash();
    }

    // Método para calcular el hash del bloque
    public String calculateHash() {
        String input = index + previousHash + timestamp + data + documentType + owner;
        return applySHA256(input);
    }

    // Implementación de SHA-256
    public static String applySHA256(String input) {
        try {
            var digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
            var hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}









