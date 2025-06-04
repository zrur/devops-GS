package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Usuario;
import br.com.fiap.aquamind.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de Usuario
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // GET /api/usuarios
    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // GET /api/usuarios/{id}
    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id = " + id));
    }

    // POST /api/usuarios
    @PostMapping
    public Usuario criar(@RequestBody Usuario novoUsuario) {
        // Exemplo: aqui você pode criptografar a senha antes de salvar (se não usar AuthController)
        return usuarioRepository.save(novoUsuario);
    }

    // PUT /api/usuarios/{id}
    @PutMapping("/{id}")
    public Usuario atualizar(
            @PathVariable Long id,
            @RequestBody Usuario dadosAtualizados
    ) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id = " + id));

        existente.setNome(dadosAtualizados.getNome());
        existente.setEmail(dadosAtualizados.getEmail());
        existente.setSenha(dadosAtualizados.getSenha());
        existente.setTipoUsuario(dadosAtualizados.getTipoUsuario());
        existente.setAtivo(dadosAtualizados.getAtivo());
        // O campo dataAtualizacao será atualizado automaticamente por um @PreUpdate na entidade (se existir)

        return usuarioRepository.save(existente);
    }

    // DELETE /api/usuarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id = " + id));

        usuarioRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
