// src/main/java/br/com/fiap/aquamind/dto/LogAcaoBombaDTO.java
package br.com.fiap.aquamind.dto;

import br.com.fiap.aquamind.model.LogAcaoBomba;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * DTO para a entidade LogAcaoBomba.
 */
public class LogAcaoBombaDTO {

    private Long id;

    @NotNull(message = "A dataHora não pode ser nula")
    private LocalDateTime dataHora;

    @NotNull(message = "A ação não pode ser nula")
    @Size(max = 20, message = "A ação deve ter no máximo 20 caracteres")
    private String acao;

    @NotNull(message = "O idBomba não pode ser nulo")
    private Long idBomba;

    @NotNull(message = "O idUsuario não pode ser nulo")
    private Long idUsuario;

    public LogAcaoBombaDTO() { }

    public LogAcaoBombaDTO(Long id, LocalDateTime dataHora, String acao, Long idBomba, Long idUsuario) {
        this.id = id;
        this.dataHora = dataHora;
        this.acao = acao;
        this.idBomba = idBomba;
        this.idUsuario = idUsuario;
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

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public Long getIdBomba() {
        return idBomba;
    }

    public void setIdBomba(Long idBomba) {
        this.idBomba = idBomba;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Converte uma entidade LogAcaoBomba em LogAcaoBombaDTO.
     */
    public static LogAcaoBombaDTO fromEntity(LogAcaoBomba log) {
        if (log == null) {
            return null;
        }
        Long bombaId = log.getBomba() != null ? log.getBomba().getId() : null;
        Long usuarioId = log.getUsuario() != null ? log.getUsuario().getId() : null;
        return new LogAcaoBombaDTO(
                log.getId(),
                log.getDataHora(),
                log.getAcao(),
                bombaId,
                usuarioId
        );
    }

    /**
     * Converte este DTO em uma nova entidade LogAcaoBomba.
     * Senao define relacionamento com Bomba e Usuario, que deve ser feito no Controller.
     */
    public LogAcaoBomba toEntity() {
        LogAcaoBomba log = new LogAcaoBomba();
        log.setDataHora(this.dataHora);
        log.setAcao(this.acao);
        return log;
    }

    /**
     * Atualiza os campos mutáveis de uma entidade existente a partir deste DTO.
     */
    public LogAcaoBomba updateEntity(LogAcaoBomba existente) {
        existente.setDataHora(this.dataHora);
        existente.setAcao(this.acao);
        return existente;
    }
}
