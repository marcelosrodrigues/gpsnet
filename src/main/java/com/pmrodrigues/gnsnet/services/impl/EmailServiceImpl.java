package com.pmrodrigues.gnsnet.services.impl;

import com.pmrodrigues.gnsnet.dto.Attachment;
import com.pmrodrigues.gnsnet.exceptions.EnderecoEmailInvalidoException;
import com.pmrodrigues.gnsnet.exceptions.ErrorProcessamentoDeTemplateException;
import com.pmrodrigues.gnsnet.services.EmailService;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static java.lang.String.format;

/**
 * Created by Marceloo on 02/10/2014.
 */
@Service("EmailService")
public class EmailServiceImpl implements EmailService{

    private static final Logger logging = Logger.getLogger(EmailServiceImpl.class);

    @Resource(name = "mailSender")
    private JavaMailSender sender;

    private MimeMessage message;

    private MimeMessageHelper helper;

    private Attachment attachment;

    @Resource(name = "velocityEngine")
    private VelocityEngine velocityEngine;

    private String from;

    @PostConstruct
    public void createMimeMessage() throws MessagingException {
        message = sender.createMimeMessage();
        helper = new MimeMessageHelper(this.message,true);
    }

    public void setFrom(final String from) {
        this.from = from;
    }

    @Override
    public EmailService to(final String to) {
        try {
            helper.setTo(to);
            return this;
        } catch (MessagingException e) {
            throw new EnderecoEmailInvalidoException(format(
                    "O endereço %s é inválido", to));
        }
    }

    @Override
    public EmailService subject(final String subject) {

        try {
            helper.setSubject(subject);
            return this;
        } catch (Exception e) {
            return this;
        }
    }

    @Override
    public EmailService template(final String template, Map<String, Object> parameters) {
        try {

            final String text = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, template, "UTF-8", parameters);

            helper.setText(text,true);

            return this;
        } catch (MessagingException e) {
            throw new ErrorProcessamentoDeTemplateException(e.getMessage(), e);
        }
    }

    @Override
    public void send() {

        try {

            logging.info(format("Enviando email %s",this.message.getRecipients(Message.RecipientType.TO)));
            helper.setFrom(from);
            if( this.attachment != null ) {
                helper.addAttachment(attachment.getFileName(),new ByteArrayResource(IOUtils.toByteArray(attachment.getInputStream())));
            }
            this.sender.send(this.message);

            logging.info(format("Enviando %s com sucesso",this.message.getRecipients(Message.RecipientType.TO)));

        } catch (MessagingException | IOException e) {
            logging.fatal(format("Ocorreu um erro para enviar email %s",e.getMessage(),e));
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmailService message(String message) {
        try {
            helper.setText(message);
            return this;
        } catch (MessagingException e) {
            throw new ErrorProcessamentoDeTemplateException(e.getMessage(), e);
        }
    }

    @Override
    public EmailService attachment(final String filename, final InputStream attachment) {
        this.attachment = new Attachment(filename , attachment);
        return this;

    }
}
