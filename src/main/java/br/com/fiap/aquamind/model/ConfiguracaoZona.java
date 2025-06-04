package br.com.fiap.aquamind.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "configuracoes_zona")
public class ConfiguracaoZona {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_configuracoes_zona")
    @SequenceGenerator(
            name = "seq_configuracoes_zona",
            sequenceName = "seq_configuracoes_zona",
            allocationSize = 1
    )
    @Column(name = "id_config")
    private Long id;

    @Column(name = "limite_umidade_min", precision = 5, scale = 2)
    private Double limiteUmidadeMin;

    @Column(name = "horario_inicio_irriga", length = 5)
    private String horarioInicioIrriga; // formato 'HH:MM'

    @Column(name = "horario_fim_irriga", length = 5)
    private String horarioFimIrriga;    // formato 'HH:MM'

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zona", nullable = false)
    private Zona zona;

    public ConfiguracaoZona() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLimiteUmidadeMin() {
        return limiteUmidadeMin;
    }

    public void setLimiteUmidadeMin(Double limiteUmidadeMin) {
        this.limiteUmidadeMin = limiteUmidadeMin;
    }

    public String getHorarioInicioIrriga() {
        return horarioInicioIrriga;
    }

    public void setHorarioInicioIrriga(String horarioInicioIrriga) {
        this.horarioInicioIrriga = horarioInicioIrriga;
    }

    public String getHorarioFimIrriga() {
        return horarioFimIrriga;
    }

    public void setHorarioFimIrriga(String horarioFimIrriga) {
        this.horarioFimIrriga = horarioFimIrriga;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }


    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}
