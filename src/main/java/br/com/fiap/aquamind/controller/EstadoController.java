package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.EstadoDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Estado;
import br.com.fiap.aquamind.repository.EstadoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    // GET /api/estados
    @GetMapping
    public List<EstadoDTO> listarTodos() {
        List<Estado> listaEntidades = estadoRepository.findAll();
        return listaEntidades.stream()
                .map(EstadoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // GET /api/estados/{id}
    @GetMapping("/{id}")
    public EstadoDTO buscarPorId(@PathVariable Long id) {
        Estado e = estadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado não encontrado com id = " + id));
        return EstadoDTO.fromEntity(e);
    }

    // POST /api/estados
    @PostMapping
    public ResponseEntity<EstadoDTO> criar(@Valid @RequestBody EstadoDTO novoEstadoDTO) {
        Estado estadoParaSalvar = novoEstadoDTO.toEntity();
        Estado salvo = estadoRepository.save(estadoParaSalvar);
        EstadoDTO respostaDTO = EstadoDTO.fromEntity(salvo);
        return ResponseEntity.status(201).body(respostaDTO);
    }

    // PUT /api/estados/{id}
    @PutMapping("/{id}")
    public ResponseEntity<EstadoDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody EstadoDTO estadoAtualizadoDTO
    ) {
        Estado existente = estadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado não encontrado com id = " + id));

        existente.setNome(estadoAtualizadoDTO.getNome());
        existente.setSigla(estadoAtualizadoDTO.getSigla());
        Estado atualizado = estadoRepository.save(existente);
        EstadoDTO respostaDTO = EstadoDTO.fromEntity(atualizado);
        return ResponseEntity.ok(respostaDTO);
    }

    // DELETE /api/estados/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Estado existente = estadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado não encontrado com id = " + id));

        estadoRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
