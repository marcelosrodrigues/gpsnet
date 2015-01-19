package com.pmrodrigues.gnsnet.models.embeddables;

import com.pmrodrigues.gnsnet.models.Estado;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created by Marceloo on 19/09/2014.
 */
@Embeddable
public class Endereco implements Serializable {

    @Column
    private String logradouro;

    @Column
    private String bairro;

    @Column
    private String cep;

    @Column
    private String cidade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uf" , referencedColumnName = "uf")
    private Estado estado;

    public Endereco() {}

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCep() {
        return cep;
    }

    public String getCidade() {
        return cidade;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setCep(String CEP) {
        this.cep = CEP;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
