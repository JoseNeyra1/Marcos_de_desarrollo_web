package com.clinica.reservas.controller;

import com.clinica.reservas.entity.Medico;
import com.clinica.reservas.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@CrossOrigin(origins = "*")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    // Devuelve los médicos filtrados por el ID de la especialidad
    @GetMapping("/especialidad/{id}")
    public ResponseEntity<List<Medico>> obtenerPorEspecialidad(@PathVariable Integer id) {
        List<Medico> medicos = medicoRepository.findByEspecialidad_IdEspecialidad(id);
        return ResponseEntity.ok(medicos);
    }
}