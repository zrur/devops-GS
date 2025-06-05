package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.RegistroSensorDTO;
import br.com.fiap.aquamind.service.RegistroSensorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de RegistroSensor (leituras de sensor), usando RegistroSensorService.
 */
@RestController
@RequestMapping("/api/leituras")
public class RegistroSensorController {

    @Autowired
    private RegistroSensorService registroSensorService;

    /**
     * GET /api/leituras
     * Lista todas as leituras como DTOs.
     */
    @GetMapping
    public List<RegistroSensorDTO> listarTodas() {
        return registroSensorService.listarTodas();
    }

    /**
     * GET /api/leituras/{id}
     * Busca uma leitura por ID. Se não existir, ResourceNotFoundException é lançada pelo service.
     */
    @GetMapping("/{id}")
    public RegistroSensorDTO buscarPorId(@PathVariable Long id) {
        return registroSensorService.buscarPorId(id);
    }

    /**
     * POST /api/leituras
     * Cria uma nova leitura de sensor a partir de DTO válido. Retorna 201 com DTO criado.
     */
    @PostMapping
    public ResponseEntity<RegistroSensorDTO> criar(
            @Valid @RequestBody RegistroSensorDTO novoDTO
    ) {
        RegistroSensorDTO criado = registroSensorService.criar(novoDTO);
        return ResponseEntity.status(201).body(criado);
    }

    /**
     * PUT /api/leituras/{id}
     * Atualiza uma leitura existente. Se não existir, ResourceNotFoundException é lançada pelo service.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RegistroSensorDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody RegistroSensorDTO dtoAtualizado
    ) {
        RegistroSensorDTO atualizado = registroSensorService.atualizar(id, dtoAtualizado);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * DELETE /api/leituras/{id}
     * Deleta uma leitura existente. Se não existir, ResourceNotFoundException é lançada pelo service.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        registroSensorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
