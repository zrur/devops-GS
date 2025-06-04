package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.RegistroSensor;
import br.com.fiap.aquamind.repository.RegistroSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para CRUD de RegistroSensor (leituras de sensor)
 */
@RestController
@RequestMapping("/api/leituras")  // você pode usar "/api/registro-sensor" se preferir
public class RegistroSensorController {

    @Autowired
    private RegistroSensorRepository registroSensorRepository;

    // GET /api/leituras
    @GetMapping
    public List<RegistroSensor> listarTodas() {
        return registroSensorRepository.findAll();
    }

    // GET /api/leituras/{id}
    @GetMapping("/{id}")
    public RegistroSensor buscarPorId(@PathVariable Long id) {
        return registroSensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leitura não encontrada com id = " + id));
    }

    // POST /api/leituras
    @PostMapping
    public RegistroSensor criar(@RequestBody RegistroSensor novaLeitura) {
        // Validação: idSensor válido, valor >= 0, dataHora não nula etc.
        return registroSensorRepository.save(novaLeitura);
    }

    // PUT /api/leituras/{id}
    @PutMapping("/{id}")
    public RegistroSensor atualizar(
            @PathVariable Long id,
            @RequestBody RegistroSensor dadosAtualizados
    ) {
        RegistroSensor existente = registroSensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leitura não encontrada com id = " + id));

        existente.setIdSensor(dadosAtualizados.getIdSensor());
        existente.setDataHora(dadosAtualizados.getDataHora());
        existente.setValor(dadosAtualizados.getValor());
        // Caso haja campo dataAtualizacao, seria ajustado via @PreUpdate na entidade

        return registroSensorRepository.save(existente);
    }

    // DELETE /api/leituras/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        RegistroSensor existente = registroSensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leitura não encontrada com id = " + id));

        registroSensorRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
