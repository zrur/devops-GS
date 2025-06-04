package br.com.fiap.aquamind.dto;

import br.com.fiap.aquamind.model.Zona;
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

    /**
     * Converte uma entidade Zona em ZonaDTO.
     */
    public static ZonaDTO fromEntity(Zona z) {
        if (z == null) {
            return null;
        }
        Long propriedadeId = null;
        if (z.getPropriedade() != null) {
            propriedadeId = z.getPropriedade().getId();
        }
        return new ZonaDTO(
                z.getId(),
                propriedadeId,
                z.getNome(),
                z.getAreaHectares(),
                z.getAtivo()
        );
    }

    /**
     * Converte este DTO em uma nova entidade Zona (usado no POST).
     */
    public Zona toEntity() {
        Zona z = new Zona();
        z.setNome(this.nome);
        z.setAreaHectares(this.areaHectares);
        z.setAtivo(this.ativo);
        return z;
    }

    /**
     * Atualiza os campos de uma entidade Zona existente a partir deste DTO (usado no PUT).
     */
    public Zona updateEntity(Zona existente) {
        existente.setNome(this.nome);
        existente.setAreaHectares(this.areaHectares);
        existente.setAtivo(this.ativo);
        return existente;
    }
}
