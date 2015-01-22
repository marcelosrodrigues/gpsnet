package com.pmrodrigues.gnsnet.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Marceloo on 21/01/2015.
 */
@Entity
@Table
public class Operadora implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

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
}
