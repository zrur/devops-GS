package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Estado;
import br.com.fiap.aquamind.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de Estado
 */
@RestController
@RequestMapping("/api/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    // GET /api/estados
    @GetMapping
    public List<Estado> listarTodos() {
        return estadoRepository.findAll();
    }

    // GET /api/estados/{id}
    @GetMapping("/{id}")
    public Estado buscarPorId(@PathVariable Long id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado não encontrado com id = " + id));
    }

    // POST /api/estados
    @PostMapping
    public Estado criar(@RequestBody Estado novoEstado) {
        // validações mínimas: nome e sigla não nulos
        return estadoRepository.save(novoEstado);
    }

    // PUT /api/estados/{id}
    @PutMapping("/{id}")
    public Estado atualizar(
            @PathVariable Long id,
            @RequestBody Estado dadosAtualizados
    ) {
        Estado existente = estadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado não encontrado com id = " + id));

        existente.setNome(dadosAtualizados.getNome());
        existente.setSigla(dadosAtualizados.getSigla());

        return estadoRepository.save(existente);
    }

    // DELETE /api/estados/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Estado existente = estadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado não encontrado com id = " + id));

        estadoRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
