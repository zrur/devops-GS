package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Sensor;
import br.com.fiap.aquamind.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço para manipulação de Sensor (CRUD).
 */
@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    /**
     * Lista todos os sensores.
     */
    public List<Sensor> listarTodos() {
        return sensorRepository.findAll();
    }

    /**
     * Busca um sensor por ID. Se não existir, lança ResourceNotFoundException.
     */
    public Sensor buscarPorId(Long id) {
        return sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado com id = " + id));
    }

    /**
     * Cria um novo sensor.
     */
    public Sensor criar(Sensor novoSensor) {
        // validações: tipoSensor não nulo, idZona válido, dataInstalacao não nula, etc.
        return sensorRepository.save(novoSensor);
    }

    /**
     * Atualiza um sensor existente. Se o ID não existir, lança ResourceNotFoundException.
     */
    public Sensor atualizar(Long id, Sensor dadosAtualizados) {
        Sensor existente = sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado com id = " + id));

        existente.setTipoSensor(dadosAtualizados.getTipoSensor());
        existente.setModelo(dadosAtualizados.getModelo());
        existente.setZona(dadosAtualizados.getZona());
        existente.setAtivo(dadosAtualizados.getAtivo());
        existente.setDataInstalacao(dadosAtualizados.getDataInstalacao());
        // @PreUpdate cuidará de dataAtualizacao

        return sensorRepository.save(existente);
    }

    /**
     * Deleta um sensor. Se o ID não existir, lança ResourceNotFoundException.
     */
    public void deletar(Long id) {
        Sensor existente = sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado com id = " + id));
        sensorRepository.delete(existente);
    }
}
