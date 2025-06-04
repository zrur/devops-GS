package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.UsuarioDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Usuario;
import br.com.fiap.aquamind.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Endpoints REST para CRUD de Usuario, usando DTO.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * GET /api/usuarios
     * Retorna todos os usuários como DTOs.
     */
    @GetMapping
    public List<UsuarioDTO> listarTodos() {
        List<Usuario> lista = usuarioRepository.findAll();
        return lista.stream()
                .map(UsuarioDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * GET /api/usuarios/{id}
     * Busca um usuário por ID. Se não existir, retorna 404.
     */
    @GetMapping("/{id}")
    public UsuarioDTO buscarPorId(@PathVariable Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id = " + id));
        return UsuarioDTO.fromEntity(u);
    }

    /**
     * POST /api/usuarios
     * Cria um novo usuário. Valida o DTO, converte para entidade, salva e retorna DTO com 201.
     */
    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioDTO novoDTO) {
        // Aqui você pode criptografar a senha antes de salvar, se quiser:
        // String senhaCripto = passwordEncoder.encode(novoDTO.getSenha());
        // novoDTO.setSenha(senhaCripto);

        Usuario user = novoDTO.toEntity();
        Usuario salvo = usuarioRepository.save(user);
        UsuarioDTO respostaDTO = UsuarioDTO.fromEntity(salvo);
        return ResponseEntity.status(201).body(respostaDTO);
    }

    /**
     * PUT /api/usuarios/{id}
     * Atualiza um usuário existente. Se não existir, retorna 404.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO dtoAtualizado
    ) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id = " + id));

        // Se precisar criptografar senha, faça aqui:
        // String senhaCripto = passwordEncoder.encode(dtoAtualizado.getSenha());
        // dtoAtualizado.setSenha(senhaCripto);

        existente = dtoAtualizado.updateEntity(existente);
        Usuario atualizado = usuarioRepository.save(existente);
        UsuarioDTO respostaDTO = UsuarioDTO.fromEntity(atualizado);
        return ResponseEntity.ok(respostaDTO);
    }

    /**
     * DELETE /api/usuarios/{id}
     * Deleta um usuário existente; se não existir, retorna 404.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id = " + id));

        usuarioRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
