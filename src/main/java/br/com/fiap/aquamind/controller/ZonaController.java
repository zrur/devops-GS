package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.ZonaDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Propriedade;
import br.com.fiap.aquamind.model.Zona;
import br.com.fiap.aquamind.repository.PropriedadeRepository;
import br.com.fiap.aquamind.repository.ZonaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Endpoints REST para CRUD de Zona, usando DTO.
 */
@RestController
@RequestMapping("/api/zonas")
public class ZonaController {

    @Autowired
    private ZonaRepository zonaRepository;

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @GetMapping
    public List<ZonaDTO> listarTodas() {
        List<Zona> lista = zonaRepository.findAll();
        return lista.stream()
                .map(ZonaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ZonaDTO buscarPorId(@PathVariable Long id) {
        Zona z = zonaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada com id = " + id));
        return ZonaDTO.fromEntity(z);
    }

    @PostMapping
    public ResponseEntity<ZonaDTO> criar(@Valid @RequestBody ZonaDTO novaDTO) {
        Propriedade prop = propriedadeRepository.findById(novaDTO.getIdPropriedade())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Propriedade não encontrada com id = " + novaDTO.getIdPropriedade()
                ));

        Zona z = novaDTO.toEntity();
        z.setPropriedade(prop);

        Zona salvo = zonaRepository.save(z);
        ZonaDTO respostaDTO = ZonaDTO.fromEntity(salvo);
        return ResponseEntity.status(201).body(respostaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZonaDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ZonaDTO dtoAtualizado
    ) {
        Zona existente = zonaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada com id = " + id));

        Propriedade prop = propriedadeRepository.findById(dtoAtualizado.getIdPropriedade())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Propriedade não encontrada com id = " + dtoAtualizado.getIdPropriedade()
                ));

        existente = dtoAtualizado.updateEntity(existente);
        existente.setPropriedade(prop);

        Zona atualizado = zonaRepository.save(existente);
        ZonaDTO respostaDTO = ZonaDTO.fromEntity(atualizado);
        return ResponseEntity.ok(respostaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Zona existente = zonaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada com id = " + id));

        zonaRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
