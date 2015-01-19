package com.pmrodrigues.gnsnet.models.embeddables;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created by Marceloo on 19/09/2014.
 */
@Embeddable
public class NossoNumero implements Serializable {

    @Column(name = "nossoNumero")
    private Long numero;

    @Column(name = "digitoNossoNumero")
    private String digito;

    public Long getNumero() {
        return numero;
    }

    public String getDigito() {
        return digito;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public void setDigito(String digito) {
        this.digito = digito;
    }
}
