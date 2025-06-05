package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.dto.IrrigacaoDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Irrigacao;
import br.com.fiap.aquamind.model.Zona;
import br.com.fiap.aquamind.repository.IrrigacaoRepository;
import br.com.fiap.aquamind.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para manipulação de Irrigacao (CRUD).
 * Recebe e retorna DTOs, faz validações e converte DTO ↔ entidade.
 */
@Service
public class IrrigacaoService {

    @Autowired
    private IrrigacaoRepository irrigacaoRepository;

    @Autowired
    private ZonaRepository zonaRepository;

    /**
     * Lista todas as irrigações, converte para DTO e retorna.
     */
    @Transactional(readOnly = true)
    public List<IrrigacaoDTO> listarTodas() {
        List<Irrigacao> entidades = irrigacaoRepository.findAll();
        return entidades.stream()
                .map(IrrigacaoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca irrigação por ID. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional(readOnly = true)
    public IrrigacaoDTO buscarPorId(Long id) {
        Irrigacao existente = irrigacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Irrigacao não encontrada com id = " + id));
        return IrrigacaoDTO.fromEntity(existente);
    }

    /**
     * Cria uma nova irrigação a partir de DTO.
     * Valida se Zona existe; converte DTO → entidade; salva; converte resultado em DTO.
     */
    @Transactional
    public IrrigacaoDTO criar(IrrigacaoDTO novoDTO) {
        // 1) Verifica se a Zona existe
        Long zonaId = novoDTO.getIdZona();
        Zona zona = zonaRepository.findById(zonaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zona não encontrada com id = " + zonaId));

        // 2) Converte DTO para entidade e associa Zona
        Irrigacao entidade = novoDTO.toEntity();
        entidade.setZona(zona);

        // 3) (Opcional) validar: dataHoraFim > dataHoraInicio, volume >= 0, etc.

        // 4) Salva no banco
        Irrigacao salvo = irrigacaoRepository.save(entidade);

        // 5) Converte entidade salva para DTO e retorna
        return IrrigacaoDTO.fromEntity(salvo);
    }

    /**
     * Atualiza uma irrigação existente (busca pelo id).
     * Se não existir, lança ResourceNotFoundException.
     * Verifica Zona, atualiza campos mutáveis, salva e retorna DTO.
     */
    @Transactional
    public IrrigacaoDTO atualizar(Long id, IrrigacaoDTO dtoAtualizado) {
        // 1) Busca a entidade existente
        Irrigacao existente = irrigacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Irrigacao não encontrada com id = " + id));

        // 2) Verifica se a Zona informada no DTO existe
        Long zonaId = dtoAtualizado.getIdZona();
        Zona zona = zonaRepository.findById(zonaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Zona não encontrada com id = " + zonaId));

        // 3) Atualiza os campos mutáveis da entidade com base no DTO
        existente = dtoAtualizado.updateEntity(existente);
        existente.setZona(zona);

        // 4) (Opcional) validar: dataHoraFim > dataHoraInicio, volume >= 0, etc.

        // 5) Salva no banco
        Irrigacao atualizado = irrigacaoRepository.save(existente);

        // 6) Converte entidade atualizada para DTO e retorna
        return IrrigacaoDTO.fromEntity(atualizado);
    }

    /**
     * Deleta uma irrigação pelo ID. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional
    public void deletar(Long id) {
        Irrigacao existente = irrigacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Irrigacao não encontrada com id = " + id));
        irrigacaoRepository.delete(existente);
    }
}
