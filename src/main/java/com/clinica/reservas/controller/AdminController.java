package com.clinica.reservas.controller;

import com.clinica.reservas.repository.CitaRepository;
import com.clinica.reservas.repository.EspecialidadRepository;
import com.clinica.reservas.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired private MedicoRepository medicoRepository;
    @Autowired private EspecialidadRepository especialidadRepository;
    @Autowired private CitaRepository citaRepository;

    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Long>> obtenerEstadisticas() {
        Map<String, Long> estadisticas = new HashMap<>();

        estadisticas.put("medicos", medicoRepository.count());
        estadisticas.put("especialidades", especialidadRepository.count());
        estadisticas.put("citas", citaRepository.count());

        return ResponseEntity.ok(estadisticas);
    }
}