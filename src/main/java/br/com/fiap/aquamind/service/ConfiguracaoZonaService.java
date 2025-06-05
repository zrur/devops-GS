package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.dto.ConfiguracaoZonaDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.ConfiguracaoZona;
import br.com.fiap.aquamind.model.Zona;
import br.com.fiap.aquamind.repository.ConfiguracaoZonaRepository;
import br.com.fiap.aquamind.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Camada de serviço para manipular ConfiguracaoZona.
 * Toda a lógica de buscar Zona, converter DTO ↔ Entidade e lançar exceção fica aqui.
 */
@Service
public class ConfiguracaoZonaService {

    @Autowired
    private ConfiguracaoZonaRepository configuracaoZonaRepository;

    @Autowired
    private ZonaRepository zonaRepository;

    /**
     * Lista todas as configurações de zona como DTOs.
     */
    @Transactional(readOnly = true)
    public List<ConfiguracaoZonaDTO> listarTodas() {
        List<ConfiguracaoZona> entidades = configuracaoZonaRepository.findAll();
        return entidades.stream()
                .map(ConfiguracaoZonaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma configuração de zona pelo ID. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional(readOnly = true)
    public ConfiguracaoZonaDTO buscarPorId(Long id) {
        ConfiguracaoZona existente = configuracaoZonaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("ConfiguracaoZona não encontrada com id = " + id));
        return ConfiguracaoZonaDTO.fromEntity(existente);
    }

    /**
     * Cria uma nova configuração de zona a partir de um DTO. Verifica se a Zona existe;
     * em seguida, salva e retorna o DTO resultante.
     */
    @Transactional
    public ConfiguracaoZonaDTO criar(ConfiguracaoZonaDTO novoDTO) {
        // 1) Verifica se a Zona existe
        Long zonaId = novoDTO.getIdZona();
        Zona zona = zonaRepository.findById(zonaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zona não encontrada com id = " + zonaId));

        // 2) Converte o DTO em entidade e associa a Zona real
        ConfiguracaoZona entidade = novoDTO.toEntity();
        entidade.setZona(zona);

        // 3) Salva e converte de volta para DTO
        ConfiguracaoZona salvo = configuracaoZonaRepository.save(entidade);
        return ConfiguracaoZonaDTO.fromEntity(salvo);
    }

    /**
     * Atualiza uma configuração de zona existente (busca pelo id). Se não existir,
     * lança ResourceNotFoundException. Também verifica se a nova Zona existe.
     */
    @Transactional
    public ConfiguracaoZonaDTO atualizar(Long id, ConfiguracaoZonaDTO dtoAtualizado) {
        // 1) Busca a entidade existente
        ConfiguracaoZona existente = configuracaoZonaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("ConfiguracaoZona não encontrada com id = " + id));

        // 2) Verifica se a Zona informada no DTO existe
        Long zonaId = dtoAtualizado.getIdZona();
        Zona zona = zonaRepository.findById(zonaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zona não encontrada com id = " + zonaId));

        // 3) Atualiza campos da entidade com base no DTO (exceto ID)
        existente = dtoAtualizado.updateEntity(existente);
        existente.setZona(zona);

        // 4) Salva e converte para DTO
        ConfiguracaoZona atualizado = configuracaoZonaRepository.save(existente);
        return ConfiguracaoZonaDTO.fromEntity(atualizado);
    }

    /**
     * Deleta uma configuração de zona. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional
    public void deletar(Long id) {
        ConfiguracaoZona existente = configuracaoZonaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("ConfiguracaoZona não encontrada com id = " + id));
        configuracaoZonaRepository.delete(existente);
    }
}
