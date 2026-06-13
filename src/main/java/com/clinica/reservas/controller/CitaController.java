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

    // Método que ya tenías para listar las citas
    @GetMapping("/mis-citas")
    public ResponseEntity<List<Cita>> obtenerMisCitas(Authentication authentication) {
        String dniPaciente = authentication.getName();
        List<Cita> misCitas = citaRepository.buscarCitasPorDniPaciente(dniPaciente);
        return ResponseEntity.ok(misCitas);
    }

    // NUEVO MÉTODO: Para guardar una nueva cita
    @PostMapping("/agendar")
    public ResponseEntity<?> agendarCita(@RequestBody CitaRequest request, Authentication authentication) {
        // 1. Identificar qué paciente está logueado
        String dniPaciente = authentication.getName();
        Paciente paciente = pacienteRepository.findByNumeroDocumento(dniPaciente)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        // 2. Buscar al médico seleccionado
        Medico medico = medicoRepository.findById(request.getIdMedico())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        // 3. Crear y guardar la cita
        Cita nuevaCita = new Cita();
        nuevaCita.setPaciente(paciente);
        nuevaCita.setMedico(medico);
        nuevaCita.setFechaHora(LocalDateTime.parse(request.getFechaHora()));
        nuevaCita.setMotivoConsulta(request.getMotivoConsulta());
        nuevaCita.setEstado("Confirmada"); // Se confirma automáticamente por ahora

        citaRepository.save(nuevaCita);

        return ResponseEntity.ok().body("{\"mensaje\": \"Cita agendada exitosamente\"}");
    }
}