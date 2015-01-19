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
public class ContaCorrente implements Serializable {

    @Column(name = "numeroContaCorrente")
    private Long numero;

    @Column(name = "digitoContaCorrente")
    private String digito;

    public Long getNumero() {
        return numero;
    }

    public char getDigito() {
        if( !"".equalsIgnoreCase(digito) && digito !=null ) {
            return digito.charAt(0);
        } else {
            return ' ';
        }
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public void setDigito(String digito) {
        this.digito = digito;
    }
}
