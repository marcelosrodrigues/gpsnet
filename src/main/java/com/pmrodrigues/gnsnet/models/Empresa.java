package com.pmrodrigues.gnsnet.models;

import com.pmrodrigues.gnsnet.models.embeddables.Endereco;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created by Marceloo on 27/09/2014.
 */
@Entity
@Table
public class Empresa implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean bloqueado = false;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Embedded
    private Endereco endereco;

    public boolean isBloqueado() {
        return bloqueado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }
}
