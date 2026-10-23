package com.matias.ventaentradas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "evento")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre",length = 100,nullable = false)
    private String nombre;
    @Column(name = "fecha",nullable = false)
    private LocalDate fecha;
    @Column(name = "capacidad_total",nullable = false)
    private Integer capacidadTotal;
    @Column(name = "capacidad_disponible",nullable = false)
    private Integer capacidadDisponible;
    @Column(name = "precio",precision = 10,scale = 2)
    private BigDecimal precio;

    public Evento(String nombre, LocalDate fecha, Integer capacidadTotal, Integer capacidadDisponible, BigDecimal precio) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.capacidadTotal = capacidadTotal;
        this.capacidadDisponible = capacidadTotal;
        this.precio = precio;
    }
}
