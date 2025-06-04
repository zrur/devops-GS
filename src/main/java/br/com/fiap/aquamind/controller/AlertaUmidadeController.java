// src/main/java/br/com/fiap/aquamind/controller/AlertaUmidadeController.java
package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.AlertaUmidadeDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.AlertaUmidade;
import br.com.fiap.aquamind.model.Zona;
import br.com.fiap.aquamind.repository.AlertaUmidadeRepository;
import br.com.fiap.aquamind.repository.ZonaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Endpoints REST para CRUD de AlertaUmidade, usando DTO.
 */
@RestController
@RequestMapping("/api/alertas-umidade")
public class AlertaUmidadeController {

    @Autowired
    private AlertaUmidadeRepository alertaRepository;

    @Autowired
    private ZonaRepository zonaRepository;

    @GetMapping
    public List<AlertaUmidadeDTO> listarTodos() {
        List<AlertaUmidade> lista = alertaRepository.findAll();
        return lista.stream()
                .map(AlertaUmidadeDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AlertaUmidadeDTO buscarPorId(@PathVariable Long id) {
        AlertaUmidade a = alertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado com id = " + id));
        return AlertaUmidadeDTO.fromEntity(a);
    }

    @PostMapping
    public ResponseEntity<AlertaUmidadeDTO> criar(@Valid @RequestBody AlertaUmidadeDTO novoDTO) {
        Zona zona = zonaRepository.findById(novoDTO.getIdZona())
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada com id = " + novoDTO.getIdZona()));

        AlertaUmidade a = novoDTO.toEntity();
        a.setZona(zona);

        AlertaUmidade salvo = alertaRepository.save(a);
        AlertaUmidadeDTO respostaDTO = AlertaUmidadeDTO.fromEntity(salvo);
        return ResponseEntity.status(201).body(respostaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertaUmidadeDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AlertaUmidadeDTO dtoAtualizado
    ) {
        AlertaUmidade existente = alertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado com id = " + id));

        Zona zona = zonaRepository.findById(dtoAtualizado.getIdZona())
                .orElseThrow(() -> new ResourceNotFoundException("Zona não encontrada com id = " + dtoAtualizado.getIdZona()));

        existente = dtoAtualizado.updateEntity(existente);
        existente.setZona(zona);

        AlertaUmidade atualizado = alertaRepository.save(existente);
        AlertaUmidadeDTO respostaDTO = AlertaUmidadeDTO.fromEntity(atualizado);
        return ResponseEntity.ok(respostaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        AlertaUmidade existente = alertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado com id = " + id));

        alertaRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
