package br.com.fiap.aquamind.controller;

import br.com.fiap.aquamind.dto.RegistroSensorDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.RegistroSensor;
import br.com.fiap.aquamind.model.Sensor;
import br.com.fiap.aquamind.repository.RegistroSensorRepository;
import br.com.fiap.aquamind.repository.SensorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Endpoints REST para CRUD de RegistroSensor (leituras de sensor), usando DTO.
 */
@RestController
@RequestMapping("/api/leituras")  // pode ser "/api/registro-sensor" ou outro nome, conforme preferência
public class RegistroSensorController {

    @Autowired
    private RegistroSensorRepository registroSensorRepository;

    @Autowired
    private SensorRepository sensorRepository;

    /**
     * GET /api/leituras
     * Lista todas as leituras como DTOs.
     */
    @GetMapping
    public List<RegistroSensorDTO> listarTodas() {
        List<RegistroSensor> lista = registroSensorRepository.findAll();
        return lista.stream()
                .map(RegistroSensorDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * GET /api/leituras/{id}
     * Busca uma leitura por ID. Se não encontrar, retorna 404.
     */
    @GetMapping("/{id}")
    public RegistroSensorDTO buscarPorId(@PathVariable Long id) {
        RegistroSensor reg = registroSensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leitura não encontrada com id = " + id));
        return RegistroSensorDTO.fromEntity(reg);
    }

    /**
     * POST /api/leituras
     * Cria uma nova leitura de sensor. Valida DTO, busca Sensor, salva e retorna DTO com 201.
     */
    @PostMapping
    public ResponseEntity<RegistroSensorDTO> criar(@Valid @RequestBody RegistroSensorDTO novaDTO) {
        // 1) Valida existência de Sensor
        Sensor sensor = sensorRepository.findById(novaDTO.getIdSensor())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Sensor não encontrado com id = " + novaDTO.getIdSensor()
                ));

        // 2) Monta a entidade RegistroSensor a partir do DTO
        RegistroSensor reg = novaDTO.toEntity();
        reg.setSensor(sensor);
        // O campo alerta pode ser recalculado aqui, por exemplo:
        // reg.setAlerta(reg.getValor().compareTo(new BigDecimal("X")) > 0);
        // Mas, se o DTO contiver 'alerta', mantemos o valor enviado.

        // 3) Salva no repositório
        RegistroSensor salvo = registroSensorRepository.save(reg);

        // 4) Converte para DTO e retorna 201 Created
        RegistroSensorDTO respostaDTO = RegistroSensorDTO.fromEntity(salvo);
        return ResponseEntity.status(201).body(respostaDTO);
    }

    /**
     * PUT /api/leituras/{id}
     * Atualiza uma leitura existente. Se não existir, retorna 404.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RegistroSensorDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody RegistroSensorDTO dtoAtualizado
    ) {
        // 1) Busca a leitura existente
        RegistroSensor existente = registroSensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Leitura não encontrada com id = " + id
                ));

        // 2) Valida se o Sensor informado existe
        Sensor sensor = sensorRepository.findById(dtoAtualizado.getIdSensor())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Sensor não encontrado com id = " + dtoAtualizado.getIdSensor()
                ));

        // 3) Atualiza campos na entidade existente via DTO
        existente = dtoAtualizado.updateEntity(existente);
        existente.setSensor(sensor);
        // Caso precise recalcular 'alerta' aqui, faça:
        // existente.setAlerta(existente.getValor().compareTo(new BigDecimal("X")) > 0);

        // 4) Salva no repositório
        RegistroSensor atualizado = registroSensorRepository.save(existente);

        // 5) Converte para DTO e retorna 200 OK
        RegistroSensorDTO respostaDTO = RegistroSensorDTO.fromEntity(atualizado);
        return ResponseEntity.ok(respostaDTO);
    }

    /**
     * DELETE /api/leituras/{id}
     * Deleta uma leitura existente; se não existir, retorna 404.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        RegistroSensor existente = registroSensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Leitura não encontrada com id = " + id
                ));

        registroSensorRepository.delete(existente);
        return ResponseEntity.noContent().build();
    }
}
