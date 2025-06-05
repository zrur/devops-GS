package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.dto.HistoricoAcaoUsuarioDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.HistoricoAcaoUsuario;
import br.com.fiap.aquamind.model.Usuario;
import br.com.fiap.aquamind.repository.HistoricoAcaoUsuarioRepository;
import br.com.fiap.aquamind.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Camada de serviço para manipular HistoricoAcaoUsuario.
 * Encapsula todas as regras de negócio, busca de Usuario e conversão DTO ↔ entidade.
 */
@Service
public class HistoricoAcaoUsuarioService {

    @Autowired
    private HistoricoAcaoUsuarioRepository historicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Lista todas as ações de usuário como DTOs.
     */
    @Transactional(readOnly = true)
    public List<HistoricoAcaoUsuarioDTO> listarTodas() {
        List<HistoricoAcaoUsuario> entidades = historicoRepository.findAll();
        return entidades.stream()
                .map(HistoricoAcaoUsuarioDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca um histórico de ação por ID. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional(readOnly = true)
    public HistoricoAcaoUsuarioDTO buscarPorId(Long id) {
        HistoricoAcaoUsuario existente = historicoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("HistoricoAcaoUsuario não encontrado com id = " + id));
        return HistoricoAcaoUsuarioDTO.fromEntity(existente);
    }

    /**
     * Cria um novo registro de ação de usuário a partir de DTO.
     * Verifica se o Usuario existe; em seguida, salva.
     */
    @Transactional
    public HistoricoAcaoUsuarioDTO criar(HistoricoAcaoUsuarioDTO novoDTO) {
        Long usuarioId = novoDTO.getIdUsuario();
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario não encontrado com id = " + usuarioId));

        // Converte DTO em entidade e associa o usuário
        HistoricoAcaoUsuario entidade = novoDTO.toEntity();
        entidade.setUsuario(usuario);

        HistoricoAcaoUsuario salvo = historicoRepository.save(entidade);
        return HistoricoAcaoUsuarioDTO.fromEntity(salvo);
    }

    /**
     * Atualiza um registro de ação de usuário. Verifica existência de histórico e usuário.
     */
    @Transactional
    public HistoricoAcaoUsuarioDTO atualizar(Long id, HistoricoAcaoUsuarioDTO dtoAtualizado) {
        HistoricoAcaoUsuario existente = historicoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("HistoricoAcaoUsuario não encontrado com id = " + id));

        Long usuarioId = dtoAtualizado.getIdUsuario();
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario não encontrado com id = " + usuarioId));

        // Atualiza campos da entidade com base no DTO
        existente = dtoAtualizado.updateEntity(existente);
        existente.setUsuario(usuario);

        HistoricoAcaoUsuario atualizado = historicoRepository.save(existente);
        return HistoricoAcaoUsuarioDTO.fromEntity(atualizado);
    }

    /**
     * Deleta um registro de ação de usuário. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional
    public void deletar(Long id) {
        HistoricoAcaoUsuario existente = historicoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("HistoricoAcaoUsuario não encontrado com id = " + id));
        historicoRepository.delete(existente);
    }
}
