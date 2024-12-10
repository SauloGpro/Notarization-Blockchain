package com.example.NotarizationBlockchain.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // Permite crear objetos Block con un builder
public class Block {
    private int index;             // Índice del bloque en la blockchain
    private long timestamp;        // Marca de tiempo del bloque
    private String previousHash;   // Hash del bloque anterior
    private String hash;           // Hash de este bloque
    private String data;           // Datos contenidos en el bloque
    private String documentType;   // Tipo de documento (ej. contrato, factura, etc.)
    private String owner;          // Propietario del documento

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











