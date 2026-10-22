package com.matias.ventaentradas.dto.evento;

import com.matias.ventaentradas.model.Evento;

import org.springframework.stereotype.Component;

@Component
public class EventoMapper {

    public Evento toEntity(CrearEvento dto){
        if (dto==null)return null;

        Evento evento = new Evento();
        evento.setNombre(dto.nombre());
        evento.setFecha(dto.fecha());
        evento.setCapacidadTotal(dto.capacidadTotal());
        evento.setCapacidadDisponible(dto.capacidadTotal());
        evento.setPrecio(dto.precio());

        return evento;
    }

    public EventoResponse toDto(Evento entity){
        if (entity==null)return null;

        return new EventoResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getFecha(),
                entity.getCapacidadTotal(),
                entity.getCapacidadDisponible(),
                entity.getPrecio()
        );
    }

}
