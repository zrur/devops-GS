// src/main/java/br/com/fiap/aquamind/controller/LogAcaoBombaController.java
package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.LogAcaoBombaDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.LogAcaoBomba;
import br.com.fiap.aquamind.model.Bomba;
import br.com.fiap.aquamind.model.Usuario;
import br.com.fiap.aquamind.repository.LogAcaoBombaRepository;
import br.com.fiap.aquamind.repository.BombaRepository;
import br.com.fiap.aquamind.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Endpoints REST para CRUD de LogAcaoBomba, usando DTO.
 */
@RestController
@RequestMapping("/api/logs-bomba")
public class LogAcaoBombaController {

    @Autowired
    private LogAcaoBombaRepository logAcaoBombaRepository;

    @Autowired
    private BombaRepository bombaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<LogAcaoBombaDTO> listarTodos() {
        List<LogAcaoBomba> lista = logAcaoBombaRepository.findAll();
        return lista.stream()
                .map(LogAcaoBombaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public LogAcaoBombaDTO buscarPorId(@PathVariable Long id) {
        LogAcaoBomba log = logAcaoBombaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Log não encontrado com id = " + id));
        return LogAcaoBombaDTO.fromEntity(log);
    }

    @PostMapping
    public ResponseEntity<LogAcaoBombaDTO> criar(@Valid @RequestBody LogAcaoBombaDTO novoDTO) {
        Bomba bomba = bombaRepository.findById(novoDTO.getIdBomba())
                .orElseThrow(() -> new ResourceNotFoundException("Bomba não encontrada com id = " + novoDTO.getIdBomba()));

        Usuario usuario = usuarioRepository.findById(novoDTO.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id = " + novoDTO.getIdUsuario()));

        LogAcaoBomba log = novoDTO.toEntity();
        log.setBomba(bomba);
        log.setUsuario(usuario);

        LogAcaoBomba salvo = logAcaoBombaRepository.save(log);
        LogAcaoBombaDTO respostaDTO = LogAcaoBombaDTO.fromEntity(salvo);
        return ResponseEntity.status(201).body(respostaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LogAcaoBombaDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody LogAcaoBombaDTO dtoAtualizado
    ) {
        LogAcaoBomba existente = logAcaoBombaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Log não encontrado com id = " + id));

        Bomba bomba = bombaRepository.findById(dtoAtualizado.getIdBomba())
                .orElseThrow(() -> new ResourceNotFoundException("Bomba não encontrada com id = " + dtoAtualizado.getIdBomba()));

        Usuario usuario = usuarioRepository.findById(dtoAtualizado.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id = " + dtoAtualizado.getIdUsuario()));

        existente = dtoAtualizado.updateEntity(existente);
        existente.setBomba(bomba);
        existente.setUsuario(usuario);

        LogAcaoBomba atualizado = logAcaoBombaRepository.save(existente);
        LogAcaoBombaDTO respostaDTO = LogAcaoBombaDTO.fromEntity(atualizado);
        return ResponseEntity.ok(respostaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        LogAcaoBomba existente = logAcaoBombaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Log não encontrado com id = " + id));

        logAcaoBombaRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
