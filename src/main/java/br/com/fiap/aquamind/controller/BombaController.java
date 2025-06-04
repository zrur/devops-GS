package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Bomba;
import br.com.fiap.aquamind.repository.BombaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de Bomba
 */
@RestController
@RequestMapping("/api/bombas")
public class BombaController {

    @Autowired
    private BombaRepository bombaRepository;

    // GET /api/bombas
    @GetMapping
    public List<Bomba> listarTodas() {
        return bombaRepository.findAll();
    }

    // GET /api/bombas/{id}
    @GetMapping("/{id}")
    public Bomba buscarPorId(@PathVariable Long id) {
        return bombaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bomba não encontrada com id = " + id));
    }

    // POST /api/bombas
    @PostMapping
    public Bomba criar(@RequestBody Bomba novaBomba) {
        // Validações: idZona não nulo, modelo não vazio, status entre 'ligada','desligada','manutencao' etc.
        return bombaRepository.save(novaBomba);
    }

    // PUT /api/bombas/{id}
    @PutMapping("/{id}")
    public Bomba atualizar(
            @PathVariable Long id,
            @RequestBody Bomba dadosAtualizados
    ) {
        Bomba existente = bombaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bomba não encontrada com id = " + id));

        existente.setIdZona(dadosAtualizados.getIdZona());
        existente.setModelo(dadosAtualizados.getModelo());
        existente.setStatus(dadosAtualizados.getStatus());
        existente.setAtivo(dadosAtualizados.getAtivo());
        existente.setDataInstalacao(dadosAtualizados.getDataInstalacao());
        // O campo dataAtualizacao será ajustado via @PreUpdate na entidade

        return bombaRepository.save(existente);
    }

    // DELETE /api/bombas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Bomba existente = bombaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bomba não encontrada com id = " + id));

        bombaRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
