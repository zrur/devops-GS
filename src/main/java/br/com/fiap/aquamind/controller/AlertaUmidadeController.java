package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.AlertaUmidadeDTO;
import br.com.fiap.aquamind.service.AlertaUmidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de AlertaUmidade, agora usando AlertaUmidadeService.
 */
@RestController
@RequestMapping("/api/alertas-umidade")
public class AlertaUmidadeController {

    @Autowired
    private AlertaUmidadeService alertaUmidadeService;

    /**
     * GET /api/alertas-umidade
     * Retorna todos os alertas de umidade em formato DTO.
     */
    @GetMapping
    public List<AlertaUmidadeDTO> listarTodos() {
        return alertaUmidadeService.listarTodas();
    }

    /**
     * GET /api/alertas-umidade/{id}
     * Busca um alerta por ID; se não encontrar, o service lança ResourceNotFoundException.
     */
    @GetMapping("/{id}")
    public AlertaUmidadeDTO buscarPorId(@PathVariable Long id) {
        return alertaUmidadeService.buscarPorId(id);
    }

    /**
     * POST /api/alertas-umidade
     * Cria um novo alerta de umidade. Recebe AlertaUmidadeDTO no corpo.
     */
    @PostMapping
    public ResponseEntity<AlertaUmidadeDTO> criar(
            @Valid @RequestBody AlertaUmidadeDTO novoDTO
    ) {
        AlertaUmidadeDTO criado = alertaUmidadeService.criar(novoDTO);
        return ResponseEntity.status(201).body(criado);
    }

    /**
     * PUT /api/alertas-umidade/{id}
     * Atualiza um alerta de umidade existente. Recebe DTO com os novos valores.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AlertaUmidadeDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AlertaUmidadeDTO dtoAtualizado
    ) {
        AlertaUmidadeDTO atualizado = alertaUmidadeService.atualizar(id, dtoAtualizado);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * DELETE /api/alertas-umidade/{id}
     * Deleta um alerta de umidade. Se não existir, o service lança ResourceNotFoundException.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        alertaUmidadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
