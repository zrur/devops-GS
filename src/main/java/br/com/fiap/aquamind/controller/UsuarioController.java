package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.UsuarioDTO;
import br.com.fiap.aquamind.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de Usuario, agora usando UsuarioService.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * GET /api/usuarios
     * Retorna todos os usuários como DTO.
     */
    @GetMapping
    public List<UsuarioDTO> listarTodos() {
        return usuarioService.listarTodos();
    }

    /**
     * GET /api/usuarios/{id}
     * Busca um usuário por ID. Se não existir, o service lançará ResourceNotFoundException.
     */
    @GetMapping("/{id}")
    public UsuarioDTO buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    /**
     * POST /api/usuarios
     * Cria um novo usuário a partir de DTO válido. Retorna 201 Created com o DTO gerado.
     */
    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioDTO novoDTO) {
        UsuarioDTO criado = usuarioService.criar(novoDTO);
        return ResponseEntity.status(201).body(criado);
    }

    /**
     * PUT /api/usuarios/{id}
     * Atualiza um usuário existente. Se não existir, ResourceNotFoundException será lançada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO dtoAtualizado
    ) {
        UsuarioDTO atualizado = usuarioService.atualizar(id, dtoAtualizado);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * DELETE /api/usuarios/{id}
     * Deleta um usuário. Se não existir, ResourceNotFoundException será lançada.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
