package com.pmrodrigues.gnsnet.webservices;

import com.pmrodrigues.gnsnet.models.Cliente;
import com.pmrodrigues.gnsnet.models.Cobranca;
import com.pmrodrigues.gnsnet.models.Empresa;
import com.pmrodrigues.gnsnet.models.Estado;
import com.pmrodrigues.gnsnet.models.embeddables.Agencia;
import com.pmrodrigues.gnsnet.models.embeddables.ContaCorrente;
import com.pmrodrigues.gnsnet.models.embeddables.Endereco;
import com.pmrodrigues.gnsnet.models.embeddables.NossoNumero;
import com.pmrodrigues.gnsnet.models.enums.Banco;
import com.pmrodrigues.gnsnet.repository.CompanyRepository;
import com.pmrodrigues.gnsnet.services.BillingService;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.text.ParseException;

import static java.lang.String.format;

/**
 * Created by Marceloo on 11/10/2014.
 */
@WebService(name = "boleto" , serviceName = "BoletoWS")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@Service("BoletoWS")
public class BoletoWS {

    private static final Logger logging = Logger.getLogger(BoletoWS.class);

    @Resource
    private WebServiceContext context;

    @WebMethod
    public String emitir(
                         @WebParam(name = "DataVencimento") String dataVencimento, @WebParam(name = "DataEmissao")String dataEmissao,
                         @WebParam(name = "BancoEmissor") String banco,
                         @WebParam(name = "NumeroAgencia")String numeroAgencia, @WebParam(name = "DigitoAgencia")String digitoAgencia,
                         @WebParam(name = "NumeroContaCorrente") String numeroContaCorrente, @WebParam(name = "DigitoContaCorrente")String digitoContaCorrente,
                         @WebParam(name = "NumeroBoleto")String numeroBoleto,@WebParam(name = "NossoNumero") String nossoNumero,
                         @WebParam(name = "DigitoNossoNumero")String digitoNossoNumero, @WebParam(name = "CodigoEmissor") String codigoEmissor,
                         @WebParam(name = "SacadoCPF")String sacadoCPF, @WebParam(name = "NomeSacado")String nomeSacado,
                         @WebParam(name = "EnderecoSacado") String enderecoSacado, @WebParam(name = "BairroSacado") String bairroSacado,
                         @WebParam(name = "CEPSacado") String cepSacado, @WebParam(name = "CidadeSacado") String cidadeSacado,
                         @WebParam(name = "EstadoSacado") String estadoSacado, @WebParam(name = "EmailSacado")String emailSacado,
                         @WebParam(name = "ValorBoleto") String valorBoleto, @WebParam(name = "Carteira") String carteira,
                         @WebParam(name = "Instrucoes") String instrucoes, @WebParam(name = "DataProcessamento") String dataProcessamento) throws ParseException {


        Cobranca cobranca = createCobranca(dataVencimento, dataEmissao, banco, numeroAgencia, numeroContaCorrente, digitoContaCorrente, numeroBoleto, nossoNumero, digitoNossoNumero, codigoEmissor, sacadoCPF, nomeSacado, enderecoSacado, bairroSacado, cepSacado, cidadeSacado, estadoSacado, emailSacado, valorBoleto, carteira, instrucoes, dataProcessamento);

        logging.info(format("emitindo o boleto conforme a cobranca %s",cobranca));

        ServletContext context =
                (ServletContext) this.context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
        BillingService service = (BillingService) WebApplicationContextUtils.getWebApplicationContext(context).getBean("CobrancaService");
        service.enviar(cobranca);
        return cobranca.getStatus().toString();

    }

    private Cobranca createCobranca(String dataVencimento, String dataEmissao, String banco, String numeroAgencia, String numeroContaCorrente, String digitoContaCorrente, String numeroBoleto, String nossoNumero, String digitoNossoNumero, String codigoEmissor, String sacadoCPF, String nomeSacado, String enderecoSacado, String bairroSacado, String cepSacado, String cidadeSacado, String estadoSacado, String emailSacado, String valorBoleto, String carteira, String instrucoes, String dataProcessamento) throws ParseException {
        Cobranca cobranca = new Cobranca();

        ServletContext context =
                (ServletContext) this.context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
        CompanyRepository repository = (CompanyRepository) WebApplicationContextUtils.getWebApplicationContext(context).getBean("CompanyRepository");

        Empresa empresa = repository.findById(Long.parseLong(codigoEmissor));

        if( empresa == null ){
            throw new RuntimeException("Emissor inválido");
        }

        cobranca.setCliente(createCliente(sacadoCPF, nomeSacado, enderecoSacado, bairroSacado, cepSacado, cidadeSacado, estadoSacado, emailSacado, empresa));
        cobranca.setCarteira(Integer.parseInt(carteira));
        cobranca.setDataEmissao(DateUtils.parseDate(dataEmissao, new String[] {"yyyy-MM-dd"}));
        cobranca.setDataProcessamento(DateUtils.parseDate(dataProcessamento, new String[] {"yyyy-MM-dd"}));
        cobranca.setDataVencimento(DateUtils.parseDate(dataVencimento, new String[] {"yyyy-MM-dd"}));
        cobranca.setValorBoleto(NumberUtils.createBigDecimal(valorBoleto));
        cobranca.setNossoNumero(createNossoNumero(nossoNumero, digitoNossoNumero));
        cobranca.setAgencia(createAgencia(numeroAgencia));
        cobranca.setContaCorrente(createContaCorrente(numeroContaCorrente, digitoContaCorrente));
        cobranca.setNumeroDoDocumento(numeroBoleto);
        cobranca.setInstrucoes(instrucoes);
        cobranca.setBanco(Banco.valueOf(banco));
        cobranca.setEmpresa(empresa);
        return cobranca;
    }

    private ContaCorrente createContaCorrente(String numeroContaCorrente, String digitoContaCorrente) {
        ContaCorrente contacorrente = new ContaCorrente();
        contacorrente.setNumero(Long.parseLong(numeroContaCorrente));
        contacorrente.setDigito(digitoContaCorrente);
        return contacorrente;
    }

    private Agencia createAgencia(String numeroAgencia) {
        Agencia agencia = new Agencia();
        agencia.setNumero(Integer.parseInt(numeroAgencia));
        return agencia;
    }

    private NossoNumero createNossoNumero(String nossoNumero, String digitoNossoNumero) {
        NossoNumero nossonumero = new NossoNumero();
        nossonumero.setNumero(Long.parseLong(nossoNumero));
        nossonumero.setDigito(digitoNossoNumero);
        return nossonumero;
    }

    private Cliente createCliente(String sacadoCPF, String nomeSacado, String enderecoSacado, String bairroSacado, String cepSacado, String cidadeSacado, String estadoSacado, String emailSacado, Empresa empresa) {
        Cliente cliente = new Cliente();
        cliente.setNome(nomeSacado);
        cliente.setEmail(emailSacado);
        cliente.setCpf(sacadoCPF);
        cliente.setEmpresa(empresa);

        Endereco endereco = new Endereco();
        endereco.setBairro(bairroSacado);
        endereco.setCep(cepSacado);
        endereco.setLogradouro(enderecoSacado);
        endereco.setCidade(cidadeSacado);
        Estado estado = new Estado();
        estado.setUf(estadoSacado);
        endereco.setEstado(estado);

        cliente.setEndereco(endereco);
        return cliente;
    }
}
