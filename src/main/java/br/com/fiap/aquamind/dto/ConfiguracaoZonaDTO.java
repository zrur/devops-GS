// src/main/java/br/com/fiap/aquamind/dto/ConfiguracaoZonaDTO.java
package br.com.fiap.aquamind.dto;

import br.com.fiap.aquamind.model.ConfiguracaoZona;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para a entidade ConfiguracaoZona.
 */
public class ConfiguracaoZonaDTO {

    private Long id;

    @NotNull(message = "O idZona não pode ser nulo")
    private Long idZona;

    @NotNull(message = "O limiteUmidadeMin não pode ser nulo")
    @DecimalMin(value = "0.00", message = "O limite deve ser maior ou igual a zero")
    private Double limiteUmidadeMin;

    @Size(max = 5, message = "horarioInicioIrriga deve ter no máximo 5 caracteres")
    private String horarioInicioIrriga; // 'HH:MM'

    @Size(max = 5, message = "horarioFimIrriga deve ter no máximo 5 caracteres")
    private String horarioFimIrriga;    // 'HH:MM'

    @NotNull(message = "O campo ativo não pode ser nulo")
    private Boolean ativo;

    public ConfiguracaoZonaDTO() { }

    public ConfiguracaoZonaDTO(Long id, Long idZona, Double limiteUmidadeMin,
                               String horarioInicioIrriga, String horarioFimIrriga, Boolean ativo) {
        this.id = id;
        this.idZona = idZona;
        this.limiteUmidadeMin = limiteUmidadeMin;
        this.horarioInicioIrriga = horarioInicioIrriga;
        this.horarioFimIrriga = horarioFimIrriga;
        this.ativo = ativo;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdZona() {
        return idZona;
    }

    public void setIdZona(Long idZona) {
        this.idZona = idZona;
    }

    public Double getLimiteUmidadeMin() {
        return limiteUmidadeMin;
    }

    public void setLimiteUmidadeMin(Double limiteUmidadeMin) {
        this.limiteUmidadeMin = limiteUmidadeMin;
    }

    public String getHorarioInicioIrriga() {
        return horarioInicioIrriga;
    }

    public void setHorarioInicioIrriga(String horarioInicioIrriga) {
        this.horarioInicioIrriga = horarioInicioIrriga;
    }

    public String getHorarioFimIrriga() {
        return horarioFimIrriga;
    }

    public void setHorarioFimIrriga(String horarioFimIrriga) {
        this.horarioFimIrriga = horarioFimIrriga;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * Converte entidade ConfiguracaoZona em DTO.
     */
    public static ConfiguracaoZonaDTO fromEntity(ConfiguracaoZona cz) {
        if (cz == null) {
            return null;
        }
        Long zonaId = cz.getZona() != null ? cz.getZona().getId() : null;
        return new ConfiguracaoZonaDTO(
                cz.getId(),
                zonaId,
                cz.getLimiteUmidadeMin(),
                cz.getHorarioInicioIrriga(),
                cz.getHorarioFimIrriga(),
                cz.getAtivo()
        );
    }

    /**
     * Converte este DTO em nova entidade ConfiguracaoZona (usado no POST).
     */
    public ConfiguracaoZona toEntity() {
        ConfiguracaoZona cz = new ConfiguracaoZona();
        cz.setLimiteUmidadeMin(this.limiteUmidadeMin);
        cz.setHorarioInicioIrriga(this.horarioInicioIrriga);
        cz.setHorarioFimIrriga(this.horarioFimIrriga);
        cz.setAtivo(this.ativo);
        return cz;
    }

    /**
     * Atualiza campos mutáveis de uma entidade existente a partir deste DTO (usado no PUT).
     */
    public ConfiguracaoZona updateEntity(ConfiguracaoZona existente) {
        existente.setLimiteUmidadeMin(this.limiteUmidadeMin);
        existente.setHorarioInicioIrriga(this.horarioInicioIrriga);
        existente.setHorarioFimIrriga(this.horarioFimIrriga);
        existente.setAtivo(this.ativo);
        return existente;
    }
}
