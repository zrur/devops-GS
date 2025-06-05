package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.HistoricoAcaoUsuario;
import br.com.fiap.aquamind.model.Usuario;
import br.com.fiap.aquamind.repository.HistoricoAcaoUsuarioRepository;
import br.com.fiap.aquamind.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricoAcaoUsuarioService {

    @Autowired
    private HistoricoAcaoUsuarioRepository historicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Lista todas as ações de usuário.
     */
    public List<HistoricoAcaoUsuario> listarTodas() {
        return historicoRepository.findAll();
    }

    /**
     * Busca um histórico de ação por ID. Se não existir, lança ResourceNotFoundException.
     */
    public HistoricoAcaoUsuario buscarPorId(Long id) {
        return historicoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("HistoricoAcaoUsuario não encontrado com id = " + id));
    }

    /**
     * Cria um novo registro de ação de usuário.
     */
    public HistoricoAcaoUsuario criar(HistoricoAcaoUsuario novaAcao) {
        // Validações: verifica se o usuário existe antes de salvar
        Long usuarioId = (novaAcao.getUsuario() != null ? novaAcao.getUsuario().getId() : null);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario não encontrado com id = " + usuarioId));
        novaAcao.setUsuario(usuario);
        return historicoRepository.save(novaAcao);
    }

    /**
     * Atualiza um registro de ação de usuário. Se o ID não existir, lança ResourceNotFoundException.
     */
    public HistoricoAcaoUsuario atualizar(Long id, HistoricoAcaoUsuario dadosAtualizados) {
        HistoricoAcaoUsuario existente = historicoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("HistoricoAcaoUsuario não encontrado com id = " + id));

        Long usuarioId = (dadosAtualizados.getUsuario() != null ? dadosAtualizados.getUsuario().getId() : null);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario não encontrado com id = " + usuarioId));

        existente.setDescricao(dadosAtualizados.getDescricao());
        existente.setDataHora(dadosAtualizados.getDataHora());
        existente.setUsuario(usuario);
        // O @PrePersist define dataHora automaticamente ao criar

        return historicoRepository.save(existente);
    }

    /**
     * Deleta um registro de ação de usuário. Se o ID não existir, lança ResourceNotFoundException.
     */
    public void deletar(Long id) {
        HistoricoAcaoUsuario existente = historicoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("HistoricoAcaoUsuario não encontrado com id = " + id));
        historicoRepository.delete(existente);
    }
}
