package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.HistoricoAcaoUsuarioDTO;
import br.com.fiap.aquamind.service.HistoricoAcaoUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de HistoricoAcaoUsuario, agora usando HistoricoAcaoUsuarioService.
 */
@RestController
@RequestMapping("/api/historico-acoes")
public class HistoricoAcaoUsuarioController {

    @Autowired
    private HistoricoAcaoUsuarioService historicoService;

    /**
     * GET /api/historico-acoes
     * Retorna todos os registros de histórico como DTO.
     */
    @GetMapping
    public List<HistoricoAcaoUsuarioDTO> listarTodos() {
        return historicoService.listarTodas();
    }

    /**
     * GET /api/historico-acoes/{id}
     * Busca um registro de histórico por ID. Se não existir, ResourceNotFoundException é lançada pelo service.
     */
    @GetMapping("/{id}")
    public HistoricoAcaoUsuarioDTO buscarPorId(@PathVariable Long id) {
        return historicoService.buscarPorId(id);
    }

    /**
     * POST /api/historico-acoes
     * Cria um novo histórico de ação de usuário a partir de um DTO válido.
     */
    @PostMapping
    public ResponseEntity<HistoricoAcaoUsuarioDTO> criar(
            @Valid @RequestBody HistoricoAcaoUsuarioDTO novoDTO
    ) {
        HistoricoAcaoUsuarioDTO criado = historicoService.criar(novoDTO);
        return ResponseEntity.status(201).body(criado);
    }

    /**
     * PUT /api/historico-acoes/{id}
     * Atualiza um histórico de ação de usuário existente.
     * Se não existir, ResourceNotFoundException é lançada pelo service.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HistoricoAcaoUsuarioDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody HistoricoAcaoUsuarioDTO dtoAtualizado
    ) {
        HistoricoAcaoUsuarioDTO atualizado = historicoService.atualizar(id, dtoAtualizado);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * DELETE /api/historico-acoes/{id}
     * Deleta um registro de histórico. Se não existir, ResourceNotFoundException é lançada pelo service.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        historicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
