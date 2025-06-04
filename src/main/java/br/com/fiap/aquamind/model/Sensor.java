package br.com.fiap.aquamind.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sensores")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sensores")
    @SequenceGenerator(
            name = "seq_sensores",
            sequenceName = "seq_sensores",
            allocationSize = 1
    )
    @Column(name = "id_sensor")
    private Long id;

    @Column(name = "tipo_sensor", length = 50, nullable = false)
    private String tipoSensor;

    @Column(name = "modelo", length = 100)
    private String modelo;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_instalacao")
    private LocalDate dataInstalacao;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zona", nullable = false)
    private Zona zona;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroSensor> registros;

    public Sensor() {
        // Construtor padrão (necessário ao JPA)
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<RegistroSensor> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroSensor> registros) {
        this.registros = registros;
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
