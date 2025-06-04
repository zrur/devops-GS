// src/main/java/br/com/fiap/aquamind/controller/HistoricoAcaoUsuarioController.java
package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.HistoricoAcaoUsuarioDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.HistoricoAcaoUsuario;
import br.com.fiap.aquamind.model.Usuario;
import br.com.fiap.aquamind.repository.HistoricoAcaoUsuarioRepository;
import br.com.fiap.aquamind.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Endpoints REST para CRUD de HistoricoAcaoUsuario, usando DTO.
 */
@RestController
@RequestMapping("/api/historico-acoes")
public class HistoricoAcaoUsuarioController {

    @Autowired
    private HistoricoAcaoUsuarioRepository historicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<HistoricoAcaoUsuarioDTO> listarTodos() {
        List<HistoricoAcaoUsuario> lista = historicoRepository.findAll();
        return lista.stream()
                .map(HistoricoAcaoUsuarioDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public HistoricoAcaoUsuarioDTO buscarPorId(@PathVariable Long id) {
        HistoricoAcaoUsuario h = historicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Histórico não encontrado com id = " + id));
        return HistoricoAcaoUsuarioDTO.fromEntity(h);
    }

    @PostMapping
    public ResponseEntity<HistoricoAcaoUsuarioDTO> criar(@Valid @RequestBody HistoricoAcaoUsuarioDTO novoDTO) {
        Usuario usuario = usuarioRepository.findById(novoDTO.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id = " + novoDTO.getIdUsuario()));

        HistoricoAcaoUsuario h = novoDTO.toEntity();
        h.setUsuario(usuario);

        HistoricoAcaoUsuario salvo = historicoRepository.save(h);
        HistoricoAcaoUsuarioDTO respostaDTO = HistoricoAcaoUsuarioDTO.fromEntity(salvo);
        return ResponseEntity.status(201).body(respostaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoricoAcaoUsuarioDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody HistoricoAcaoUsuarioDTO dtoAtualizado
    ) {
        HistoricoAcaoUsuario existente = historicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Histórico não encontrado com id = " + id));

        Usuario usuario = usuarioRepository.findById(dtoAtualizado.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id = " + dtoAtualizado.getIdUsuario()));

        existente = dtoAtualizado.updateEntity(existente);
        existente.setUsuario(usuario);

        HistoricoAcaoUsuario atualizado = historicoRepository.save(existente);
        HistoricoAcaoUsuarioDTO respostaDTO = HistoricoAcaoUsuarioDTO.fromEntity(atualizado);
        return ResponseEntity.ok(respostaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        HistoricoAcaoUsuario existente = historicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Histórico não encontrado com id = " + id));

        historicoRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
