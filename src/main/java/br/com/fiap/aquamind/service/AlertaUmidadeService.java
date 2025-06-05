package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.dto.AlertaUmidadeDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.AlertaUmidade;
import br.com.fiap.aquamind.model.Zona;
import br.com.fiap.aquamind.repository.AlertaUmidadeRepository;
import br.com.fiap.aquamind.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Camada de serviço para manipular AlertaUmidade.
 * Toda validação, busca de entidade 'Zona' e conversão DTO ↔ entidade ficam aqui.
 */
@Service
public class AlertaUmidadeService {

    @Autowired
    private AlertaUmidadeRepository alertaUmidadeRepository;

    @Autowired
    private ZonaRepository zonaRepository;

    /**
     * Lista todos os alertas de umidade, convertendo-os em DTOs.
     */
    @Transactional(readOnly = true)
    public List<AlertaUmidadeDTO> listarTodas() {
        List<AlertaUmidade> entidades = alertaUmidadeRepository.findAll();
        return entidades.stream()
                .map(AlertaUmidadeDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca um alerta de umidade pelo ID. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional(readOnly = true)
    public AlertaUmidadeDTO buscarPorId(Long id) {
        AlertaUmidade existente = alertaUmidadeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("AlertaUmidade não encontrado com id = " + id));
        return AlertaUmidadeDTO.fromEntity(existente);
    }

    /**
     * Cria um novo alerta de umidade a partir de um DTO. Verifica se a Zona existe; em seguida, salva.
     */
    @Transactional
    public AlertaUmidadeDTO criar(AlertaUmidadeDTO novoDTO) {
        // 1) Verifica se a zona indicada existe
        Zona zona = zonaRepository.findById(novoDTO.getIdZona())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zona não encontrada com id = " + novoDTO.getIdZona()));

        // 2) Converte o DTO para entidade e associa a zona
        AlertaUmidade entidade = novoDTO.toEntity();
        entidade.setZona(zona);

        // 3) Salva e converte de volta para DTO
        AlertaUmidade salvo = alertaUmidadeRepository.save(entidade);
        return AlertaUmidadeDTO.fromEntity(salvo);
    }

    /**
     * Atualiza um alerta de umidade existente. Verifica se o alerta e a zona existem; em seguida, atualiza e salva.
     */
    @Transactional
    public AlertaUmidadeDTO atualizar(Long id, AlertaUmidadeDTO dtoAtualizado) {
        // 1) Busca a entidade existente ou lança exceção
        AlertaUmidade existente = alertaUmidadeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("AlertaUmidade não encontrado com id = " + id));

        // 2) Verifica se a zona informada no DTO existe
        Zona zona = zonaRepository.findById(dtoAtualizado.getIdZona())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zona não encontrada com id = " + dtoAtualizado.getIdZona()));

        // 3) Atualiza os campos da entidade a partir do DTO
        existente = dtoAtualizado.updateEntity(existente);
        existente.setZona(zona);

        // 4) Salva e converte para DTO
        AlertaUmidade atualizado = alertaUmidadeRepository.save(existente);
        return AlertaUmidadeDTO.fromEntity(atualizado);
    }

    /**
     * Deleta um alerta de umidade. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional
    public void deletar(Long id) {
        AlertaUmidade existente = alertaUmidadeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("AlertaUmidade não encontrado com id = " + id));
        alertaUmidadeRepository.delete(existente);
    }
}
