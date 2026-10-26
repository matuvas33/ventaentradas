package com.matias.ventaentradas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "devoluciones")
public class Devolucion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_compra",nullable = false)
    private Ticket ticket;
    @Column(name = "cantidad",nullable = false)
    private Integer cantidad;
    @Column(name = "fecha_devolucion",updatable = false,nullable = false)
    private LocalDateTime fechaDevolucion;

    @PrePersist
    protected void onCreate(){
        if (this.fechaDevolucion==null){
            this.fechaDevolucion = LocalDateTime.now();
        }
    }

    public Devolucion(Ticket ticket, Integer cantidad) {
        this.ticket = ticket;
        this.cantidad = cantidad;
    }
}
