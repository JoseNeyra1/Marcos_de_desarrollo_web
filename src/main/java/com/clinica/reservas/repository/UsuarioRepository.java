package com.clinica.reservas.repository;

import com.clinica.reservas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Este método lo usaremos para buscar al usuario cuando intente hacer Login
    Optional<Usuario> findByUsername(String username);
}