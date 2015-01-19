package com.pmrodrigues.gnsnet.services;

import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Emissor;
import br.com.caelum.stella.boleto.Sacado;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import com.pmrodrigues.gnsnet.models.Cliente;
import com.pmrodrigues.gnsnet.models.Cobranca;
import com.pmrodrigues.gnsnet.models.Empresa;
import com.pmrodrigues.gnsnet.models.enums.Status;
import com.pmrodrigues.gnsnet.repository.BillingRepository;
import com.pmrodrigues.gnsnet.repository.ClientRepository;
import com.pmrodrigues.gnsnet.repository.CompanyRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * Created by Marceloo on 02/10/2014.
 */
@Service("CobrancaService")
public class BillingService {

    private static final String BOLETO_TEMPLATE = "template/billing-template.vm";

    @Resource(name = "CompanyRepository")
    private CompanyRepository companyRepository;

    @Resource(name = "ClientRepository")
    private ClientRepository clientRepository;

    @Resource(name = "BillingRepository")
    private BillingRepository billingRepository;

    @Resource(name = "boleto")
    private EmailService emailService;

    private static final Logger logging = Logger.getLogger(BillingService.class);

    @Transactional
    public void enviar(final Cobranca cobranca) {

        logging.info(format("Enviando a cobranca para o cliente %s",cobranca.getCliente().getNome()));

        final Cliente cliente = clientRepository.findByCPF(cobranca.getCliente().getCpf());
        final Empresa empresa = companyRepository.findById(cobranca.getEmpresa().getId());

        if( cliente != null ) {
            cobranca.setCliente(cliente);
        } else {
            clientRepository.add(cobranca.getCliente());
        }
        cobranca.setEmpresa(empresa);

        final Cobranca existed = billingRepository.findBillingByNumDoc(cobranca.getNumeroDoDocumento(),cobranca.getBanco());

        if( existed == null ) {
            billingRepository.add(cobranca);
        } else {
            existed.setAgencia(cobranca.getAgencia());
            existed.setBanco(cobranca.getBanco());
            existed.setCarteira(cobranca.getCarteira());
            existed.setContaCorrente(cobranca.getContaCorrente());
            existed.setDataEmissao(cobranca.getDataEmissao());
            existed.setDataProcessamento(cobranca.getDataProcessamento());
            existed.setDataVencimento(cobranca.getDataVencimento());
            existed.setInstrucoes(cobranca.getInstrucoes());
            existed.setNossoNumero(cobranca.getNossoNumero());
            existed.setValorBoleto(cobranca.getValorBoleto());
            existed.setCliente(cobranca.getCliente());
            billingRepository.set(existed);
        }

        doSend(cobranca);

    }

    private void doSend(Cobranca cobranca) {
        Boleto boleto = this.generate(cobranca);
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("cobranca", cobranca);

        emailService.to(cobranca.getCliente().getEmail())
                    .subject("Segue anexo seu boleto")
                    .template(BillingService.BOLETO_TEMPLATE,parameters)
                    .attachment("boleto.pdf", generateFile(boleto))
                    .send();

        cobranca.setStatus(Status.SUCESSO);
    }

    private InputStream generateFile(Boleto boleto) {
        final GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);
        return new ByteArrayInputStream(gerador.geraPDF());
    }

    private Boleto generate(final Cobranca cobranca){
        Boleto boleto =  Boleto.novoBoleto()
                .comValorBoleto(cobranca.getValorBoleto())
                .comNumeroDoDocumento(cobranca.getNumeroDoDocumento())
                .comEmissor(this.criarEmissor(cobranca))
                .comSacado(this.criarSacado(cobranca))
                .comDatas(this.criarDataVencimento(cobranca))
                .comBanco(cobranca.getBanco().getBancoEmissor());

        String[] instrucoes = cobranca.getInstrucoes().split("#");
        boleto.comInstrucoes(instrucoes);

        return boleto;
    }

    private Datas criarDataVencimento(final Cobranca cobranca) {
        Calendar vencimento = Calendar.getInstance();
        vencimento.setTime(cobranca.getDataVencimento());

        Calendar emissao = Calendar.getInstance();
        emissao.setTime(cobranca.getDataEmissao());

        Calendar processamento = Calendar.getInstance();
        processamento.setTime(cobranca.getDataProcessamento());

        return Datas.novasDatas()
                .comVencimento(vencimento)
                .comDocumento(emissao)
                .comProcessamento(processamento);
    }

    private Sacado criarSacado(Cobranca cobranca) {
        return Sacado.novoSacado()
                .comCpf(cobranca.getCliente().getCpf())
                .comNome(cobranca.getCliente().getNome())
                .comEndereco(cobranca.getCliente().getEndereco().getLogradouro())
                .comBairro(cobranca.getCliente().getEndereco().getBairro())
                .comCep(cobranca.getCliente().getEndereco().getCep())
                .comCidade(cobranca.getCliente().getEndereco().getCidade())
                .comUf(cobranca.getCliente().getEndereco().getEstado().getUF());
    }

    private Emissor criarEmissor(final Cobranca cobranca) {
        return Emissor.novoEmissor()
                .comCedente(cobranca.getEmpresa().getNome())
                .comAgencia(cobranca.getAgencia().getNumero())
                .comDigitoAgencia(cobranca.getAgencia().getDigito())
                .comCarteira(cobranca.getCarteira())
                .comContaCorrente(cobranca.getContaCorrente().getNumero())
                .comDigitoContaCorrente(cobranca.getContaCorrente().getDigito())
                .comNossoNumero(cobranca.getNossoNumero().getNumero())
                .comDigitoNossoNumero(cobranca.getNossoNumero().getDigito());
    }


}
