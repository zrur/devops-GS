package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.SensorDTO;
import br.com.fiap.aquamind.service.SensorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de Sensor, agora usando SensorService.
 */
@RestController
@RequestMapping("/api/sensores")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    /**
     * GET /api/sensores
     * Lista todos os sensores como DTOs.
     */
    @GetMapping
    public List<SensorDTO> listarTodos() {
        return sensorService.listarTodos();
    }

    /**
     * GET /api/sensores/{id}
     * Busca um sensor por ID. Se não existir, ResourceNotFoundException é lançada pelo service.
     */
    @GetMapping("/{id}")
    public SensorDTO buscarPorId(@PathVariable Long id) {
        return sensorService.buscarPorId(id);
    }

    /**
     * POST /api/sensores
     * Cria um novo sensor a partir de DTO válido. Retorna 201 com DTO criado.
     */
    @PostMapping
    public ResponseEntity<SensorDTO> criar(@Valid @RequestBody SensorDTO novoDTO) {
        SensorDTO criado = sensorService.criar(novoDTO);
        return ResponseEntity.status(201).body(criado);
    }

    /**
     * PUT /api/sensores/{id}
     * Atualiza um sensor existente. Se não existir, ResourceNotFoundException é lançada pelo service.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SensorDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody SensorDTO dtoAtualizado
    ) {
        SensorDTO atualizado = sensorService.atualizar(id, dtoAtualizado);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * DELETE /api/sensores/{id}
     * Deleta um sensor existente. Se não existir, ResourceNotFoundException é lançada pelo service.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        sensorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
