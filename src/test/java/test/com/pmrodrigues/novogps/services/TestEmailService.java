package test.com.pmrodrigues.novogps.services;

import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.Datas;
import br.com.caelum.stella.boleto.Emissor;
import br.com.caelum.stella.boleto.Sacado;
import br.com.caelum.stella.boleto.bancos.Itau;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import com.pmrodrigues.gnsnet.models.Usuario;
import com.pmrodrigues.gnsnet.services.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marceloo on 02/10/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-applicationContext.xml")
@TransactionConfiguration(transactionManager = "transactionManager" , defaultRollback = true)
public class TestEmailService {

    @Resource(name = "novoUsuario")
    private EmailService service;

    @Test
    public void trySendASimpleEmail() {

        service.to("marsilvarodrigues@gmail.com")
                .subject("teste")
                .message("teste")
                .send();

    }

    @Test
    public void trySendEmailWithTemplate() {

        Map<String,Object> parameters = new HashMap<>();

        Usuario usuario = new Usuario();
        usuario.setEmail("marsilvarodrigues@gmail.com");
        usuario.setNome("MARCELO");
        usuario.generateNewPassword();

        parameters.put("usuario",usuario);

        service.to("marsilvarodrigues@gmail.com")
                .subject("teste")
                .template("teste.vm",parameters)
                .send();

    }

    @Test
    public void trySendEMailWithAttachment() {

        Boleto boleto =  Boleto.novoBoleto()
                .comValorBoleto(new BigDecimal("223.94"))
                .comNumeroDoDocumento("9096334")
                .comEmissor(Emissor.novoEmissor()
                        .comAgencia(540)
                        .comCarteira(109)
                        .comCedente("TESTE")
                        .comContaCorrente(5055)
                        .comDigitoContaCorrente('6')
                        .comNossoNumero(9096334)
                        .comDigitoNossoNumero("8"))
                .comSacado(Sacado.novoSacado().comBairro("TESTE").comCep("123").comCidade("123").comCpf("123").comEndereco("teste").comNome("TESTE"))
                .comDatas(Datas.novasDatas().comDocumento(1, 2, 2014).comVencimento(1, 1, 2014).comProcessamento(1, 1, 2014))
                .comBanco(new Itau());

        final GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);

        service.to("marsilvarodrigues@gmail.com")
                .subject("teste")
                .message("teste")
                .attachment("boleto.pdf",new ByteArrayInputStream(gerador.geraPDF()))
                .send();

    }

}
