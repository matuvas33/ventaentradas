package com.matias.ventaentradas.controller;

import com.matias.ventaentradas.dto.evento.CrearEvento;
import com.matias.ventaentradas.dto.evento.EventoMapper;
import com.matias.ventaentradas.dto.evento.EventoResponse;
import com.matias.ventaentradas.service.EventoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eventos")
@AllArgsConstructor
public class EventoController {
    private final EventoService eventoService;


    @PostMapping
    public ResponseEntity<EventoResponse> crearEvento(@Valid @RequestBody CrearEvento crearEvento){
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.crearEvento(crearEvento));
    }

    @GetMapping("{/id}")
    public ResponseEntity<EventoResponse> obtenerEventoId(@PathVariable Long id){
        return ResponseEntity.ok(eventoService.obtenerEventoId(id));
    }


}
