package com.clinica.reservas.controller;

import com.clinica.reservas.entity.Especialidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Interfaz aquí mismo
interface EspecialidadRepository extends JpaRepository<Especialidad, Integer> {}

@RestController
@RequestMapping("/api/especialidades")
@CrossOrigin(origins = "*")
public class EspecialidadController {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @GetMapping
    public ResponseEntity<List<Especialidad>> obtenerTodas() {
        return ResponseEntity.ok(especialidadRepository.findAll());
    }
}