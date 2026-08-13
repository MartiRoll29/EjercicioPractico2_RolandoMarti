package com.medicare.domain;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "cita_medica")
public class CitaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paciente_nombre", length = 150)
    private String pacienteNombre;

    @Column(length = 100)
    private String especialidad;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fecha;

    @Column
    private Double costo;

    @Column(nullable = false)
    private Boolean activa = true;

    public CitaMedica() {}

    public CitaMedica(String pacienteNombre, String especialidad, LocalDate fecha, Double costo) {
        this.pacienteNombre = pacienteNombre;
        this.especialidad = especialidad;
        this.fecha = fecha;
        this.costo = costo;
        this.activa = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPacienteNombre() {
        return pacienteNombre;
    }

    public void setPacienteNombre(String pacienteNombre) {
        this.pacienteNombre = pacienteNombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    @Override
    public String toString() {
        return "CitaMedica{" +
                "id=" + id +
                ", pacienteNombre='" + pacienteNombre + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", fecha=" + fecha +
                ", costo=" + costo +
                ", activa=" + activa +
                '}';
    }
}
