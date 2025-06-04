package br.com.fiap.aquamind.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import java.time.LocalDateTime;

@Entity
@Table(name = "irrigacao")
public class Irrigacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_irrigacao")
    @SequenceGenerator(
            name = "seq_irrigacao",
            sequenceName = "seq_irrigacao",
            allocationSize = 1
    )
    @Column(name = "id_irrigacao")
    private Long idIrrigacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_zona", nullable = false)
    private Zona zona;

    @Column(name = "data_hora_inicio", nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(name = "data_hora_fim", nullable = false)
    private LocalDateTime dataHoraFim;

    @Column(name = "volume_agua", nullable = false)
    private Double volumeAgua;

    public Irrigacao() { }

    public Long getIdIrrigacao() {
        return idIrrigacao;
    }

    public void setIdIrrigacao(Long idIrrigacao) {
        this.idIrrigacao = idIrrigacao;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
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
