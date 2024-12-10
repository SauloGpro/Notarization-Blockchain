package com.example.NotarizationBlockchain.service.impl;

import com.example.NotarizationBlockchain.mapper.AppMapper;
import com.example.NotarizationBlockchain.model.User;
import com.example.NotarizationBlockchain.repository.UserRepository;
import com.example.NotarizationBlockchain.repository.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppMapper appMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // El repositorio devuelve un UserEntity
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Convertimos UserEntity a UserDetails de Spring Security
        return org.springframework.security.core.userdetails.User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(Collections.emptyList()) // Puedes añadir roles aquí si lo necesitas
                .build();
    }

    public User register(User user) {
        // Validación adicional en el servicio
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía");
        }

        // Convertimos el modelo User a UserEntity
        UserEntity userEntity = appMapper.userToUserEntity(user);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword())); // Codifica la contraseña

        // Guardamos en la base de datos y retornamos un User de dominio
        UserEntity savedEntity = userRepository.save(userEntity);
        return appMapper.userEntityToUser(savedEntity);
    }
}







