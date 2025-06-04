package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Sensor;
import br.com.fiap.aquamind.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de Sensor
 */
@RestController
@RequestMapping("/api/sensores")
public class SensorController {

    @Autowired
    private SensorRepository sensorRepository;

    // GET /api/sensores
    @GetMapping
    public List<Sensor> listarTodos() {
        return sensorRepository.findAll();
    }

    // GET /api/sensores/{id}
    @GetMapping("/{id}")
    public Sensor buscarPorId(@PathVariable Long id) {
        return sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado com id = " + id));
    }

    // POST /api/sensores
    @PostMapping
    public Sensor criar(@RequestBody Sensor novoSensor) {
        // Validação de tipo_sensor, id_zona, data_instalacao etc.
        return sensorRepository.save(novoSensor);
    }

    // PUT /api/sensores/{id}
    @PutMapping("/{id}")
    public Sensor atualizar(
            @PathVariable Long id,
            @RequestBody Sensor dadosAtualizados
    ) {
        Sensor existente = sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado com id = " + id));

        existente.setTipoSensor(dadosAtualizados.getTipoSensor());
        existente.setModelo(dadosAtualizados.getModelo());
        existente.setIdZona(dadosAtualizados.getIdZona());
        existente.setAtivo(dadosAtualizados.getAtivo());
        existente.setDataInstalacao(dadosAtualizados.getDataInstalacao());
        // O dataAtualizacao é atualizado via @PreUpdate na entidade

        return sensorRepository.save(existente);
    }

    // DELETE /api/sensores/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Sensor existente = sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado com id = " + id));

        sensorRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
