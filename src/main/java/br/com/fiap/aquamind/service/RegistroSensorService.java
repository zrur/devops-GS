package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.dto.RegistroSensorDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.RegistroSensor;
import br.com.fiap.aquamind.model.Sensor;
import br.com.fiap.aquamind.repository.RegistroSensorRepository;
import br.com.fiap.aquamind.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para manipulação de RegistroSensor (leituras de sensor) em CRUD.
 * Recebe e retorna DTOs, busca Sensor, converte DTO ↔ entidade.
 */
@Service
public class RegistroSensorService {

    @Autowired
    private RegistroSensorRepository registroSensorRepository;

    @Autowired
    private SensorRepository sensorRepository;

    /**
     * Lista todas as leituras de sensor como DTOs.
     */
    @Transactional(readOnly = true)
    public List<RegistroSensorDTO> listarTodas() {
        List<RegistroSensor> entidades = registroSensorRepository.findAll();
        return entidades.stream()
                .map(RegistroSensorDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma leitura de sensor por ID. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional(readOnly = true)
    public RegistroSensorDTO buscarPorId(Long id) {
        RegistroSensor existente = registroSensorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("RegistroSensor não encontrado com id = " + id));
        return RegistroSensorDTO.fromEntity(existente);
    }

    /**
     * Cria uma nova leitura de sensor a partir de DTO. Verifica existência de Sensor.
     */
    @Transactional
    public RegistroSensorDTO criar(RegistroSensorDTO dto) {
        // 1) Verifica se o Sensor existe
        Long sensorId = dto.getIdSensor();
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sensor não encontrado com id = " + sensorId));

        // 2) Converte DTO para entidade e associa Sensor
        RegistroSensor entidade = dto.toEntity();
        entidade.setSensor(sensor);

        // (Opcional) definir alerta automaticamente, por ex.:
        // entidade.setAlerta(entidade.getValor().compareTo(new BigDecimal("X")) < 0);

        // 3) Salva no banco
        RegistroSensor salvo = registroSensorRepository.save(entidade);

        // 4) Converte para DTO e retorna
        return RegistroSensorDTO.fromEntity(salvo);
    }

    /**
     * Atualiza uma leitura existente. Verifica existência de leitura e Sensor.
     */
    @Transactional
    public RegistroSensorDTO atualizar(Long id, RegistroSensorDTO dtoAtualizado) {
        // 1) Busca entidade existente
        RegistroSensor existente = registroSensorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("RegistroSensor não encontrado com id = " + id));

        // 2) Verifica se o Sensor informado no DTO existe
        Long sensorId = dtoAtualizado.getIdSensor();
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sensor não encontrado com id = " + sensorId));

        // 3) Atualiza campos mutáveis a partir do DTO
        existente = dtoAtualizado.updateEntity(existente);
        existente.setSensor(sensor);

        // (Opcional) recalcular alerta conforme valor etc.

        // 4) Salva no banco
        RegistroSensor atualizado = registroSensorRepository.save(existente);

        // 5) Converte para DTO e retorna
        return RegistroSensorDTO.fromEntity(atualizado);
    }

    /**
     * Deleta uma leitura de sensor. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional
    public void deletar(Long id) {
        RegistroSensor existente = registroSensorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("RegistroSensor não encontrado com id = " + id));
        registroSensorRepository.delete(existente);
    }
}
