package com.clinica.reservas.dto;

public class MedicoRequest {
    private String numeroDocumento;
    private String nombres;
    private String apellidos;
    private String colegiaturaMedica;
    private Integer idEspecialidad;
    private String telefono;
    private String correo;

    // Getters y Setters
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getColegiaturaMedica() { return colegiaturaMedica; }
    public void setColegiaturaMedica(String colegiaturaMedica) { this.colegiaturaMedica = colegiaturaMedica; }

    public Integer getIdEspecialidad() { return idEspecialidad; }
    public void setIdEspecialidad(Integer idEspecialidad) { this.idEspecialidad = idEspecialidad; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
}