package com.clinica.reservas.dto;

public class CitaRequest {
    private Integer idMedico;
    private String fechaHora; // Formato esperado: YYYY-MM-DDTHH:MM
    private String motivoConsulta;

    // Getters y Setters
    public Integer getIdMedico() { return idMedico; }
    public void setIdMedico(Integer idMedico) { this.idMedico = idMedico; }

    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }

    public String getMotivoConsulta() { return motivoConsulta; }
    public void setMotivoConsulta(String motivoConsulta) { this.motivoConsulta = motivoConsulta; }
}