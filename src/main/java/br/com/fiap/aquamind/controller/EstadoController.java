package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.EstadoDTO;
import br.com.fiap.aquamind.service.EstadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de Estado, agora usando EstadoService.
 */
@RestController
@RequestMapping("/api/estados")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    /**
     * GET /api/estados
     * Retorna todos os estados em formato DTO.
     */
    @GetMapping
    public List<EstadoDTO> listarTodos() {
        return estadoService.listarTodos();
    }

    /**
     * GET /api/estados/{id}
     * Busca um estado por ID. Se não existir, ResourceNotFoundException é lançada pelo service.
     */
    @GetMapping("/{id}")
    public EstadoDTO buscarPorId(@PathVariable Long id) {
        return estadoService.buscarPorId(id);
    }

    /**
     * POST /api/estados
     * Cria um novo estado a partir de um DTO válido.
     */
    @PostMapping
    public ResponseEntity<EstadoDTO> criar(@Valid @RequestBody EstadoDTO novoEstadoDTO) {
        EstadoDTO criado = estadoService.criar(novoEstadoDTO);
        return ResponseEntity.status(201).body(criado);
    }

    /**
     * PUT /api/estados/{id}
     * Atualiza um estado existente. Se não existir, ResourceNotFoundException é lançada pelo service.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EstadoDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EstadoDTO estadoAtualizadoDTO
    ) {
        EstadoDTO atualizado = estadoService.atualizar(id, estadoAtualizadoDTO);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * DELETE /api/estados/{id}
     * Deleta um estado. Se não existir, ResourceNotFoundException é lançada pelo service.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        estadoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
