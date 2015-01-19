package com.pmrodrigues.gnsnet.models.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * Created by Marceloo on 02/10/2014.
 */
public enum Banco {

    BANCO_DO_BRASIL("br.com.caelum.stella.boleto.bancos.BancoDoBrasil"),
    BRADESCO("br.com.caelum.stella.boleto.bancos.Bradesco"),
    CAIXA("br.com.caelum.stella.boleto.bancos.Caixa"),
    HSBC("br.com.caelum.stella.boleto.bancos.HSBC"),
    ITAU("br.com.caelum.stella.boleto.bancos.Itau"),
    SAFRA("br.com.caelum.stella.boleto.bancos.Safra"),
    SANTANDER("br.com.caelum.stella.boleto.bancos.Santander");

    private String clazz;

    private Banco(String clazz) {
        this.clazz = clazz;
    }

    public br.com.caelum.stella.boleto.Banco getBancoEmissor()  {
        try {
            return (br.com.caelum.stella.boleto.Banco) Class.forName(this.clazz).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
