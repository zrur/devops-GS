package br.com.fiap.aquamind.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "zonas")
public class Zona {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_zonas")
    @SequenceGenerator(
            name = "seq_zonas",
            sequenceName = "seq_zonas",
            allocationSize = 1
    )
    @Column(name = "id_zona")
    private Long id;

    @Column(name = "nome", length = 150, nullable = false)
    private String nome;

    @Column(name = "area_hectares", precision = 10, scale = 2, nullable = false)
    private BigDecimal areaHectares;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_propriedade", nullable = false)
    private Propriedade propriedade;

    @OneToMany(mappedBy = "zona", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sensor> sensores;

    @OneToMany(mappedBy = "zona", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bomba> bombas;

    @OneToOne(mappedBy = "zona", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ConfiguracaoZona configuracaoZona;

    @OneToMany(mappedBy = "zona", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlertaUmidade> alertas;

    public Zona() {
        // Construtor padrão (necessário ao JPA)
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getAreaHectares() {
        return areaHectares;
    }

    public void setAreaHectares(BigDecimal areaHectares) {
        this.areaHectares = areaHectares;
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

    public Propriedade getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(Propriedade propriedade) {
        this.propriedade = propriedade;
    }

    public List<Sensor> getSensores() {
        return sensores;
    }

    public void setSensores(List<Sensor> sensores) {
        this.sensores = sensores;
    }

    public List<Bomba> getBombas() {
        return bombas;
    }

    public void setBombas(List<Bomba> bombas) {
        this.bombas = bombas;
    }

    public ConfiguracaoZona getConfiguracaoZona() {
        return configuracaoZona;
    }

    public void setConfiguracaoZona(ConfiguracaoZona configuracaoZona) {
        this.configuracaoZona = configuracaoZona;
    }

    public List<AlertaUmidade> getAlertas() {
        return alertas;
    }

    public void setAlertas(List<AlertaUmidade> alertas) {
        this.alertas = alertas;
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
