package com.clinica.reservas.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "especialidades")
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_especialidad;

    @Column(name = "nombre_especialidad", nullable = false, unique = true, length = 100)
    private String nombreEspecialidad;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    public Especialidad() {}

    // Getters y Setters
    public Integer getId_especialidad() { return id_especialidad; }
    public void setId_especialidad(Integer id_especialidad) { this.id_especialidad = id_especialidad; }

    public String getNombreEspecialidad() { return nombreEspecialidad; }
    public void setNombreEspecialidad(String nombreEspecialidad) { this.nombreEspecialidad = nombreEspecialidad; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}