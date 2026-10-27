package com.matias.ventaentradas.controller;

import com.matias.ventaentradas.dto.devolucion.DevolucionEntrada;
import com.matias.ventaentradas.dto.devolucion.DevolucionResponse;
import com.matias.ventaentradas.service.DevolucionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets/{id}/devoluciones")
@AllArgsConstructor
public class DevolucionController {
    private final DevolucionService devolucionService;

    @PostMapping
    public ResponseEntity<DevolucionResponse> procesarDevolucion(
            @PathVariable Long id,@Valid @RequestBody DevolucionEntrada devolucionEntrada
            ){
        DevolucionResponse response = devolucionService.devolucionTicket(id,devolucionEntrada);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
