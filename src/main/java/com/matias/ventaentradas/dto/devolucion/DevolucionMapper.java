package com.matias.ventaentradas.dto.devolucion;

import com.matias.ventaentradas.model.Devolucion;
import com.matias.ventaentradas.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public class DevolucionMapper {

    public Devolucion toEntity(DevolucionEntrada dto, Ticket ticket){
        if (dto==null)return null;

        Devolucion devolucion = new Devolucion();

        devolucion.setTicket(ticket);
        devolucion.setCantidad(dto.cantidad());

        return devolucion;
    }

    public DevolucionResponse toDto(Devolucion devolucion){
        if (devolucion==null)return null;

        return new DevolucionResponse(
                devolucion.getId(),
                devolucion.getTicket().getId(),
                devolucion.getTicket().getEmailComprador(),
                devolucion.getCantidad(),
                devolucion.getFechaDevolucion()
        );
    }
}
