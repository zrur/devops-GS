package br.com.fiap.aquamind.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * DTO para a entidade Irrigacao.
 */
public class IrrigacaoDTO {

    private Long id;

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
}
