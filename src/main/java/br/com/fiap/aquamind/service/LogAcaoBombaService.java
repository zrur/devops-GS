package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.dto.LogAcaoBombaDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.LogAcaoBomba;
import br.com.fiap.aquamind.model.Bomba;
import br.com.fiap.aquamind.model.Usuario;
import br.com.fiap.aquamind.repository.LogAcaoBombaRepository;
import br.com.fiap.aquamind.repository.BombaRepository;
import br.com.fiap.aquamind.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para manipulação de LogAcaoBomba (CRUD).
 * Recebe e retorna DTOs, busca Bomba e Usuario, converte DTO ↔ entidade.
 */
@Service
public class LogAcaoBombaService {

    @Autowired
    private LogAcaoBombaRepository logRepository;

    @Autowired
    private BombaRepository bombaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Lista todos os logs de ação de bomba como DTOs.
     */
    @Transactional(readOnly = true)
    public List<LogAcaoBombaDTO> listarTodos() {
        List<LogAcaoBomba> entidades = logRepository.findAll();
        return entidades.stream()
                .map(LogAcaoBombaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca um log de ação de bomba pelo ID. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional(readOnly = true)
    public LogAcaoBombaDTO buscarPorId(Long id) {
        LogAcaoBomba existente = logRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("LogAcaoBomba não encontrado com id = " + id));
        return LogAcaoBombaDTO.fromEntity(existente);
    }

    /**
     * Cria um novo log de ação de bomba a partir de DTO.
     * Verifica se a Bomba e o Usuario existem; em seguida, salva e retorna DTO.
     */
    @Transactional
    public LogAcaoBombaDTO criar(LogAcaoBombaDTO novoDTO) {
        // 1) Verifica se a Bomba existe
        Long bombaId = novoDTO.getIdBomba();
        Bomba bomba = bombaRepository.findById(bombaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bomba não encontrada com id = " + bombaId));

        // 2) Verifica se o Usuario existe
        Long usuarioId = novoDTO.getIdUsuario();
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario não encontrado com id = " + usuarioId));

        // 3) Converte DTO em entidade e associa Bomba e Usuario
        LogAcaoBomba entidade = novoDTO.toEntity();
        entidade.setBomba(bomba);
        entidade.setUsuario(usuario);

        // 4) Salva no banco
        LogAcaoBomba salvo = logRepository.save(entidade);

        // 5) Converte entidade salva para DTO e retorna
        return LogAcaoBombaDTO.fromEntity(salvo);
    }

    /**
     * Atualiza um log de ação de bomba existente. Verifica existência do log, Bomba e Usuario.
     */
    @Transactional
    public LogAcaoBombaDTO atualizar(Long id, LogAcaoBombaDTO dtoAtualizado) {
        // 1) Busca a entidade existente
        LogAcaoBomba existente = logRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("LogAcaoBomba não encontrado com id = " + id));

        // 2) Verifica se a Bomba informada no DTO existe
        Long bombaId = dtoAtualizado.getIdBomba();
        Bomba bomba = bombaRepository.findById(bombaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bomba não encontrada com id = " + bombaId));

        // 3) Verifica se o Usuario informado no DTO existe
        Long usuarioId = dtoAtualizado.getIdUsuario();
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario não encontrado com id = " + usuarioId));

        // 4) Atualiza campos mutáveis da entidade com base no DTO
        existente = dtoAtualizado.updateEntity(existente);
        existente.setBomba(bomba);
        existente.setUsuario(usuario);

        // 5) Salva no banco
        LogAcaoBomba atualizado = logRepository.save(existente);

        // 6) Converte entidade atualizada para DTO e retorna
        return LogAcaoBombaDTO.fromEntity(atualizado);
    }

    /**
     * Deleta um log de ação de bomba pelo ID. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional
    public void deletar(Long id) {
        LogAcaoBomba existente = logRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("LogAcaoBomba não encontrado com id = " + id));
        logRepository.delete(existente);
    }
}
