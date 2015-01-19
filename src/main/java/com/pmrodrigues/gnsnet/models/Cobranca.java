package com.pmrodrigues.gnsnet.models;

import com.pmrodrigues.gnsnet.models.embeddables.Agencia;
import com.pmrodrigues.gnsnet.models.embeddables.ContaCorrente;
import com.pmrodrigues.gnsnet.models.embeddables.NossoNumero;
import com.pmrodrigues.gnsnet.models.enums.Banco;
import com.pmrodrigues.gnsnet.models.enums.Status;

import javax.naming.NamingException;
import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Marceloo on 02/10/2014.
 */
@Entity
@Table(name = "cobranca",uniqueConstraints = @UniqueConstraint(columnNames = {"numeroDoDocumento" , "banco"}))
public class Cobranca implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name="empresa_id",referencedColumnName = "id")
    private Empresa empresa;

    @ManyToOne(optional = false,fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name="cliente_id",referencedColumnName = "id")
    private Cliente cliente;

    @Column
    private BigDecimal valorBoleto;

    @Column
    private Integer carteira;

    @Column
    private Date dataVencimento;

    @Column
    private Date dataEmissao;

    @Column
    private Date dataProcessamento;

    @Column
    private String instrucoes;

    @Embedded
    private Agencia agencia;

    @Embedded
    private ContaCorrente contaCorrente;


    @Embedded
    private NossoNumero nossoNumero;

    @Column(nullable = false)
    private String numeroDoDocumento;

    @Enumerated
    private Banco banco;

    @Enumerated
    private Status status;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public Integer getCarteira() {
        return carteira;
    }

    public void setCarteira(final Integer carteira) {
        this.carteira = carteira;
    }

    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }

    public NossoNumero getNossoNumero() {
        return nossoNumero;
    }

    public void setNossoNumero(NossoNumero nossoNumero) {
        this.nossoNumero = nossoNumero;
    }

    public BigDecimal getValorBoleto() {
        return valorBoleto;
    }

    public void setValorBoleto(BigDecimal valorBoleto) {
        this.valorBoleto = valorBoleto;
    }

    public String getNumeroDoDocumento() {
        return numeroDoDocumento;
    }

    public void setNumeroDoDocumento(String numeroDoDocumento) {
        this.numeroDoDocumento = numeroDoDocumento;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(Date dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    public void setContaCorrente(ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public void setInstrucoes(String instrucoes) {
        this.instrucoes = instrucoes;
    }

    public String getInstrucoes() {
        return instrucoes;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }
}
