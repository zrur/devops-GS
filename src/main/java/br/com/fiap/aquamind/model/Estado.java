package br.com.fiap.aquamind.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estados")
public class Estado {

    @Id
    @Column(name = "id_estado")
    private Integer id;

    @Column(name = "nome", length = 100, nullable = false, unique = true)
    private String nome;

    @Column(name = "sigla", length = 2, nullable = false, unique = true)
    private String sigla;

    public Estado() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}
