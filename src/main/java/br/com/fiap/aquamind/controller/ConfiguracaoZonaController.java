package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.ConfiguracaoZonaDTO;
import br.com.fiap.aquamind.service.ConfiguracaoZonaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de ConfiguracaoZona, agora usando ConfiguracaoZonaService.
 */
@RestController
@RequestMapping("/api/config-zona")
public class ConfiguracaoZonaController {

    @Autowired
    private ConfiguracaoZonaService configuracaoZonaService;

    /**
     * GET /api/config-zona
     * Retorna todas as configurações de zona como DTOs.
     */
    @GetMapping
    public List<ConfiguracaoZonaDTO> listarTodas() {
        return configuracaoZonaService.listarTodas();
    }

    /**
     * GET /api/config-zona/{id}
     * Busca uma configuração de zona por ID; se não existir, o service lança ResourceNotFoundException.
     */
    @GetMapping("/{id}")
    public ConfiguracaoZonaDTO buscarPorId(@PathVariable Long id) {
        return configuracaoZonaService.buscarPorId(id);
    }

    /**
     * POST /api/config-zona
     * Cria uma nova configuração de zona a partir de um DTO válido.
     */
    @PostMapping
    public ResponseEntity<ConfiguracaoZonaDTO> criar(
            @Valid @RequestBody ConfiguracaoZonaDTO novoDTO
    ) {
        ConfiguracaoZonaDTO criado = configuracaoZonaService.criar(novoDTO);
        return ResponseEntity.status(201).body(criado);
    }

    /**
     * PUT /api/config-zona/{id}
     * Atualiza uma configuração de zona existente. Se não existir, o service lança ResourceNotFoundException.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ConfiguracaoZonaDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ConfiguracaoZonaDTO dtoAtualizado
    ) {
        ConfiguracaoZonaDTO atualizado = configuracaoZonaService.atualizar(id, dtoAtualizado);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * DELETE /api/config-zona/{id}
     * Deleta uma configuração de zona. Se não existir, o service lança ResourceNotFoundException.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        configuracaoZonaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
