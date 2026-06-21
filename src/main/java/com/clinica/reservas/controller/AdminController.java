package com.clinica.reservas.controller;

import com.clinica.reservas.repository.CitaRepository;
import com.clinica.reservas.repository.EspecialidadRepository;
import com.clinica.reservas.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.clinica.reservas.entity.Cita;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
    // GENERAR REPORTE PDF DE CITAS
    @GetMapping("/reporte/citas")
    public void generarReporteCitas(HttpServletResponse response) throws IOException, DocumentException {
        // Configuramos la respuesta como un archivo PDF descargable
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_citas.pdf");

        // Creamos el documento PDF
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Título del PDF
        document.add(new Paragraph("Reporte General de Citas Médicas"));
        document.add(new Paragraph("Clinica Salud Plus"));
        document.add(new Paragraph(" ")); // Espacio en blanco

        // Creamos una tabla de 4 columnas
        PdfPTable tabla = new PdfPTable(4);
        tabla.addCell("Fecha y Hora");
        tabla.addCell("Paciente");
        tabla.addCell("Especialidad");
        tabla.addCell("Estado");

        // Traemos todas las citas de la base de datos
        List<Cita> citas = citaRepository.findAll();

        // Llenamos la tabla fila por fila
        for (Cita cita : citas) {
            tabla.addCell(cita.getFechaHora().toString());
            tabla.addCell(cita.getPaciente().getNombres() + " " + cita.getPaciente().getApellidos());
            tabla.addCell(cita.getMedico().getEspecialidad().getNombreEspecialidad());
            tabla.addCell(cita.getEstado());
        }

        // Agregamos la tabla al documento y lo cerramos
        document.add(tabla);
        document.close();
    }
}