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
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id",nullable = false)
    private Evento evento;
    @Column(name = "email_comprador",length = 255,nullable = false)
    private String emailComprador;
    @Column(name = "cantidad",nullable = false)
    private Integer cantidad;
    @Column(name = "fecha_compra",updatable = false,nullable = false)
    private LocalDateTime fechaCompra;

    @PrePersist
    protected void onCreate(){
        if (this.fechaCompra == null){
            this.fechaCompra = LocalDateTime.now();
        }
    }

    public Ticket(Evento evento, String emailComprador, Integer cantidad) {
        this.evento = evento;
        this.emailComprador = emailComprador;
        this.cantidad = cantidad;
    }
}

