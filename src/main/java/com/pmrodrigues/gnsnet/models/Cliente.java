package com.pmrodrigues.gnsnet.models;

import com.pmrodrigues.gnsnet.models.embeddables.Endereco;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Marceloo on 02/10/2014.
 */
@Entity
@Table(name = "cliente")
public class Cliente extends Usuario{

    @Column
    private String cpf;

    @Embedded
    private Endereco endereco;

    public Cliente() {
        super();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(final String cpf) {
        this.cpf = cpf;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(final Endereco endereco) {
        this.endereco = endereco;
    }
}
