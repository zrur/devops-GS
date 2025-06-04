package br.com.fiap.aquamind.dto;

import br.com.fiap.aquamind.model.Estado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para a entidade Estado.
 */
public class EstadoDTO {

    private Long id;

    @NotBlank(message = "O nome do estado não pode ficar em branco")
    @Size(max = 100, message = "Nome do estado deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "A sigla não pode ficar em branco")
    @Size(min = 2, max = 2, message = "A sigla deve ter exatamente 2 caracteres")
    private String sigla;

    public EstadoDTO() { }

    public EstadoDTO(Long id, String nome, String sigla) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
    }

    // GETTERS E SETTERS
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

    public String getSigla() {
        return sigla;
    }
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    /**
     * Converte uma entidade Estado em EstadoDTO.
     */
    public static EstadoDTO fromEntity(Estado estado) {
        if (estado == null) {
            return null;
        }
        return new EstadoDTO(
                estado.getId(),
                estado.getNome(),
                estado.getSigla()
        );
    }

    /**
     * Converte este DTO em uma nova entidade Estado.
     * Usado principalmente no POST (quando criamos um Estado).
     */
    public Estado toEntity() {
        Estado e = new Estado();
        e.setId(this.id);
        e.setNome(this.nome);
        e.setSigla(this.sigla);
        return e;
    }
}
