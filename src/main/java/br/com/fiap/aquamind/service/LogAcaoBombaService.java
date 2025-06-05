package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.LogAcaoBomba;
import br.com.fiap.aquamind.model.Bomba;
import br.com.fiap.aquamind.model.Usuario;
import br.com.fiap.aquamind.repository.LogAcaoBombaRepository;
import br.com.fiap.aquamind.repository.BombaRepository;
import br.com.fiap.aquamind.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogAcaoBombaService {

    @Autowired
    private LogAcaoBombaRepository logRepository;

    @Autowired
    private BombaRepository bombaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Lista todos os logs de ação de bomba.
     */
    public List<LogAcaoBomba> listarTodos() {
        return logRepository.findAll();
    }

    /**
     * Busca um log de ação de bomba pelo ID. Se não existir, lança ResourceNotFoundException.
     */
    public LogAcaoBomba buscarPorId(Long id) {
        return logRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("LogAcaoBomba não encontrado com id = " + id));
    }

    /**
     * Cria um novo log de ação de bomba.
     */
    public LogAcaoBomba criar(LogAcaoBomba novoLog) {
        Long bombaId = (novoLog.getBomba() != null ? novoLog.getBomba().getId() : null);
        Bomba bomba = bombaRepository.findById(bombaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bomba não encontrada com id = " + bombaId));

        Long usuarioId = (novoLog.getUsuario() != null ? novoLog.getUsuario().getId() : null);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario não encontrado com id = " + usuarioId));

        novoLog.setBomba(bomba);
        novoLog.setUsuario(usuario);
        return logRepository.save(novoLog);
    }

    /**
     * Atualiza um log de ação de bomba existente. Se o ID não existir, lança ResourceNotFoundException.
     */
    public LogAcaoBomba atualizar(Long id, LogAcaoBomba dadosAtualizados) {
        LogAcaoBomba existente = logRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("LogAcaoBomba não encontrado com id = " + id));

        Long bombaId = (dadosAtualizados.getBomba() != null ? dadosAtualizados.getBomba().getId() : null);
        Bomba bomba = bombaRepository.findById(bombaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bomba não encontrada com id = " + bombaId));

        Long usuarioId = (dadosAtualizados.getUsuario() != null ? dadosAtualizados.getUsuario().getId() : null);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario não encontrado com id = " + usuarioId));

        existente.setDataHora(dadosAtualizados.getDataHora());
        existente.setAcao(dadosAtualizados.getAcao());
        existente.setBomba(bomba);
        existente.setUsuario(usuario);

        return logRepository.save(existente);
    }

    /**
     * Deleta um log de ação de bomba. Se o ID não existir, lança ResourceNotFoundException.
     */
    public void deletar(Long id) {
        LogAcaoBomba existente = logRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("LogAcaoBomba não encontrado com id = " + id));
        logRepository.delete(existente);
    }
}
