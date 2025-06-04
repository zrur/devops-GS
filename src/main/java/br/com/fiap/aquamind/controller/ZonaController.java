package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Zona;
import br.com.fiap.aquamind.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de Zona
 */
@RestController
@RequestMapping("/api/zonas")
public class ZonaController {

    @Autowired
    private ZonaRepository zonaRepository;

    // GET /api/zonas
    @GetMapping
    public List<Zona> listarTodas() {
        return zonaRepository.findAll();
    }

    // GET /api/zonas/{id}
    @GetMapping("/{id}")
    public Zona buscarPorId(@PathVariable Long id) {
        return zonaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada com id = " + id));
    }

    // POST /api/zonas
    @PostMapping
    public Zona criar(@RequestBody Zona novaZona) {
        // Aqui você pode validar nome, área, idPropriedade não nulos etc.
        return zonaRepository.save(novaZona);
    }

    // PUT /api/zonas/{id}
    @PutMapping("/{id}")
    public Zona atualizar(
            @PathVariable Long id,
            @RequestBody Zona dadosAtualizados
    ) {
        Zona existente = zonaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada com id = " + id));

        existente.setNome(dadosAtualizados.getNome());
        existente.setAreaHectares(dadosAtualizados.getAreaHectares());
        existente.setIdPropriedade(dadosAtualizados.getIdPropriedade());
        existente.setAtivo(dadosAtualizados.getAtivo());
        // O dataAtualizacao é atualizado automaticamente pelo @PreUpdate da entidade

        return zonaRepository.save(existente);
    }

    // DELETE /api/zonas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Zona existente = zonaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada com id = " + id));

        zonaRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
