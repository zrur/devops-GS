// src/main/java/br/com/fiap/aquamind/service/UsuarioService.java
package br.com.fiap.aquamind.service;

import br.com.fiap.aquamind.exception.ResourceNotFoundException;
import br.com.fiap.aquamind.model.Usuario;
import br.com.fiap.aquamind.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Lista todos os usuários.
     */
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca um usuário pelo ID. Se não existir, lança ResourceNotFoundException.
     */
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario não encontrado com id = " + id));
    }

    /**
     * Cria um novo usuário.
     * (Geralmente, para cadastro normal você chamaria o AuthService.register().
     *  Este método pode ser usado para criar usuários via endpoint administrativo, por exemplo.)
     */
    public Usuario criar(Usuario novoUsuario) {
        // validações: email único, senha criptografada, tipoUsuario válido, etc.
        return usuarioRepository.save(novoUsuario);
    }

    /**
     * Atualiza um usuário existente. Se o ID não existir, lança ResourceNotFoundException.
     */
    public Usuario atualizar(Long id, Usuario dadosAtualizados) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario não encontrado com id = " + id));

        existente.setNome(dadosAtualizados.getNome());
        existente.setEmail(dadosAtualizados.getEmail());
        existente.setSenha(dadosAtualizados.getSenha());
        existente.setTipoUsuario(dadosAtualizados.getTipoUsuario());
        existente.setAtivo(dadosAtualizados.getAtivo());
        // @PreUpdate ajusta dataAtualizacao

        return usuarioRepository.save(existente);
    }

    /**
     * Deleta um usuário. Se o ID não existir, lança ResourceNotFoundException.
     */
    public void deletar(Long id) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario não encontrado com id = " + id));
        usuarioRepository.delete(existente);
    }
}
