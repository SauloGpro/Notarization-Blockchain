package com.example.NotarizationBlockchain.controller;

import com.example.NotarizationBlockchain.mapper.AppMapper;
import com.example.NotarizationBlockchain.controller.dto.request.LoginRequestDTO;
import com.example.NotarizationBlockchain.controller.dto.request.RegisterRequestDTO;
import com.example.NotarizationBlockchain.controller.dto.response.UserResponseDTO;
import com.example.NotarizationBlockchain.service.impl.JwtService;
import com.example.NotarizationBlockchain.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AppMapper appMapper;

    /**
     * Endpoint para registrar un usuario
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO requestDTO) {
        try {
            // Mapeo manual para evitar errores
            var user = appMapper.registerRequestDTOToUser(requestDTO);

            // Registro en el servicio
            var registeredUser = userService.register(user);

            // Respuesta en formato DTO
            return ResponseEntity.ok(appMapper.userToUserResponseDTO(registeredUser));
        } catch (IllegalArgumentException ex) {
            // Manejo de errores de validación
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


    /**
     * Endpoint para iniciar sesión
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO requestDTO) {
        // Autenticar el usuario
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getUsername(),
                        requestDTO.getPassword()
                )
        );

        // Cargar detalles del usuario y generar un token
        UserDetails userDetails = userService.loadUserByUsername(requestDTO.getUsername());
        String token = jwtService.generateToken(userDetails);

        // Devolver el token en la respuesta
        return ResponseEntity.ok(Map.of("token", token));
    }
}





