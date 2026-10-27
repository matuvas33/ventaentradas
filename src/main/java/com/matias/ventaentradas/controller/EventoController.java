package com.matias.ventaentradas.controller;

import com.matias.ventaentradas.dto.evento.CrearEvento;
import com.matias.ventaentradas.dto.evento.EventoResponse;
import com.matias.ventaentradas.service.EventoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/eventos")
@AllArgsConstructor
public class EventoController {
    private final EventoService eventoService;

    @PostMapping
    public ResponseEntity<EventoResponse> crearEvento(@Valid @RequestBody CrearEvento crearEvento){
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.crearEvento(crearEvento));
    }

    @GetMapping
    public Page<EventoResponse> listarEventos(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha,
            @RequestParam(required = false,defaultValue = "false")
            Boolean soloDisponible,
            @PageableDefault(size = 5,page = 0)
            Pageable pageable){

        return eventoService.listarEventos(fecha,soloDisponible,pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponse> obtenerEventoId(@PathVariable Long id){
        return ResponseEntity.ok(eventoService.obtenerEventoId(id));
    }


}
