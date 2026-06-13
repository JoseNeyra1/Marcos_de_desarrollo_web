package com.clinica.reservas.repository;

import com.clinica.reservas.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

    // Este método mágico buscará todas las citas que le pertenezcan a un DNI específico
    @Query("SELECT c FROM Cita c WHERE c.paciente.numeroDocumento = :dniPaciente ORDER BY c.fechaHora DESC")
    List<Cita> buscarCitasPorDniPaciente(String dniPaciente);
}