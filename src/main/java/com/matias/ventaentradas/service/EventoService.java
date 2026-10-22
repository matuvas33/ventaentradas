package com.matias.ventaentradas.service;

import com.matias.ventaentradas.dto.evento.CrearEvento;
import com.matias.ventaentradas.dto.evento.EventoMapper;
import com.matias.ventaentradas.dto.evento.EventoResponse;
import com.matias.ventaentradas.globalexceptionhandler.excepciones.EventoDuplicadoException;
import com.matias.ventaentradas.model.Evento;
import com.matias.ventaentradas.repository.EventoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
@AllArgsConstructor
public class EventoService {
    private final EventoRepository eventoRepository;
    private final EventoMapper eventoMapper;

    public EventoResponse crearEvento(CrearEvento crearEvento){

        if (eventoRepository.existsByNombre(crearEvento.nombre())){
            throw new EventoDuplicadoException("No puede tener el mismo nombre que otro evento! "+crearEvento.nombre());
        }

        Evento evento = eventoMapper.toEntity(crearEvento);

        Evento guardado = eventoRepository.save(evento);

        return eventoMapper.toDto(guardado);
    }

    public EventoResponse obtenerEventoId(Long id){
       Evento eventoEncontrado = eventoRepository.findByIdOrThrow(id);

        return eventoMapper.toDto(eventoEncontrado);
    }

}
