package com.SweetDreams.sweetDreams.Services.Impl;


import com.SweetDreams.sweetDreams.Models.Email;
import com.SweetDreams.sweetDreams.Services.EmailSenderService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private Configuration configuration;


    private static final Logger log = LoggerFactory.getLogger(EmailSenderServiceImpl.class);

    @Override
    public void sendSimpleMessage(String para, String assunto, String text) {
        log.info("enviando email");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lojadocessweetdreams@gmail.com");
        message.setTo(para);
        message.setSubject(assunto);
        message.setText(text);
        emailSender.send(message);
        log.info("email enviado");
    }

    @Override
    public void sendEmail(Email email) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        // Using a subfolder such as /templates here
        configuration.setClassForTemplateLoading(this.getClass(), "/Templates");

        Template t = configuration.getTemplate("NovoCadastro.ftl");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, email.getModel());

        helper.setTo(email.getEmailPara());
        helper.setText(text, true);
        helper.setSubject(email.getEmailAssunto());

        emailSender.send(message);
    }

}
