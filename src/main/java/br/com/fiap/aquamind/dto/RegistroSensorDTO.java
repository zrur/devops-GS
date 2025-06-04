package br.com.fiap.aquamind.dto;

import br.com.fiap.aquamind.model.RegistroSensor;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para a entidade RegistroSensor (Leitura de sensor).
 * Usado para entrada (POST/PUT) e saída (GET).
 */
public class RegistroSensorDTO {

    private Long id;

    @NotNull(message = "O idSensor não pode ser nulo")
    private Long idSensor;

    @NotNull(message = "A dataHora não pode ser nula")
    private LocalDateTime dataHora;

    @NotNull(message = "O valor da leitura não pode ser nulo")
    private BigDecimal valor;

    private Boolean alerta;  // opcional na entrada; retornamos o estado de alerta na saída

    public RegistroSensorDTO() { }

    public RegistroSensorDTO(Long id, Long idSensor, LocalDateTime dataHora, BigDecimal valor, Boolean alerta) {
        this.id = id;
        this.idSensor = idSensor;
        this.dataHora = dataHora;
        this.valor = valor;
        this.alerta = alerta;
    }

    // GETTERS E SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Long idSensor) {
        this.idSensor = idSensor;
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

    /**
     * Converte uma entidade RegistroSensor em RegistroSensorDTO.
     */
    public static RegistroSensorDTO fromEntity(RegistroSensor reg) {
        if (reg == null) {
            return null;
        }
        Long sensorId = null;
        if (reg.getSensor() != null) {
            sensorId = reg.getSensor().getId(); // getId() de Sensor
        }
        return new RegistroSensorDTO(
                reg.getId(),
                sensorId,
                reg.getDataHora(),
                reg.getValor(),
                reg.getAlerta()
        );
    }

    /**
     * Converte este DTO em uma nova entidade RegistroSensor.
     * O campo 'alerta' pode ser calculado depois de salvar, mas aqui mantemos o que foi enviado.
     */
    public RegistroSensor toEntity() {
        RegistroSensor reg = new RegistroSensor();
        reg.setDataHora(this.dataHora);
        reg.setValor(this.valor);
        // Se o DTO contiver 'alerta', usamos; senão fica default definido na entidade.
        if (this.alerta != null) {
            reg.setAlerta(this.alerta);
        }
        return reg;
    }

    /**
     * Atualiza os campos de uma entidade existente a partir deste DTO.
     * Normalmente usado em PUT. Mantém o ID, mas atualiza dataHora, valor e alerta.
     */
    public RegistroSensor updateEntity(RegistroSensor existente) {
        existente.setDataHora(this.dataHora);
        existente.setValor(this.valor);
        if (this.alerta != null) {
            existente.setAlerta(this.alerta);
        }
        return existente;
    }
}
