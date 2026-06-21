package com.clinica.reservas.controller;

import com.clinica.reservas.dto.JwtResponse;
import com.clinica.reservas.dto.LoginRequest;
import com.clinica.reservas.entity.Usuario;
import com.clinica.reservas.repository.UsuarioRepository;
import com.clinica.reservas.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 1. INICIO DE SESIÓN NORMAL
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Autenticar con Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generar el token
        String jwt = jwtUtil.generateJwtToken(authentication); // o generateJwtToken(authentication) dependiendo de tu JwtUtil

        // Extraer los roles
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), roles));
    }

    // 2. NUEVO MÉTODO: RESTABLECER CONTRASEÑA
    @PostMapping("/restablecer-password")
    public ResponseEntity<?> restablecerPassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String nuevaPassword = request.get("nuevaPassword");

        if (nuevaPassword == null || nuevaPassword.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("{\"mensaje\": \"La nueva contraseña no puede estar vacía.\"}");
        }

        // Buscamos al usuario por su DNI (username)
        Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);

        if (usuario == null) {
            return ResponseEntity.badRequest().body("{\"mensaje\": \"No existe una cuenta con este número de documento.\"}");
        }

        // Actualizamos la contraseña y guardamos en la base de datos
        usuario.setPasswordHash(nuevaPassword);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok().body("{\"mensaje\": \"Contraseña actualizada exitosamente\"}");
    }
}