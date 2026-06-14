package com.clinica.reservas.controller;

import com.clinica.reservas.dto.CitaRequest;
import com.clinica.reservas.entity.Cita;
import com.clinica.reservas.entity.Medico;
import com.clinica.reservas.entity.Paciente;
import com.clinica.reservas.repository.CitaRepository;
import com.clinica.reservas.repository.MedicoRepository;
import com.clinica.reservas.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*")
public class CitaController {

    @Autowired private CitaRepository citaRepository;
    @Autowired private PacienteRepository pacienteRepository;
    @Autowired private MedicoRepository medicoRepository;

    // 1. OBTENER LAS CITAS DEL PACIENTE
    @GetMapping("/mis-citas")
    public ResponseEntity<List<Cita>> obtenerMisCitas(Authentication authentication) {
        String dniPaciente = authentication.getName();
        List<Cita> misCitas = citaRepository.buscarCitasPorDniPaciente(dniPaciente);
        return ResponseEntity.ok(misCitas);
    }

    // 2. AGENDAR UNA NUEVA CITA
    @PostMapping("/agendar")
    public ResponseEntity<?> agendarCita(@RequestBody CitaRequest request, Authentication authentication) {
        String dniPaciente = authentication.getName();
        Paciente paciente = pacienteRepository.findByNumeroDocumento(dniPaciente)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        Medico medico = medicoRepository.findById(request.getIdMedico())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        Cita nuevaCita = new Cita();
        nuevaCita.setPaciente(paciente);
        nuevaCita.setMedico(medico);
        nuevaCita.setFechaHora(LocalDateTime.parse(request.getFechaHora()));
        nuevaCita.setMotivoConsulta(request.getMotivoConsulta());
        nuevaCita.setEstado("Confirmada");

        citaRepository.save(nuevaCita);

        return ResponseEntity.ok().body("{\"mensaje\": \"Cita agendada exitosamente\"}");
    }

    // 3. CANCELAR UNA CITA
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarCita(@PathVariable Integer id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        cita.setEstado("Cancelada");
        citaRepository.save(cita);

        return ResponseEntity.ok().body("{\"mensaje\": \"Cita cancelada con éxito\"}");
    }
    // 4. OBTENER LA AGENDA DEL MÉDICO
    @GetMapping("/agenda-medico")
    public ResponseEntity<List<Cita>> obtenerAgendaMedico(Authentication authentication) {
        // En este caso, el username logueado es el DNI del médico
        String dniMedico = authentication.getName();
        List<Cita> agenda = citaRepository.buscarCitasPorDniMedico(dniMedico);
        return ResponseEntity.ok(agenda);
    }

    // 5. MARCAR CITA COMO ATENDIDA
    @PutMapping("/{id}/atender")
    public ResponseEntity<?> atenderCita(@PathVariable Integer id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        cita.setEstado("Atendida");
        citaRepository.save(cita);

        return ResponseEntity.ok().body("{\"mensaje\": \"Paciente atendido con éxito\"}");
    }
}