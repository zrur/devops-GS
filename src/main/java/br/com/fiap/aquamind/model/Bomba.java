package br.com.fiap.aquamind.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bombas")
public class Bomba {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_bombas")
    @SequenceGenerator(
            name = "seq_bombas",
            sequenceName = "seq_bombas",
            allocationSize = 1
    )
    @Column(name = "id_bomba")
    private Long id;

    @Column(name = "modelo", length = 100, nullable = false)
    private String modelo;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_instalacao")
    private LocalDate dataInstalacao;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // RELACIONAMENTO ManyToOne para Zona:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zona", nullable = false)
    private Zona zona;

    // RELACIONAMENTO OneToMany para logs de ação da bomba (caso existam):
    @OneToMany(mappedBy = "bomba", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LogAcaoBomba> logs;

    public Bomba() {
        // Construtor padrão (necessário ao JPA)
    }

    // GETTERS e SETTERS:

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<LogAcaoBomba> getLogs() {
        return logs;
    }

    public void setLogs(List<LogAcaoBomba> logs) {
        this.logs = logs;
    }

    // PrePersist e PreUpdate para “created_at” e “updated_at”:
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
