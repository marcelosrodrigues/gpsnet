package test.com.pmrodrigues.novogps.services;

import com.pmrodrigues.gnsnet.models.Cliente;
import com.pmrodrigues.gnsnet.models.Cobranca;
import com.pmrodrigues.gnsnet.models.Empresa;
import com.pmrodrigues.gnsnet.models.Estado;
import com.pmrodrigues.gnsnet.models.embeddables.Agencia;
import com.pmrodrigues.gnsnet.models.embeddables.ContaCorrente;
import com.pmrodrigues.gnsnet.models.embeddables.Endereco;
import com.pmrodrigues.gnsnet.models.embeddables.NossoNumero;
import com.pmrodrigues.gnsnet.models.enums.Banco;
import com.pmrodrigues.gnsnet.repository.BillingRepository;
import com.pmrodrigues.gnsnet.repository.ClientRepository;
import com.pmrodrigues.gnsnet.repository.CompanyRepository;
import com.pmrodrigues.gnsnet.services.BillingService;
import com.pmrodrigues.gnsnet.services.EmailService;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Marceloo on 02/10/2014.
 */
public class TestBillingService{

    private Mockery context = new Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};

    private CompanyRepository companyRepository = context.mock(CompanyRepository.class);
    private ClientRepository clientRepository = context.mock(ClientRepository.class);
    private BillingRepository billingRepository = context.mock(BillingRepository.class);
    private EmailService emailService = context.mock(EmailService.class);
    private BillingService service = new BillingService();

    @Before
    public void setup() throws Exception {

        Field company = service.getClass().getDeclaredField("companyRepository");
        company.setAccessible(true);
        company.set(service,companyRepository);

        Field cliente = service.getClass().getDeclaredField("clientRepository");
        cliente.setAccessible(true);
        cliente.set(service,clientRepository);

        Field boleto = service.getClass().getDeclaredField("billingRepository");
        boleto.setAccessible(true);
        boleto.set(service,billingRepository);

        Field email = service.getClass().getDeclaredField("emailService");
        email.setAccessible(true);
        email.set(service,emailService);

    }

    @Test
    public void shouldSendBilling() throws Exception {

        final Cobranca cobranca = createMock();
        final Empresa empresa = cobranca.getEmpresa();
        Field id = empresa.getClass().getDeclaredField("id");
        id.setAccessible(true);
        id.set(empresa,1L);

        context.checking(new Expectations(){{

            oneOf(clientRepository).findByCPF(with(aNonNull(String.class)));
            will(returnValue(null));

            oneOf(companyRepository).findById(with(aNonNull(Long.class)));
            will(returnValue(empresa));

            oneOf(clientRepository).add(with(aNonNull(Cliente.class)));

            oneOf(billingRepository).findBillingByNumDoc(with(aNonNull(String.class)),with(aNonNull(Banco.class)));
            will(returnValue(null));

            oneOf(billingRepository).add(with(aNonNull(Cobranca.class)));

            oneOf(emailService).to(with(aNonNull(String.class)));
            will(returnValue(emailService));

            oneOf(emailService).subject(with(aNonNull(String.class)));
            will(returnValue(emailService));

            oneOf(emailService).template(with(aNonNull(String.class)), with(aNonNull(Map.class)));
            will(returnValue(emailService));

            oneOf(emailService).attachment(with(aNonNull(String.class)), with(aNonNull(InputStream.class)));
            will(returnValue(emailService));

            oneOf(emailService).send();

        }});

        service.enviar(cobranca);
    }

    @Test
    public void shouldAssociateAClientSavedToThisBilling() throws Exception {

        final Cobranca cobranca = createMock();
        final Cliente cliente = cobranca.getCliente();
        final Empresa empresa = cobranca.getEmpresa();
        Field id = empresa.getClass().getDeclaredField("id");
        id.setAccessible(true);
        id.set(empresa,1L);

        context.checking(new Expectations(){{

            oneOf(clientRepository).findByCPF(with(aNonNull(String.class)));
            will(returnValue(cliente));

            oneOf(companyRepository).findById(with(aNonNull(Long.class)));
            will(returnValue(empresa));

            oneOf(billingRepository).findBillingByNumDoc(with(aNonNull(String.class)),with(aNonNull(Banco.class)));
            will(returnValue(null));

            oneOf(billingRepository).add(with(aNonNull(Cobranca.class)));

            oneOf(emailService).to(with(aNonNull(String.class)));
            will(returnValue(emailService));

            oneOf(emailService).subject(with(aNonNull(String.class)));
            will(returnValue(emailService));

            oneOf(emailService).template(with(aNonNull(String.class)), with(aNonNull(Map.class)));
            will(returnValue(emailService));

            oneOf(emailService).attachment(with(aNonNull(String.class)), with(aNonNull(InputStream.class)));
            will(returnValue(emailService));

            oneOf(emailService).send();

        }});

        service.enviar(cobranca);

    }

    @Test
    public void shouldUpdateBillingBeforeSend() throws Exception {

        final Cobranca cobranca = createMock();
        final Cliente cliente = cobranca.getCliente();
        final Cobranca existed = createMock();
        final Empresa empresa = cobranca.getEmpresa();
        Field id = empresa.getClass().getDeclaredField("id");
        id.setAccessible(true);
        id.set(empresa,1L);

        context.checking(new Expectations(){{

            oneOf(clientRepository).findByCPF(with(aNonNull(String.class)));
            will(returnValue(cliente));

            oneOf(companyRepository).findById(with(aNonNull(Long.class)));
            will(returnValue(empresa));

            oneOf(billingRepository).findBillingByNumDoc(with(aNonNull(String.class)),with(aNonNull(Banco.class)));
            will(returnValue(existed));

            oneOf(billingRepository).set(with(aNonNull(Cobranca.class)));

            oneOf(emailService).to(with(aNonNull(String.class)));
            will(returnValue(emailService));

            oneOf(emailService).subject(with(aNonNull(String.class)));
            will(returnValue(emailService));

            oneOf(emailService).template(with(aNonNull(String.class)), with(aNonNull(Map.class)));
            will(returnValue(emailService));

            oneOf(emailService).attachment(with(aNonNull(String.class)), with(aNonNull(InputStream.class)));
            will(returnValue(emailService));

            oneOf(emailService).send();

        }});

        service.enviar(cobranca);

    }



    private Cobranca createMock() {
        Cliente cliente = new Cliente();
        cliente.setNome("teste");
        cliente.setEmail("marcelosrodrigues@globo.com");
        cliente.setCpf("111");

        Endereco endereco = new Endereco();
        endereco.setBairro("ENGENHO DA R");
        endereco.setCep("20761090");
        endereco.setLogradouro("R.BENTO DO AMARAL 43 CASA");
        endereco.setCidade("RIO DE JANEIRO");
        Estado estado = new Estado();
        estado.setUf("AC");
        estado.setNome("ACRE");
        endereco.setEstado(estado);

        cliente.setEndereco(endereco);

        Cobranca cobranca = new Cobranca();
        cobranca.setCliente(cliente);
        cobranca.setCarteira(109);
        cobranca.setDataEmissao(DateTime.now().toDate());
        cobranca.setDataProcessamento(DateTime.now().toDate());
        cobranca.setDataVencimento(DateTime.now().toDate());
        cobranca.setValorBoleto(BigDecimal.ONE);

        Empresa empresa = new Empresa();
        empresa.setNome("TESTE");
        cobranca.setEmpresa(empresa);

        NossoNumero nossonumero = new NossoNumero();
        nossonumero.setNumero(80549721L);
        nossonumero.setDigito("8");
        cobranca.setNossoNumero(nossonumero);

        Agencia agencia = new Agencia();
        agencia.setNumero(540);
        cobranca.setAgencia(agencia);

        ContaCorrente contacorrente = new ContaCorrente();
        contacorrente.setNumero(5055L);
        contacorrente.setDigito("6");

        cobranca.setContaCorrente(contacorrente);

        cobranca.setNumeroDoDocumento("9096334");
        cobranca.setInstrucoes("APÓS O VENCIMENTO PAGAR SOMENTE NO ITAU#PAGAVEL SOMENTE EM BANCOS#APOS VENCIMENTO COBRAR R$ 0,45 POR DIA DE ATRASO#APOS VENCIMENTO COBRAR MULTA DE R$ 4,48#Competência : 09/2014  Parcela : 12");
        cobranca.setBanco(Banco.ITAU);
        return cobranca;
    }

}
