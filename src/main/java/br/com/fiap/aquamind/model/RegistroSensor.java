package br.com.fiap.aquamind.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "registros_sensor")
public class RegistroSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_registros_sensor")
    @SequenceGenerator(
            name = "seq_registros_sensor",
            sequenceName = "seq_registros_sensor",
            allocationSize = 1
    )
    @Column(name = "id_registro")
    private Long id;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "valor", precision = 10, scale = 4, nullable = false)
    private BigDecimal valor;

    @Column(name = "alerta", nullable = false)
    private Boolean alerta = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sensor", nullable = false)
    private Sensor sensor;

    public RegistroSensor() { }

    // GETTERS E SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Boolean getAlerta() {
        return alerta;
    }

    public void setAlerta(Boolean alerta) {
        this.alerta = alerta;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
