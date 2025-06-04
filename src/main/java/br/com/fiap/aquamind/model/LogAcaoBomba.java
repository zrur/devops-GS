package br.com.fiap.aquamind.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs_acao_bomba")
public class LogAcaoBomba {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_logs_acao_bomba")
    @SequenceGenerator(
            name = "seq_logs_acao_bomba",
            sequenceName = "seq_logs_acao_bomba",
            allocationSize = 1
    )
    @Column(name = "id_log")
    private Long id;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "acao", length = 20, nullable = false)
    private String acao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bomba", nullable = false)
    private Bomba bomba;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public LogAcaoBomba() {

    }


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

    public Bomba getBomba() {
        return bomba;
    }

    public void setBomba(Bomba bomba) {
        this.bomba = bomba;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
