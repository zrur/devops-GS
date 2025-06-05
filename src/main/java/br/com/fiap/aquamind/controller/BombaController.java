package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.BombaDTO;
import br.com.fiap.aquamind.service.BombaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de Bomba, agora usando a camada de serviço.
 */
@RestController
@RequestMapping("/api/bombas")
public class BombaController {

    @Autowired
    private BombaService bombaService;

    /**
     * GET /api/bombas
     * Retorna todas as bombas (DTO).
     */
    @GetMapping
    public List<BombaDTO> listarTodas() {
        return bombaService.listarTodas();
    }

    /**
     * GET /api/bombas/{id}
     * Busca uma única bomba por ID.
     */
    @GetMapping("/{id}")
    public BombaDTO buscarPorId(@PathVariable Long id) {
        return bombaService.buscarPorId(id);
    }

    /**
     * POST /api/bombas
     * Cria uma bomba a partir de um DTO válido.
     */
    @PostMapping
    public ResponseEntity<BombaDTO> criar(
            @Valid @RequestBody BombaDTO novaBombaDTO
    ) {
        BombaDTO criada = bombaService.criar(novaBombaDTO);
        return ResponseEntity.status(201).body(criada);
    }

    /**
     * PUT /api/bombas/{id}
     * Atualiza uma bomba existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BombaDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody BombaDTO dadosAtualizadosDTO
    ) {
        BombaDTO atualizada = bombaService.atualizar(id, dadosAtualizadosDTO);
        return ResponseEntity.ok(atualizada);
    }

    /**
     * DELETE /api/bombas/{id}
     * Deleta uma bomba. Se não existir, o service lança ResourceNotFoundException.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        bombaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
