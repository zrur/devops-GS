package br.com.fiap.aquamind.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * DTO para a entidade Zona.
 */
public class ZonaDTO {

    private Long id;

    @NotNull(message = "O idPropriedade não pode ser nulo")
    private Long idPropriedade;

    @NotBlank(message = "O nome da zona não pode ficar em branco")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
    private String nome;

    @NotNull(message = "A área em hectares não pode ser nula")
    @DecimalMin(value = "0.01", message = "A área deve ser maior que zero")
    private BigDecimal areaHectares;

    @NotNull(message = "O campo ativo não pode ser nulo")
    private Boolean ativo;

    public ZonaDTO() { }

    public ZonaDTO(Long id, Long idPropriedade, String nome, BigDecimal areaHectares, Boolean ativo) {
        this.id = id;
        this.idPropriedade = idPropriedade;
        this.nome = nome;
        this.areaHectares = areaHectares;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPropriedade() {
        return idPropriedade;
    }

    public void setIdPropriedade(Long idPropriedade) {
        this.idPropriedade = idPropriedade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getAreaHectares() {
        return areaHectares;
    }

    public void setAreaHectares(BigDecimal areaHectares) {
        this.areaHectares = areaHectares;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
