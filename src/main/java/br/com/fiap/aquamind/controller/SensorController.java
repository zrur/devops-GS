package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.SensorDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Sensor;
import br.com.fiap.aquamind.model.Zona;
import br.com.fiap.aquamind.repository.SensorRepository;
import br.com.fiap.aquamind.repository.ZonaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Endpoints REST para CRUD de Sensor, usando DTO.
 */
@RestController
@RequestMapping("/api/sensores")
public class SensorController {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private ZonaRepository zonaRepository;

    /**
     * GET /api/sensores
     * Lista todos os sensores como DTOs.
     */
    @GetMapping
    public List<SensorDTO> listarTodos() {
        List<Sensor> lista = sensorRepository.findAll();
        return lista.stream()
                .map(SensorDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * GET /api/sensores/{id}
     * Busca um sensor por ID. Se não encontrar, retorna 404.
     */
    @GetMapping("/{id}")
    public SensorDTO buscarPorId(@PathVariable Long id) {
        Sensor s = sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado com id = " + id));
        return SensorDTO.fromEntity(s);
    }

    /**
     * POST /api/sensores
     * Cria um novo sensor. Valida o DTO, busca Zona, monta entidade e retorna DTO com 201.
     */
    @PostMapping
    public ResponseEntity<SensorDTO> criar(@Valid @RequestBody SensorDTO novoDTO) {
        // 1) Valida existência de Zona
        Zona zona = zonaRepository.findById(novoDTO.getIdZona())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Zona não encontrada com id = " + novoDTO.getIdZona()
                ));

        // 2) Monta a entidade Sensor a partir do DTO
        Sensor sensor = novoDTO.toEntity();
        sensor.setZona(zona);

        // 3) Salva no repositório
        Sensor salvo = sensorRepository.save(sensor);

        // 4) Converte para DTO e retorna 201 Created
        SensorDTO respostaDTO = SensorDTO.fromEntity(salvo);
        return ResponseEntity.status(201).body(respostaDTO);
    }

    /**
     * PUT /api/sensores/{id}
     * Atualiza um sensor existente. Se não existir, retorna 404.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SensorDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody SensorDTO dtoAtualizado
    ) {
        // 1) Busca entidade existente
        Sensor existente = sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado com id = " + id));

        // 2) Valida existência de Zona
        Zona zona = zonaRepository.findById(dtoAtualizado.getIdZona())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Zona não encontrada com id = " + dtoAtualizado.getIdZona()
                ));

        // 3) Atualiza campos na entidade existente via DTO
        existente = dtoAtualizado.updateEntity(existente);
        existente.setZona(zona);

        // 4) Salva e converte para DTO
        Sensor atualizado = sensorRepository.save(existente);
        SensorDTO respostaDTO = SensorDTO.fromEntity(atualizado);
        return ResponseEntity.ok(respostaDTO);
    }

    /**
     * DELETE /api/sensores/{id}
     * Deleta um sensor existente; se não existir, retorna 404.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Sensor existente = sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado com id = " + id));

        sensorRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
