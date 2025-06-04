package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Irrigacao;
import br.com.fiap.aquamind.repository.IrrigacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de Irrigacao
 */
@RestController
@RequestMapping("/api/irrigacoes")
public class IrrigacaoController {

    @Autowired
    private IrrigacaoRepository irrigacaoRepository;

    // GET /api/irrigacoes
    @GetMapping
    public List<Irrigacao> listarTodas() {
        return irrigacaoRepository.findAll();
    }

    // GET /api/irrigacoes/{id}
    @GetMapping("/{id}")
    public Irrigacao buscarPorId(@PathVariable Long id) {
        return irrigacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Irrigacao não encontrada com id = " + id));
    }

    // POST /api/irrigacoes
    @PostMapping
    public Irrigacao criar(@RequestBody Irrigacao novaIrrigacao) {
        // Validações: idZona não nulo, dataHoraFim > dataHoraInicio, volumeAgua >= 0 etc.
        return irrigacaoRepository.save(novaIrrigacao);
    }

    // PUT /api/irrigacoes/{id}
    @PutMapping("/{id}")
    public Irrigacao atualizar(
            @PathVariable Long id,
            @RequestBody Irrigacao dadosAtualizados
    ) {
        Irrigacao existente = irrigacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Irrigacao não encontrada com id = " + id));

        existente.setIdZona(dadosAtualizados.getIdZona());
        existente.setDataHoraInicio(dadosAtualizados.getDataHoraInicio());
        existente.setDataHoraFim(dadosAtualizados.getDataHoraFim());
        existente.setVolumeAgua(dadosAtualizados.getVolumeAgua());
        // O campo dataAtualizacao (se houver) será ajustado via @PreUpdate na entidade

        return irrigacaoRepository.save(existente);
    }

    // DELETE /api/irrigacoes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Irrigacao existente = irrigacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Irrigacao não encontrada com id = " + id));

        irrigacaoRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
