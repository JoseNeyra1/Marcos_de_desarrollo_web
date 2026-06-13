package com.clinica.reservas.repository;

import com.clinica.reservas.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Integer> {

    // Con @Query evitamos que Spring se confunda con el guion bajo del nombre
    @Query("SELECT m FROM Medico m WHERE m.especialidad.id_especialidad = :id")
    List<Medico> findByEspecialidad_IdEspecialidad(@Param("id") Integer id);
}