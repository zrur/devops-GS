package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.IrrigacaoDTO;
import br.com.fiap.aquamind.service.IrrigacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de Irrigacao, agora usando IrrigacaoService.
 */
@RestController
@RequestMapping("/api/irrigacoes")
public class IrrigacaoController {

    @Autowired
    private IrrigacaoService irrigacaoService;

    /**
     * GET /api/irrigacoes
     * Lista todas as irrigações como DTO.
     */
    @GetMapping
    public List<IrrigacaoDTO> listarTodas() {
        return irrigacaoService.listarTodas();
    }

    /**
     * GET /api/irrigacoes/{id}
     * Busca uma irrigação por ID. Se não existir, o service lança ResourceNotFoundException.
     */
    @GetMapping("/{id}")
    public IrrigacaoDTO buscarPorId(@PathVariable Long id) {
        return irrigacaoService.buscarPorId(id);
    }

    /**
     * POST /api/irrigacoes
     * Cria uma nova irrigação a partir de DTO válido. Retorna 201 com DTO criado.
     */
    @PostMapping
    public ResponseEntity<IrrigacaoDTO> criar(@Valid @RequestBody IrrigacaoDTO novoDTO) {
        IrrigacaoDTO criado = irrigacaoService.criar(novoDTO);
        return ResponseEntity.status(201).body(criado);
    }

    /**
     * PUT /api/irrigacoes/{id}
     * Atualiza uma irrigação existente. Se não existir, o service lança ResourceNotFoundException.
     */
    @PutMapping("/{id}")
    public ResponseEntity<IrrigacaoDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody IrrigacaoDTO dtoAtualizado
    ) {
        IrrigacaoDTO atualizado = irrigacaoService.atualizar(id, dtoAtualizado);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * DELETE /api/irrigacoes/{id}
     * Deleta uma irrigação existente. Se não existir, o service lança ResourceNotFoundException.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        irrigacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
