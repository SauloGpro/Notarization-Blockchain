package com.example.NotarizationBlockchain.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // Permite construir objetos de User fácilmente
public class User {
    private Long id;              // ID del usuario
    private String username;      // Nombre de usuario
    private String password;      // Contraseña (codificada)
    private String email;         // Email del usuario
}


