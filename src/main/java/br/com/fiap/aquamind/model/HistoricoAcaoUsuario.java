package br.com.fiap.aquamind.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico_acoes_usuario")
public class HistoricoAcaoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_historico_acoes_usuario")
    @SequenceGenerator(
            name = "seq_historico_acoes_usuario",
            sequenceName = "seq_historico_acoes_usuario",
            allocationSize = 1
    )
    @Column(name = "id_acao")
    private Long id;

    @Column(name = "descricao", length = 400, nullable = false)
    private String descricao;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public HistoricoAcaoUsuario() {

    }


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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    @PrePersist
    public void prePersist() {
        this.dataHora = LocalDateTime.now();
    }
}
