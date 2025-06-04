package br.com.fiap.aquamind.dto;

import br.com.fiap.aquamind.model.Sensor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * DTO para a entidade Sensor.
 */
public class SensorDTO {

    private Long id;

    @NotNull(message = "O idZona não pode ser nulo")
    private Long idZona;

    @NotBlank(message = "O tipoSensor não pode ficar em branco")
    @Size(max = 50, message = "O tipo do sensor deve ter no máximo 50 caracteres")
    private String tipoSensor; // ex: "umidade", "temperatura", "pressao"

    @Size(max = 100, message = "O modelo deve ter no máximo 100 caracteres")
    private String modelo;

    @NotNull(message = "O campo ativo não pode ser nulo")
    private Boolean ativo;

    @NotNull(message = "A data de instalação não pode ser nula")
    private LocalDate dataInstalacao;

    public SensorDTO() { }

    public SensorDTO(
            Long id,
            Long idZona,
            String tipoSensor,
            String modelo,
            Boolean ativo,
            LocalDate dataInstalacao
    ) {
        this.id = id;
        this.idZona = idZona;
        this.tipoSensor = tipoSensor;
        this.modelo = modelo;
        this.ativo = ativo;
        this.dataInstalacao = dataInstalacao;
    }

    // GETTERS E SETTERS

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

    public String getTipoSensor() {
        return tipoSensor;
    }

    public void setTipoSensor(String tipoSensor) {
        this.tipoSensor = tipoSensor;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDate getDataInstalacao() {
        return dataInstalacao;
    }

    public void setDataInstalacao(LocalDate dataInstalacao) {
        this.dataInstalacao = dataInstalacao;
    }

    /**
     * Converte uma entidade Sensor em SensorDTO.
     */
    public static SensorDTO fromEntity(Sensor sensor) {
        if (sensor == null) {
            return null;
        }
        Long zonaId = null;
        if (sensor.getZona() != null) {
            zonaId = sensor.getZona().getId();
        }
        return new SensorDTO(
                sensor.getId(),
                zonaId,
                sensor.getTipoSensor(),
                sensor.getModelo(),
                sensor.getAtivo(),
                sensor.getDataInstalacao()
        );
    }

    /**
     * Converte este DTO em uma nova entidade Sensor (usado no POST).
     */
    public Sensor toEntity() {
        Sensor s = new Sensor();
        s.setTipoSensor(this.tipoSensor);
        s.setModelo(this.modelo);
        s.setAtivo(this.ativo);
        s.setDataInstalacao(this.dataInstalacao);
        return s;
    }

    /**
     * Atualiza os campos de uma entidade Sensor existente a partir deste DTO (usado no PUT).
     */
    public Sensor updateEntity(Sensor existente) {
        existente.setTipoSensor(this.tipoSensor);
        existente.setModelo(this.modelo);
        existente.setAtivo(this.ativo);
        existente.setDataInstalacao(this.dataInstalacao);
        return existente;
    }
}
