// src/main/java/br/com/fiap/aquamind/service/RegistroSensorService.java
package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.RegistroSensor;
import br.com.fiap.aquamind.model.Sensor;
import br.com.fiap.aquamind.repository.RegistroSensorRepository;
import br.com.fiap.aquamind.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroSensorService {

    @Autowired
    private RegistroSensorRepository registroSensorRepository;

    @Autowired
    private SensorRepository sensorRepository;

    /**
     * Lista todas as leituras de sensor.
     */
    public List<RegistroSensor> listarTodas() {
        return registroSensorRepository.findAll();
    }

    /**
     * Busca uma leitura de sensor pelo ID. Se não existir, lança ResourceNotFoundException.
     */
    public RegistroSensor buscarPorId(Long id) {
        return registroSensorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("RegistroSensor não encontrado com id = " + id));
    }

    /**
     * Cria uma nova leitura de sensor.
     */
    public RegistroSensor criar(RegistroSensor novaLeitura) {
        Long sensorId = (novaLeitura.getSensor() != null ? novaLeitura.getSensor().getId() : null);
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sensor não encontrado com id = " + sensorId));
        novaLeitura.setSensor(sensor);
        // validações adicionais: valor >= 0, dataHora não nula, etc.
        return registroSensorRepository.save(novaLeitura);
    }

    /**
     * Atualiza uma leitura de sensor. Se o ID não existir, lança ResourceNotFoundException.
     */
    public RegistroSensor atualizar(Long id, RegistroSensor dadosAtualizados) {
        RegistroSensor existente = registroSensorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("RegistroSensor não encontrado com id = " + id));

        Long sensorId = (dadosAtualizados.getSensor() != null ? dadosAtualizados.getSensor().getId() : null);
        Sensor sensor = sensorRepository.findById(sensorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sensor não encontrado com id = " + sensorId));

        existente.setSensor(sensor);
        existente.setDataHora(dadosAtualizados.getDataHora());
        existente.setValor(dadosAtualizados.getValor());
        existente.setAlerta(dadosAtualizados.getAlerta());
        // @PreUpdate ajusta dataAtualizacao, se existir

        return registroSensorRepository.save(existente);
    }

    /**
     * Deleta uma leitura de sensor. Se o ID não existir, lança ResourceNotFoundException.
     */
    public void deletar(Long id) {
        RegistroSensor existente = registroSensorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("RegistroSensor não encontrado com id = " + id));
        registroSensorRepository.delete(existente);
    }
}
