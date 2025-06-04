package br.com.fiap.aquamind.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * DTO para a entidade Propriedade.
 */
public class PropriedadeDTO {

    private Long id;

    @NotBlank(message = "O nome da propriedade não pode ficar em branco")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
    private String nome;

    @NotNull(message = "O idUsuario não pode ser nulo")
    private Long idUsuario;

    @NotNull(message = "O idEstado não pode ser nulo")
    private Long idEstado;

    @NotNull(message = "A área em hectares não pode ser nula")
    @DecimalMin(value = "0.01", message = "A área deve ser maior que zero")
    private BigDecimal areaHectares;

    @NotNull(message = "O campo ativo não pode ser nulo")
    private Boolean ativo;

    public PropriedadeDTO() { }

    public PropriedadeDTO(Long id, String nome, Long idUsuario, Long idEstado, BigDecimal areaHectares, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.idUsuario = idUsuario;
        this.idEstado = idEstado;
        this.areaHectares = areaHectares;
        this.ativo = ativo;
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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
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
