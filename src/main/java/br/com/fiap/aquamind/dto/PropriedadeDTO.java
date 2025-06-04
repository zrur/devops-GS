package br.com.fiap.aquamind.dto;

import br.com.fiap.aquamind.model.Propriedade;
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

    public PropriedadeDTO(
            Long id,
            String nome,
            Long idUsuario,
            Long idEstado,
            BigDecimal areaHectares,
            Boolean ativo
    ) {
        this.id = id;
        this.nome = nome;
        this.idUsuario = idUsuario;
        this.idEstado = idEstado;
        this.areaHectares = areaHectares;
        this.ativo = ativo;
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

    /**
     * Converte uma entidade Propriedade em PropriedadeDTO.
     */
    public static PropriedadeDTO fromEntity(Propriedade p) {
        if (p == null) {
            return null;
        }
        Long usuarioId = null;
        if (p.getUsuario() != null) {
            usuarioId = p.getUsuario().getId();
        }
        Long estadoId = null;
        if (p.getEstado() != null) {
            estadoId = p.getEstado().getId();
        }
        return new PropriedadeDTO(
                p.getId(),
                p.getNome(),
                usuarioId,
                estadoId,
                p.getAreaHectares(),
                p.getAtivo()
        );
    }

    /**
     * Converte este DTO em uma nova entidade Propriedade.
     * Para POST, cria uma instância nova (sem setar ID).
     */
    public Propriedade toEntity() {
        Propriedade p = new Propriedade();
        p.setNome(this.nome);
        p.setAreaHectares(this.areaHectares);
        p.setAtivo(this.ativo);
        return p;
    }

    /**
     * Atualiza os campos de uma entidade existente a partir deste DTO.
     * Por exemplo, em PUT, use:
     *    Propriedade existente = repository.findById(id).orElseThrow(...);
     *    existente = dto.updateEntity(existente);
     */
    public Propriedade updateEntity(Propriedade existente) {
        existente.setNome(this.nome);
        existente.setAreaHectares(this.areaHectares);
        existente.setAtivo(this.ativo);
        return existente;
    }
}
