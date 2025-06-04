package br.com.fiap.aquamind.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * DTO para a entidade RegistroSensor (Leitura de sensor).
 */
public class RegistroSensorDTO {

    private Long id;

    @NotNull(message = "O idSensor não pode ser nulo")
    private Long idSensor;

    @NotNull(message = "A dataHora não pode ser nula")
    private LocalDateTime dataHora;

    @NotNull(message = "O valor da leitura não pode ser nulo")
    private Double valor;

    public RegistroSensorDTO() { }

    public RegistroSensorDTO(Long id, Long idSensor, LocalDateTime dataHora, Double valor) {
        this.id = id;
        this.idSensor = idSensor;
        this.dataHora = dataHora;
        this.valor = valor;
    }

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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
