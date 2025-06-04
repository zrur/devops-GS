package br.com.fiap.aquamind.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
}
