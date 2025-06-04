// src/main/java/br/com/fiap/aquamind/dto/AlertaUmidadeDTO.java
package br.com.fiap.aquamind.dto;

import br.com.fiap.aquamind.model.AlertaUmidade;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * DTO para a entidade AlertaUmidade.
 */
public class AlertaUmidadeDTO {

    private Long id;

    @NotNull(message = "A dataHora não pode ser nula")
    private LocalDateTime dataHora;

    @NotNull(message = "A umidadeAtual não pode ser nula")
    private Double umidadeAtual;

    @Size(max = 400, message = "A descrição deve ter no máximo 400 caracteres")
    private String descricao;

    @NotNull(message = "O campo resolvido não pode ser nulo")
    private Boolean resolvido;

    @NotNull(message = "O idZona não pode ser nulo")
    private Long idZona;

    public AlertaUmidadeDTO() { }

    public AlertaUmidadeDTO(Long id, LocalDateTime dataHora, Double umidadeAtual,
                            String descricao, Boolean resolvido, Long idZona) {
        this.id = id;
        this.dataHora = dataHora;
        this.umidadeAtual = umidadeAtual;
        this.descricao = descricao;
        this.resolvido = resolvido;
        this.idZona = idZona;
    }

    // Getters e setters
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

    public Double getUmidadeAtual() {
        return umidadeAtual;
    }

    public void setUmidadeAtual(Double umidadeAtual) {
        this.umidadeAtual = umidadeAtual;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getResolvido() {
        return resolvido;
    }

    public void setResolvido(Boolean resolvido) {
        this.resolvido = resolvido;
    }

    public Long getIdZona() {
        return idZona;
    }

    public void setIdZona(Long idZona) {
        this.idZona = idZona;
    }

    /**
     * Converte uma entidade AlertaUmidade em AlertaUmidadeDTO.
     */
    public static AlertaUmidadeDTO fromEntity(AlertaUmidade a) {
        if (a == null) {
            return null;
        }
        Long zonaId = a.getZona() != null ? a.getZona().getId() : null;
        return new AlertaUmidadeDTO(
                a.getId(),
                a.getDataHora(),
                a.getUmidadeAtual(),
                a.getDescricao(),
                a.getResolvido(),
                zonaId
        );
    }

    /**
     * Converte este DTO em uma nova entidade AlertaUmidade (usado no POST).
     */
    public AlertaUmidade toEntity() {
        AlertaUmidade a = new AlertaUmidade();
        a.setDataHora(this.dataHora);
        a.setUmidadeAtual(this.umidadeAtual);
        a.setDescricao(this.descricao);
        a.setResolvido(this.resolvido);
        return a;
    }

    /**
     * Atualiza campos mutáveis de uma entidade existente a partir deste DTO (usado no PUT).
     */
    public AlertaUmidade updateEntity(AlertaUmidade existente) {
        existente.setDataHora(this.dataHora);
        existente.setUmidadeAtual(this.umidadeAtual);
        existente.setDescricao(this.descricao);
        existente.setResolvido(this.resolvido);
        return existente;
    }
}
