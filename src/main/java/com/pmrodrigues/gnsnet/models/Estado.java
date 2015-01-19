package com.pmrodrigues.gnsnet.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Marceloo on 02/10/2014.
 */
@Entity
@Table(name = "estado")
public class Estado {

    @Id
    @Column(name = "uf" , unique = true , nullable = false)
    private String uf;

    @Column(name = "nome" , nullable = false)
    private String nome;

    public String getUF() {
        return uf;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof Estado ){
            Estado other = (Estado) obj;
            return this.uf.equalsIgnoreCase(other.uf);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.uf.hashCode();
    }
}
