package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.dto.PropriedadeDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Propriedade;
import br.com.fiap.aquamind.model.Usuario;
import br.com.fiap.aquamind.model.Estado;
import br.com.fiap.aquamind.repository.PropriedadeRepository;
import br.com.fiap.aquamind.repository.UsuarioRepository;
import br.com.fiap.aquamind.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para manipulação de Propriedade (CRUD). Recebe e retorna DTOs,
 * valida existência de Usuario e Estado, converte DTO ↔ entidade.
 */
@Service
public class PropriedadeService {

    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    /**
     * Lista todas as propriedades convertidas para DTO.
     */
    @Transactional(readOnly = true)
    public List<PropriedadeDTO> listarTodas() {
        List<Propriedade> entidades = propriedadeRepository.findAll();
        return entidades.stream()
                .map(PropriedadeDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma propriedade por ID. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional(readOnly = true)
    public PropriedadeDTO buscarPorId(Long id) {
        Propriedade existente = propriedadeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Propriedade não encontrada com id = " + id));
        return PropriedadeDTO.fromEntity(existente);
    }

    /**
     * Cria uma nova propriedade a partir de DTO. Verifica existência de Usuario e Estado.
     */
    @Transactional
    public PropriedadeDTO criar(PropriedadeDTO dto) {
        // 1) Verifica se o usuário existe
        Long usuarioId = dto.getIdUsuario();
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado com id = " + usuarioId));

        // 2) Verifica se o estado existe
        Long estadoId = dto.getIdEstado();
        Estado estado = estadoRepository.findById(estadoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Estado não encontrado com id = " + estadoId));

        // 3) Converte DTO para entidade e associa usuário/estado
        Propriedade entidade = dto.toEntity();
        entidade.setUsuario(usuario);
        entidade.setEstado(estado);

        // (Opcional) validador: areaHectares > 0 etc.

        // 4) Salva
        Propriedade salvo = propriedadeRepository.save(entidade);
        return PropriedadeDTO.fromEntity(salvo);
    }

    /**
     * Atualiza uma propriedade existente. Verifica existência de propriedade, usuário e estado.
     */
    @Transactional
    public PropriedadeDTO atualizar(Long id, PropriedadeDTO dtoAtualizado) {
        // 1) Busca a entidade existente ou lança exceção
        Propriedade existente = propriedadeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Propriedade não encontrada com id = " + id));

        // 2) Verifica se o usuário informado no DTO existe
        Long usuarioId = dtoAtualizado.getIdUsuario();
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado com id = " + usuarioId));

        // 3) Verifica se o estado informado no DTO existe
        Long estadoId = dtoAtualizado.getIdEstado();
        Estado estado = estadoRepository.findById(estadoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Estado não encontrado com id = " + estadoId));

        // 4) Atualiza os campos da entidade existente a partir do DTO
        existente = dtoAtualizado.updateEntity(existente);
        existente.setUsuario(usuario);
        existente.setEstado(estado);

        // 5) Salva e converte para DTO
        Propriedade atualizado = propriedadeRepository.save(existente);
        return PropriedadeDTO.fromEntity(atualizado);
    }

    /**
     * Deleta uma propriedade. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional
    public void deletar(Long id) {
        Propriedade existente = propriedadeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Propriedade não encontrada com id = " + id));
        propriedadeRepository.delete(existente);
    }
}
