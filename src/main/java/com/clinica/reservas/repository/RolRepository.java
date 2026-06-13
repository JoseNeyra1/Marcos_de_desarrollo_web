package com.clinica.reservas.repository;

import com.clinica.reservas.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    // Spring Boot crea la consulta SQL automáticamente solo con leer el nombre de este método
    Optional<Rol> findByNombreRol(String nombreRol);
}