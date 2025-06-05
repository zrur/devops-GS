package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.dto.SensorDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Sensor;
import br.com.fiap.aquamind.model.Zona;
import br.com.fiap.aquamind.repository.SensorRepository;
import br.com.fiap.aquamind.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para manipulação de Sensor (CRUD).
 * Recebe e retorna DTOs, busca Zona, converte DTO ↔ entidade.
 */
@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private ZonaRepository zonaRepository;

    /**
     * Lista todos os sensores convertidos para DTO.
     */
    @Transactional(readOnly = true)
    public List<SensorDTO> listarTodos() {
        List<Sensor> entidades = sensorRepository.findAll();
        return entidades.stream()
                .map(SensorDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca um sensor por ID. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional(readOnly = true)
    public SensorDTO buscarPorId(Long id) {
        Sensor existente = sensorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sensor não encontrado com id = " + id));
        return SensorDTO.fromEntity(existente);
    }

    /**
     * Cria um novo sensor a partir de DTO. Verifica existência de Zona.
     */
    @Transactional
    public SensorDTO criar(SensorDTO dto) {
        // 1) Verifica se a Zona existe
        Long zonaId = dto.getIdZona();
        Zona zona = zonaRepository.findById(zonaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zona não encontrada com id = " + zonaId));

        // 2) Converte DTO em entidade e associa Zona
        Sensor entidade = dto.toEntity();
        entidade.setZona(zona);

        // (Opcional) validações: tipoSensor não vazio, ativo não nulo etc.

        // 3) Salva no banco
        Sensor salvo = sensorRepository.save(entidade);

        // 4) Converte entidade salva para DTO e retorna
        return SensorDTO.fromEntity(salvo);
    }

    /**
     * Atualiza um sensor existente. Verifica existência de Sensor e Zona.
     */
    @Transactional
    public SensorDTO atualizar(Long id, SensorDTO dtoAtualizado) {
        // 1) Busca entidade existente
        Sensor existente = sensorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sensor não encontrado com id = " + id));

        // 2) Verifica se a Zona informada no DTO existe
        Long zonaId = dtoAtualizado.getIdZona();
        Zona zona = zonaRepository.findById(zonaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zona não encontrada com id = " + zonaId));

        // 3) Atualiza campos mutáveis a partir do DTO
        existente = dtoAtualizado.updateEntity(existente);
        existente.setZona(zona);

        // 4) Salva no banco
        Sensor atualizado = sensorRepository.save(existente);

        // 5) Converte para DTO e retorna
        return SensorDTO.fromEntity(atualizado);
    }

    /**
     * Deleta um sensor. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional
    public void deletar(Long id) {
        Sensor existente = sensorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sensor não encontrado com id = " + id));
        sensorRepository.delete(existente);
    }
}
