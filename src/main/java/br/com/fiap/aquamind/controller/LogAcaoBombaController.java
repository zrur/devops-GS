package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.LogAcaoBombaDTO;
import br.com.fiap.aquamind.service.LogAcaoBombaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de LogAcaoBomba, agora usando LogAcaoBombaService.
 */
@RestController
@RequestMapping("/api/logs-bomba")
public class LogAcaoBombaController {

    @Autowired
    private LogAcaoBombaService logAcaoBombaService;

    /**
     * GET /api/logs-bomba
     * Lista todos os logs de ação de bomba como DTO.
     */
    @GetMapping
    public List<LogAcaoBombaDTO> listarTodos() {
        return logAcaoBombaService.listarTodos();
    }

    /**
     * GET /api/logs-bomba/{id}
     * Busca um log por ID. Se não existir, o service lança ResourceNotFoundException.
     */
    @GetMapping("/{id}")
    public LogAcaoBombaDTO buscarPorId(@PathVariable Long id) {
        return logAcaoBombaService.buscarPorId(id);
    }

    /**
     * POST /api/logs-bomba
     * Cria um novo log de ação de bomba a partir de DTO válido.
     */
    @PostMapping
    public ResponseEntity<LogAcaoBombaDTO> criar(
            @Valid @RequestBody LogAcaoBombaDTO novoDTO
    ) {
        LogAcaoBombaDTO criado = logAcaoBombaService.criar(novoDTO);
        return ResponseEntity.status(201).body(criado);
    }

    /**
     * PUT /api/logs-bomba/{id}
     * Atualiza um log existente. Se não existir, o service lança ResourceNotFoundException.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LogAcaoBombaDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody LogAcaoBombaDTO dtoAtualizado
    ) {
        LogAcaoBombaDTO atualizado = logAcaoBombaService.atualizar(id, dtoAtualizado);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * DELETE /api/logs-bomba/{id}
     * Deleta um log existente. Se não existir, o service lança ResourceNotFoundException.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        logAcaoBombaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
