package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.dto.EstadoDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Estado;
import br.com.fiap.aquamind.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Camada de serviço para manipular Estado.
 * Toda lógica de conversão DTO ↔ entidade e tratamento de exceções fica aqui.
 */
@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    /**
     * Lista todos os estados convertidos para DTO.
     */
    @Transactional(readOnly = true)
    public List<EstadoDTO> listarTodos() {
        List<Estado> entidades = estadoRepository.findAll();
        return entidades.stream()
                .map(EstadoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca um estado por ID. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional(readOnly = true)
    public EstadoDTO buscarPorId(Long id) {
        Estado existente = estadoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Estado não encontrado com id = " + id));
        return EstadoDTO.fromEntity(existente);
    }

    /**
     * Cria um novo estado a partir de um DTO.
     */
    @Transactional
    public EstadoDTO criar(EstadoDTO novoDTO) {
        // Converte DTO em entidade
        Estado entidade = novoDTO.toEntity();
        Estado salvo = estadoRepository.save(entidade);
        return EstadoDTO.fromEntity(salvo);
    }

    /**
     * Atualiza um estado existente. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional
    public EstadoDTO atualizar(Long id, EstadoDTO dtoAtualizado) {
        Estado existente = estadoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Estado não encontrado com id = " + id));

        // Atualiza campos da entidade com base no DTO
        existente.setNome(dtoAtualizado.getNome());
        existente.setSigla(dtoAtualizado.getSigla());

        Estado atualizado = estadoRepository.save(existente);
        return EstadoDTO.fromEntity(atualizado);
    }

    /**
     * Deleta um estado. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional
    public void deletar(Long id) {
        Estado existente = estadoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Estado não encontrado com id = " + id));
        estadoRepository.delete(existente);
    }
}
