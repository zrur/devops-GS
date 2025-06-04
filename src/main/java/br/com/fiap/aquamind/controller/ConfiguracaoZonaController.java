// src/main/java/br/com/fiap/aquamind/controller/ConfiguracaoZonaController.java
package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.ConfiguracaoZonaDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.ConfiguracaoZona;
import br.com.fiap.aquamind.model.Zona;
import br.com.fiap.aquamind.repository.ConfiguracaoZonaRepository;
import br.com.fiap.aquamind.repository.ZonaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Endpoints REST para CRUD de ConfiguracaoZona, usando DTO.
 */
@RestController
@RequestMapping("/api/config-zona")
public class ConfiguracaoZonaController {

    @Autowired
    private ConfiguracaoZonaRepository configRepository;

    @Autowired
    private ZonaRepository zonaRepository;

    @GetMapping
    public List<ConfiguracaoZonaDTO> listarTodas() {
        List<ConfiguracaoZona> lista = configRepository.findAll();
        return lista.stream()
                .map(ConfiguracaoZonaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ConfiguracaoZonaDTO buscarPorId(@PathVariable Long id) {
        ConfiguracaoZona cz = configRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuração não encontrada com id = " + id));
        return ConfiguracaoZonaDTO.fromEntity(cz);
    }

    @PostMapping
    public ResponseEntity<ConfiguracaoZonaDTO> criar(@Valid @RequestBody ConfiguracaoZonaDTO novoDTO) {
        Zona zona = zonaRepository.findById(novoDTO.getIdZona())
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada com id = " + novoDTO.getIdZona()));

        ConfiguracaoZona cz = novoDTO.toEntity();
        cz.setZona(zona);

        ConfiguracaoZona salvo = configRepository.save(cz);
        ConfiguracaoZonaDTO respostaDTO = ConfiguracaoZonaDTO.fromEntity(salvo);
        return ResponseEntity.status(201).body(respostaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConfiguracaoZonaDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ConfiguracaoZonaDTO dtoAtualizado
    ) {
        ConfiguracaoZona existente = configRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuração não encontrada com id = " + id));

        Zona zona = zonaRepository.findById(dtoAtualizado.getIdZona())
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada com id = " + dtoAtualizado.getIdZona()));

        existente = dtoAtualizado.updateEntity(existente);
        existente.setZona(zona);

        ConfiguracaoZona atualizado = configRepository.save(existente);
        ConfiguracaoZonaDTO respostaDTO = ConfiguracaoZonaDTO.fromEntity(atualizado);
        return ResponseEntity.ok(respostaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        ConfiguracaoZona existente = configRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuração não encontrada com id = " + id));

        configRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
