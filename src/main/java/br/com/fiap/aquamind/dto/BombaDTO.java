package br.com.fiap.aquamind.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * DTO para a entidade Bomba.
 */
public class BombaDTO {

    private Long id;

    @NotNull(message = "O idZona não pode ser nulo")
    private Long idZona;

    @NotBlank(message = "O modelo não pode ficar em branco")
    @Size(max = 100, message = "O modelo deve ter no máximo 100 caracteres")
    private String modelo;

    @NotBlank(message = "O status não pode ficar em branco")
    @Size(max = 20, message = "O status deve ter no máximo 20 caracteres")
    private String status; // ex: "ligada", "desligada", "manutencao"

    @NotNull(message = "O campo ativo não pode ser nulo")
    private Boolean ativo;

    @NotNull(message = "A data de instalação não pode ser nula")
    private LocalDate dataInstalacao;

    public BombaDTO() { }

    public BombaDTO(
            Long id,
            Long idZona,
            String modelo,
            String status,
            Boolean ativo,
            LocalDate dataInstalacao
    ) {
        this.id = id;
        this.idZona = idZona;
        this.modelo = modelo;
        this.status = status;
        this.ativo = ativo;
        this.dataInstalacao = dataInstalacao;
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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
