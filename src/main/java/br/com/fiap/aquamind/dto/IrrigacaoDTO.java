package br.com.fiap.aquamind.dto;

import br.com.fiap.aquamind.model.Irrigacao;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * DTO para a entidade Irrigacao.
 */
public class IrrigacaoDTO {

    private Long id;          // corresponde a idIrrigacao na entidade
    @NotNull(message = "O idZona não pode ser nulo")
    private Long idZona;
    @NotNull(message = "A dataHoraInicio não pode ser nula")
    private LocalDateTime dataHoraInicio;
    @NotNull(message = "A dataHoraFim não pode ser nula")
    private LocalDateTime dataHoraFim;
    @NotNull(message = "O volume de água não pode ser nulo")
    @DecimalMin(value = "0.00", message = "O volume de água deve ser maior ou igual a zero")
    private Double volumeAgua;

    public IrrigacaoDTO() { }

    public IrrigacaoDTO(
            Long id,
            Long idZona,
            LocalDateTime dataHoraInicio,
            LocalDateTime dataHoraFim,
            Double volumeAgua
    ) {
        this.id = id;
        this.idZona = idZona;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.volumeAgua = volumeAgua;
    }

    // ================================ GETTERS & SETTERS =================================

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

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public Double getVolumeAgua() {
        return volumeAgua;
    }

    public void setVolumeAgua(Double volumeAgua) {
        this.volumeAgua = volumeAgua;
    }

    // ====================== MÉTODOS DE CONVERSÃO ENTRE ENTIDADE ↔ DTO ======================

    /**
     * Converte uma entidade Irrigacao em IrrigacaoDTO.
     */
    public static IrrigacaoDTO fromEntity(Irrigacao irrigacao) {
        if (irrigacao == null) {
            return null;
        }

        // Se a irrigacao tiver zona associada, pegamos o ID com getId()
        Long zonaId = null;
        if (irrigacao.getZona() != null) {
            zonaId = irrigacao.getZona().getId();  // <-- aqui usamos getId(), não getIdZona()
        }

        return new IrrigacaoDTO(
                irrigacao.getIdIrrigacao(),   // campo id da entidade
                zonaId,
                irrigacao.getDataHoraInicio(),
                irrigacao.getDataHoraFim(),
                irrigacao.getVolumeAgua()
        );
    }

    /**
     * Converte este DTO em uma nova entidade Irrigacao (usado no POST).
     * Atenção: não define a zona aqui; o Controller deverá associá-la chamando setZona(zona).
     */
    public Irrigacao toEntity() {
        Irrigacao i = new Irrigacao();
        i.setDataHoraInicio(this.dataHoraInicio);
        i.setDataHoraFim(this.dataHoraFim);
        i.setVolumeAgua(this.volumeAgua);
        // i.setZona(...) será feito no Controller, após validar ID de Zona.
        return i;
    }

    /**
     * Atualiza os campos mutáveis de uma entidade Irrigacao existente a partir deste DTO (usado no PUT).
     */
    public Irrigacao updateEntity(Irrigacao existente) {
        existente.setDataHoraInicio(this.dataHoraInicio);
        existente.setDataHoraFim(this.dataHoraFim);
        existente.setVolumeAgua(this.volumeAgua);
        // existente.setZona(...) será chamado no Controller para atualizar relacionamento.
        return existente;
    }
}
