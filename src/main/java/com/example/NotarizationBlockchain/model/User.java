package com.example.NotarizationBlockchain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Si deseas añadir roles, puedes devolverlos aquí.
        // Por ahora devolvemos una colección vacía.
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Cambia si tienes lógica para cuentas expiradas
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Cambia si tienes lógica para cuentas bloqueadas
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Cambia si tienes lógica para credenciales expiradas
    }

    @Override
    public boolean isEnabled() {
        return true; // Cambia si tienes lógica para usuarios deshabilitados
    }
}

