package com.example.NotarizationBlockchain.controller;

import com.example.NotarizationBlockchain.model.User;
import com.example.NotarizationBlockchain.service.JwtService;
import com.example.NotarizationBlockchain.service.UserService;
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
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        System.out.println("Intentando autenticar al usuario: " + username);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        UserDetails userDetails = userService.loadUserByUsername(username);
        String token = jwtService.generateToken(userDetails);

        System.out.println("Autenticación exitosa para el usuario: " + username);

        return ResponseEntity.ok(Map.of("token", token));
    }

}



