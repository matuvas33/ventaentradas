package com.matias.ventaentradas.service;

import com.matias.ventaentradas.dto.evento.CrearEvento;
import com.matias.ventaentradas.dto.evento.EventoMapper;
import com.matias.ventaentradas.dto.evento.EventoResponse;
import com.matias.ventaentradas.globalexceptionhandler.excepciones.EventoDuplicadoException;
import com.matias.ventaentradas.model.Evento;
import com.matias.ventaentradas.repository.EventoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    public Page<EventoResponse> listarEventos(LocalDate fecha,Boolean soloDisponibles,Pageable pageable){
      boolean filtrarDisponibles = Boolean.TRUE.equals(soloDisponibles);
      Page<Evento> eventos;
      if (fecha !=null && soloDisponibles){
          eventos=eventoRepository.findByFechaAndCapacidadDisponibleGreaterThan(fecha,0,pageable);
      }
      else if (fecha!=null){

          eventos = eventoRepository.findByFecha(fecha,pageable);

      }else if(filtrarDisponibles){

          eventos=eventoRepository.findByCapacidadDisponibleGreaterThan(0,pageable);
      }
      else {
          eventos = eventoRepository.findAll(pageable);
      }

      return eventos.map(eventoMapper::toDto);
    }
    public EventoResponse obtenerEventoId(Long id){
       Evento eventoEncontrado = eventoRepository.findByIdOrThrow(id);

        return eventoMapper.toDto(eventoEncontrado);
    }

}
