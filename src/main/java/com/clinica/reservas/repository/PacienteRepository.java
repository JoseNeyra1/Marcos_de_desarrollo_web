package com.clinica.reservas.repository;

import com.clinica.reservas.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    // Busca un paciente usando su DNI (que en usuarios es el username)
    Optional<Paciente> findByNumeroDocumento(String numeroDocumento);
}