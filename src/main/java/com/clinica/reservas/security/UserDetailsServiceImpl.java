package com.clinica.reservas.security;

import com.clinica.reservas.entity.Usuario;
import com.clinica.reservas.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca al usuario en la BD
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con DNI: " + username));

        // Transforma el rol de la BD al formato que requiere Spring Security (ej. "ROLE_ADMIN")
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombreRol());

        // Devuelve el objeto User oficial de Spring Security
        return new User(
                usuario.getUsername(),
                usuario.getPasswordHash(),
                usuario.getEstado().equals("Activo"), // Verifica si la cuenta está activa
                true, true, true,
                Collections.singletonList(authority)
        );
    }
}