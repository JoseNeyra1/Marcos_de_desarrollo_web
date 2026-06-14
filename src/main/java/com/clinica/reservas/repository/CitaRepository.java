package com.clinica.reservas.repository;

import com.clinica.reservas.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

    // El que ya tenías para el paciente
    @Query("SELECT c FROM Cita c WHERE c.paciente.numeroDocumento = :dniPaciente ORDER BY c.fechaHora DESC")
    List<Cita> buscarCitasPorDniPaciente(@Param("dniPaciente") String dniPaciente);

    // NUEVO: El buscador para la agenda del médico
    @Query("SELECT c FROM Cita c WHERE c.medico.numeroDocumento = :dniMedico ORDER BY c.fechaHora ASC")
    List<Cita> buscarCitasPorDniMedico(@Param("dniMedico") String dniMedico);
}