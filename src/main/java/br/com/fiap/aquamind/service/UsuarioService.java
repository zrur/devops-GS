package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.dto.UsuarioDTO;
import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Usuario;
import br.com.fiap.aquamind.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para manipulação de Usuario (CRUD).
 * Recebe e retorna DTOs, valida existência e converte DTO ↔ entidade.
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Lista todos os usuários, convertendo para DTO.
     */
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca um usuário por ID. Se não existir, lança ResourceNotFoundException.
     */
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado com id = " + id));
        return UsuarioDTO.fromEntity(existente);
    }

    /**
     * Cria um novo usuário a partir de DTO.
     * (Se precisar de criptografia de senha, faz aqui.)
     */
    @Transactional
    public UsuarioDTO criar(UsuarioDTO dto) {
        // 1) Verifica e/ou criptografa a senha
        String rawSenha = dto.getSenha();
        dto.setSenha(passwordEncoder.encode(rawSenha));

        // 2) Converte DTO para entidade
        Usuario entidade = dto.toEntity();

        // 3) (Opcional) validar email único, tipoUsuario válido, etc.

        // 4) Salva no repositório
        Usuario salvo = usuarioRepository.save(entidade);

        // 5) Converte de volta para DTO e retorna
        return UsuarioDTO.fromEntity(salvo);
    }

    /**
     * Atualiza um usuário existente. Se o ID não existir, lança ResourceNotFoundException.
     * (Se senha vier no DTO, criptografa novamente.)
     */
    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioDTO dtoAtualizado) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado com id = " + id));

        // 1) Se quiser criptografar senha ao atualizar:
        String rawSenha = dtoAtualizado.getSenha();
        dtoAtualizado.setSenha(passwordEncoder.encode(rawSenha));

        // 2) Atualiza campos da entidade a partir do DTO
        existente = dtoAtualizado.updateEntity(existente);

        // 3) Salva no repositório
        Usuario atualizado = usuarioRepository.save(existente);

        // 4) Converte para DTO e retorna
        return UsuarioDTO.fromEntity(atualizado);
    }

    /**
     * Deleta um usuário. Se o ID não existir, lança ResourceNotFoundException.
     */
    @Transactional
    public void deletar(Long id) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado com id = " + id));
        usuarioRepository.delete(existente);
    }
}
