package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.dto.ZonaDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Zona;
import br.com.fiap.aquamind.model.Propriedade;
import br.com.fiap.aquamind.repository.ZonaRepository;
import br.com.fiap.aquamind.repository.PropriedadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para manipulação de Zona (CRUD).
 * Recebe e retorna DTOs, valida existência de Propriedade, converte DTO ↔ entidade.
 */
@Service
public class ZonaService {

    @Autowired
    private ZonaRepository zonaRepository;

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    /**
     * Lista todas as zonas convertidas para DTO.
     */
    @Transactional(readOnly = true)
    public List<ZonaDTO> listarTodas() {
        return zonaRepository.findAll().stream()
                .map(ZonaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma zona por ID. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional(readOnly = true)
    public ZonaDTO buscarPorId(Long id) {
        Zona existente = zonaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zona não encontrada com id = " + id));
        return ZonaDTO.fromEntity(existente);
    }

    /**
     * Cria uma nova zona a partir de DTO. Verifica existência de Propriedade.
     */
    @Transactional
    public ZonaDTO criar(ZonaDTO dto) {
        // 1) Verifica se a Propriedade existe
        Long propId = dto.getIdPropriedade();
        Propriedade prop = propriedadeRepository.findById(propId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Propriedade não encontrada com id = " + propId));

        // 2) Converte DTO para entidade e associa propriedade
        Zona entidade = dto.toEntity();
        entidade.setPropriedade(prop);

        // (Opcional) validações: areaHectares > 0 etc.

        // 3) Salva no banco
        Zona salvo = zonaRepository.save(entidade);

        // 4) Converte entidade salva para DTO e retorna
        return ZonaDTO.fromEntity(salvo);
    }

    /**
     * Atualiza uma zona existente. Verifica existência de Zona e Propriedade.
     */
    @Transactional
    public ZonaDTO atualizar(Long id, ZonaDTO dtoAtualizado) {
        // 1) Busca o registro existente
        Zona existente = zonaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zona não encontrada com id = " + id));

        // 2) Verifica se a Propriedade informada no DTO existe
        Long propId = dtoAtualizado.getIdPropriedade();
        Propriedade prop = propriedadeRepository.findById(propId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Propriedade não encontrada com id = " + propId));

        // 3) Atualiza campos mutáveis a partir do DTO
        existente = dtoAtualizado.updateEntity(existente);
        existente.setPropriedade(prop);

        // 4) Salva no banco
        Zona atualizado = zonaRepository.save(existente);

        // 5) Converte para DTO e retorna
        return ZonaDTO.fromEntity(atualizado);
    }

    /**
     * Deleta uma zona. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional
    public void deletar(Long id) {
        Zona existente = zonaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zona não encontrada com id = " + id));
        zonaRepository.delete(existente);
    }
}
