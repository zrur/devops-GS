package br.com.fiap.aquamind.dto;

import br.com.fiap.aquamind.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para a entidade Usuario.
 */
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "O nome não pode ficar em branco")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
    private String nome;

    @NotBlank(message = "O e-mail não pode ficar em branco")
    @Email(message = "E-mail inválido")
    @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres")
    private String email;

    @NotBlank(message = "A senha não pode ficar em branco")
    @Size(min = 6, max = 512, message = "A senha deve ter entre 6 e 512 caracteres")
    private String senha;

    @NotBlank(message = "O tipo de usuário não pode ficar em branco")
    @Size(max = 20, message = "O tipo de usuário deve ter no máximo 20 caracteres")
    private String tipoUsuario; // ex: "produtor", "admin", "tecnico"

    @NotNull(message = "O campo ativo não pode ser nulo")
    private Boolean ativo;

    public UsuarioDTO() { }

    public UsuarioDTO(Long id, String nome, String email, String senha, String tipoUsuario, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.ativo = ativo;
    }

    // GETTERS e SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * Converte uma entidade Usuario em UsuarioDTO.
     */
    public static UsuarioDTO fromEntity(Usuario user) {
        if (user == null) {
            return null;
        }
        return new UsuarioDTO(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getSenha(),
                user.getTipoUsuario(),
                user.getAtivo()
        );
    }

    /**
     * Converte este DTO em uma nova entidade Usuario (usado no POST).
     */
    public Usuario toEntity() {
        Usuario u = new Usuario();
        u.setNome(this.nome);
        u.setEmail(this.email);
        u.setSenha(this.senha);
        u.setTipoUsuario(this.tipoUsuario);
        u.setAtivo(this.ativo);
        return u;
    }

    /**
     * Atualiza os campos de uma entidade Usuario existente a partir deste DTO (usado no PUT).
     * Mantém o ID original.
     */
    public Usuario updateEntity(Usuario existente) {
        existente.setNome(this.nome);
        existente.setEmail(this.email);
        existente.setSenha(this.senha);
        existente.setTipoUsuario(this.tipoUsuario);
        existente.setAtivo(this.ativo);
        return existente;
    }
}
