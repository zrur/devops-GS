package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.ZonaDTO;
import br.com.fiap.aquamind.service.ZonaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de Zona, agora usando ZonaService.
 */
@RestController
@RequestMapping("/api/zonas")
public class ZonaController {

    @Autowired
    private ZonaService zonaService;

    /**
     * GET /api/zonas
     * Lista todas as zonas como DTOs.
     */
    @GetMapping
    public List<ZonaDTO> listarTodas() {
        return zonaService.listarTodas();
    }

    /**
     * GET /api/zonas/{id}
     * Busca uma zona por ID. Se não existir, ResourceNotFoundException é lançada pelo service.
     */
    @GetMapping("/{id}")
    public ZonaDTO buscarPorId(@PathVariable Long id) {
        return zonaService.buscarPorId(id);
    }

    /**
     * POST /api/zonas
     * Cria uma nova zona a partir de DTO válido. Retorna 201 Created com o DTO criado.
     */
    @PostMapping
    public ResponseEntity<ZonaDTO> criar(@Valid @RequestBody ZonaDTO novaDTO) {
        ZonaDTO criado = zonaService.criar(novaDTO);
        return ResponseEntity.status(201).body(criado);
    }

    /**
     * PUT /api/zonas/{id}
     * Atualiza uma zona existente. Se não existir, ResourceNotFoundException é lançada pelo service.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ZonaDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ZonaDTO dtoAtualizado
    ) {
        ZonaDTO atualizado = zonaService.atualizar(id, dtoAtualizado);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * DELETE /api/zonas/{id}
     * Deleta uma zona existente. Se não existir, ResourceNotFoundException é lançada pelo service.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        zonaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
