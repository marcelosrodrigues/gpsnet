package com.pmrodrigues.gnsnet.models.embeddables;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created by Marceloo on 19/09/2014.
 */
@Embeddable
public class Agencia implements Serializable{

    @Column(name = "numeroAgencia")
    private Integer numero;

    @Column(name = "digitoAgencia")
    private String digito;

    public Integer getNumero() {
        return numero;
    }

    public char getDigito() {
        if( !"".equalsIgnoreCase(digito) && digito !=null ) {
            return digito.charAt(0);
        } else {
            return ' ';
        }
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
}
