package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.PropriedadeDTO;
import br.com.fiap.aquamind.service.PropriedadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de Propriedade, agora usando PropriedadeService.
 */
@RestController
@RequestMapping("/api/propriedades")
public class PropriedadeController {

    @Autowired
    private PropriedadeService propriedadeService;

    /**
     * GET /api/propriedades
     * Retorna todas as propriedades em formato DTO.
     */
    @GetMapping
    public List<PropriedadeDTO> listarTodas() {
        return propriedadeService.listarTodas();
    }

    /**
     * GET /api/propriedades/{id}
     * Busca uma propriedade por ID. Se não existir, ResourceNotFoundException é lançada pelo service.
     */
    @GetMapping("/{id}")
    public PropriedadeDTO buscarPorId(@PathVariable Long id) {
        return propriedadeService.buscarPorId(id);
    }

    /**
     * POST /api/propriedades
     * Cria uma nova propriedade. Recebe PropriedadeDTO, retorna PropriedadeDTO criado com 201.
     */
    @PostMapping
    public ResponseEntity<PropriedadeDTO> criar(
            @Valid @RequestBody PropriedadeDTO novaDTO
    ) {
        PropriedadeDTO criado = propriedadeService.criar(novaDTO);
        return ResponseEntity.status(201).body(criado);
    }

    /**
     * PUT /api/propriedades/{id}
     * Atualiza uma propriedade existente. Se não existir, ResourceNotFoundException é lançada pelo service.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PropriedadeDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PropriedadeDTO dtoAtualizado
    ) {
        PropriedadeDTO atualizado = propriedadeService.atualizar(id, dtoAtualizado);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * DELETE /api/propriedades/{id}
     * Deleta uma propriedade existente. Se não existir, ResourceNotFoundException é lançada pelo service.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        propriedadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
