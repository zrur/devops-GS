package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Propriedade;
import br.com.fiap.aquamind.repository.PropriedadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de Propriedade
 */
@RestController
@RequestMapping("/api/propriedades")
public class PropriedadeController {

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    // GET /api/propriedades
    @GetMapping
    public List<Propriedade> listarTodas() {
        return propriedadeRepository.findAll();
    }

    // GET /api/propriedades/{id}
    @GetMapping("/{id}")
    public Propriedade buscarPorId(@PathVariable Long id) {
        return propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade não encontrada com id = " + id));
    }

    // POST /api/propriedades
    @PostMapping
    public Propriedade criar(@RequestBody Propriedade novaPropriedade) {
        // Aqui você pode validar campos: nome, idUsuario, idEstado, areaHectares > 0 etc.
        return propriedadeRepository.save(novaPropriedade);
    }

    // PUT /api/propriedades/{id}
    @PutMapping("/{id}")
    public Propriedade atualizar(
            @PathVariable Long id,
            @RequestBody Propriedade dadosAtualizados
    ) {
        Propriedade existente = propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade não encontrada com id = " + id));

        existente.setNome(dadosAtualizados.getNome());
        existente.setIdUsuario(dadosAtualizados.getIdUsuario());
        existente.setIdEstado(dadosAtualizados.getIdEstado());
        existente.setAreaHectares(dadosAtualizados.getAreaHectares());
        existente.setAtivo(dadosAtualizados.getAtivo());
        // O campo dataAtualizacao será ajustado via @PreUpdate na entidade, se houver

        return propriedadeRepository.save(existente);
    }

    // DELETE /api/propriedades/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Propriedade existente = propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade não encontrada com id = " + id));

        propriedadeRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
