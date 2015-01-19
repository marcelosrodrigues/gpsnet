package com.pmrodrigues.gnsnet.services;

import com.pmrodrigues.gnsnet.exceptions.DuplicateRegisterException;
import com.pmrodrigues.gnsnet.exceptions.UserIsBlockedException;
import com.pmrodrigues.gnsnet.models.Empresa;
import com.pmrodrigues.gnsnet.models.Usuario;
import com.pmrodrigues.gnsnet.repository.ResultList;
import com.pmrodrigues.gnsnet.repository.UserRepository;
import com.pmrodrigues.gnsnet.utilities.MD5;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * Created by Marceloo on 27/09/2014.
 */
@Service
public class UserService {

    @Resource(name = "UserRepository")
    private UserRepository repository;

    @Resource(name = "novoUsuario")
    private EmailService email;

    private static final String NEW_USERS_TEMPLATE = "template/novo-usuario.vm";

    private static final Logger logging = Logger.getLogger(UserService.class);

    public UserService(final UserRepository repository) {
        this();
        this.repository = repository;
    }

    public UserService() {

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Usuario doAuthentication(final String email, final String password) {

        try {

            logging.info("iniciando a autenticacao para o email " + email);
            final Usuario usuario = repository.findByEmail(email);
            final String senha = MD5.encrypt(password);


            if( usuario == null ) {
                logging.debug(format("Email %s não encontrado",email));
                throw new UsernameNotFoundException("Usuario não encontrado ou senha inválida");
            } else {
                final Empresa empresa = usuario.getEmpresa();
                if( empresa != null && empresa.isBloqueado() ) {
                    logging.debug(format("tentativa de autenticacao de um usuário onde a empresa esta bloqueada"));
                    throw new UserIsBlockedException("Ocorreu um erro na sua autenticação. Entre em contato com a Administração");
                }
                if (usuario.getPassword().equals(senha)) {

                    if( usuario.isBloqueado() ){
                        throw new UserIsBlockedException("Usuário bloqueado. Entre em contato com a Administração");
                    }

                    return usuario;
                } else {
                    usuario.incrementarTentativasInvalidas();
                    if( usuario.isBloqueado() ){
                        throw new UserIsBlockedException("Usuário bloqueado. Entre em contato com a Administração");
                    } else {
                        throw new UsernameNotFoundException("Usuario não encontrado ou senha inválida");
                    }
                }
            }

        } catch (NoSuchAlgorithmException e) {
            logging.error("ERRO ao tentar gerar encriptar a senha do usuario " + e.getMessage(),e);
            return null;
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void createUser(final Usuario usuario) throws DuplicateRegisterException {

        logging.debug(format("Adicionando o usuário %s",usuario));

        final Usuario existed = repository.findByEmail(usuario.getEmail());

        if( existed == null ) {

            logging.debug("usuario novo");
            usuario.generateNewPassword();
            repository.add(usuario);

            Map<String,Object> parameters = new HashMap<>();
            parameters.put("usuario",usuario);
            email.to(usuario.getEmail())
                 .subject("Segue sua senha de acesso")
                 .template(UserService.NEW_USERS_TEMPLATE,parameters)
                 .send();

        } else {
            logging.debug("usuário duplicado");
            throw new DuplicateRegisterException("Não foi possível salvar o usuário. O E-mail informado já esta em uso por outro usuário");
        }

    }

    public ResultList<Usuario> search(final Usuario usuario) {
        return search(usuario,0);
    }

    public ResultList<Usuario> search(final Usuario usuario, Integer page) {
        try {
            logging.info(format("Procurando usuarios utilizando como exemplo %s",usuario));
            return repository.search(usuario,page);
        } finally {
            logging.info("metodo finalizado");
        }

    }

    public Usuario findById(Long id) {
        return repository.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void changeUser(final Usuario usuario) {
        Usuario existed = repository.findById(usuario.getId());
        existed.setEmail(usuario.getEmail());
        existed.setEmpresa(usuario.getEmpresa());
        existed.setNome(usuario.getNome());
        repository.set(existed);
    }
}
