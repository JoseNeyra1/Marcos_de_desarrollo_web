package com.clinica.reservas.controller;

import com.clinica.reservas.dto.MedicoRequest;
import com.clinica.reservas.entity.Especialidad;
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

    // Buscar todos los médicos (Para la tabla del Admin)
    @GetMapping
    public ResponseEntity<List<Medico>> obtenerTodos() {
        return ResponseEntity.ok(medicoRepository.findAll());
    }

    // Buscar por especialidad (Para el paciente al agendar)
    @GetMapping("/especialidad/{id}")
    public ResponseEntity<List<Medico>> obtenerPorEspecialidad(@PathVariable Integer id) {
        List<Medico> medicos = medicoRepository.findByEspecialidad_IdEspecialidad(id);
        return ResponseEntity.ok(medicos);
    }

    // NUEVO: Registrar un médico
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarMedico(@RequestBody MedicoRequest request) {
        Medico nuevoMedico = new Medico();
        nuevoMedico.setTipoDocumento("DNI");
        nuevoMedico.setNumeroDocumento(request.getNumeroDocumento());
        nuevoMedico.setNombres(request.getNombres());
        nuevoMedico.setApellidos(request.getApellidos());
        nuevoMedico.setColegiaturaMedica(request.getColegiaturaMedica());
        nuevoMedico.setTelefono(request.getTelefono());
        nuevoMedico.setCorreo(request.getCorreo());

        // Asignar la especialidad elegida
        Especialidad especialidad = new Especialidad();
        especialidad.setId_especialidad(request.getIdEspecialidad());
        nuevoMedico.setEspecialidad(especialidad);

        medicoRepository.save(nuevoMedico);

        return ResponseEntity.ok().body("{\"mensaje\": \"Médico registrado exitosamente\"}");
    }
    // 4. ELIMINAR MÉDICO
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMedico(@PathVariable Integer id) {
        try {
            medicoRepository.deleteById(id);
            return ResponseEntity.ok().body("{\"mensaje\": \"Médico eliminado exitosamente\"}");
        } catch (Exception e) {
            // Si el médico ya tiene citas programadas, MySQL bloqueará el borrado por seguridad (Foreign Key)
            return ResponseEntity.badRequest().body("{\"mensaje\": \"No se puede eliminar: el médico tiene citas asignadas.\"}");
        }
    }

    // 5. EDITAR MÉDICO
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMedico(@PathVariable Integer id, @RequestBody MedicoRequest request) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        medico.setNumeroDocumento(request.getNumeroDocumento());
        medico.setNombres(request.getNombres());
        medico.setApellidos(request.getApellidos());
        medico.setColegiaturaMedica(request.getColegiaturaMedica());
        medico.setTelefono(request.getTelefono());
        medico.setCorreo(request.getCorreo());

        Especialidad especialidad = new Especialidad();
        especialidad.setId_especialidad(request.getIdEspecialidad());
        medico.setEspecialidad(especialidad);

        medicoRepository.save(medico);
        return ResponseEntity.ok().body("{\"mensaje\": \"Médico actualizado exitosamente\"}");
    }
}