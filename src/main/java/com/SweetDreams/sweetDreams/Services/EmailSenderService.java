package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Models.Email;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

@Service
public interface EmailSenderService {
    public void sendSimpleMessage(String para, String assunto, String text);

    public void sendEmail(Email email) throws Exception;
}
