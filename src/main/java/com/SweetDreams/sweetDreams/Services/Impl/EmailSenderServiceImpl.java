package com.SweetDreams.sweetDreams.Services.Impl;


import com.SweetDreams.sweetDreams.Models.Email;
import com.SweetDreams.sweetDreams.Services.EmailSenderService;

import freemarker.template.Configuration;
import freemarker.template.Template;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;


@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private Configuration configuration;


    private static final Logger log = LoggerFactory.getLogger(EmailSenderServiceImpl.class);

    @Override
    public void sendSimpleMessage(Email email) {
        log.info("enviando email");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lojadocessweetdreams@gmail.com");
        message.setTo(email.getEmailPara());
        message.setSubject(email.getEmailAssunto());
        message.setText(email.getEmailTexto());
        emailSender.send(message);
        log.info("email enviado");
    }

    @Override
    public void sendEmail(Email email) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mime = new MimeMessageHelper(message);

        configuration.setClassForTemplateLoading(this.getClass(), "/Templates");
        Template t = configuration.getTemplate("NovoCadastro.ftl");

        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, email.getModel());
        mime.setTo(email.getEmailPara());
        mime.setText(text, true);
        mime.setSubject(email.getEmailAssunto());

        emailSender.send(message);
    }

}
