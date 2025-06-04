// src/main/java/br/com/fiap/aquamind/dto/HistoricoAcaoUsuarioDTO.java
package br.com.fiap.aquamind.dto;

import br.com.fiap.aquamind.model.HistoricoAcaoUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * DTO para a entidade HistoricoAcaoUsuario.
 */
public class HistoricoAcaoUsuarioDTO {

    private Long id;

    @NotBlank(message = "A descrição não pode ficar em branco")
    private String descricao;

    private LocalDateTime dataHora; // preenchido automaticamente no POST

    @NotNull(message = "O idUsuario não pode ser nulo")
    private Long idUsuario;

    public HistoricoAcaoUsuarioDTO() { }

    public HistoricoAcaoUsuarioDTO(Long id, String descricao, LocalDateTime dataHora, Long idUsuario) {
        this.id = id;
        this.descricao = descricao;
        this.dataHora = dataHora;
        this.idUsuario = idUsuario;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Converte uma entidade HistoricoAcaoUsuario em HistoricoAcaoUsuarioDTO.
     */
    public static HistoricoAcaoUsuarioDTO fromEntity(HistoricoAcaoUsuario h) {
        if (h == null) {
            return null;
        }
        Long usuarioId = h.getUsuario() != null ? h.getUsuario().getId() : null;
        return new HistoricoAcaoUsuarioDTO(
                h.getId(),
                h.getDescricao(),
                h.getDataHora(),
                usuarioId
        );
    }

    /**
     * Converte este DTO em uma nova entidade HistoricoAcaoUsuario.
     */
    public HistoricoAcaoUsuario toEntity() {
        HistoricoAcaoUsuario h = new HistoricoAcaoUsuario();
        h.setDescricao(this.descricao);
        return h;
    }

    /**
     * Atualiza os campos de uma entidade existente a partir deste DTO (usado no PUT).
     */
    public HistoricoAcaoUsuario updateEntity(HistoricoAcaoUsuario existente) {
        existente.setDescricao(this.descricao);
        return existente;
    }
}
