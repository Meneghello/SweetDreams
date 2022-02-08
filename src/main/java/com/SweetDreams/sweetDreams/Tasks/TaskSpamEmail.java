package com.SweetDreams.sweetDreams.Tasks;

import com.SweetDreams.sweetDreams.Models.Email;
import com.SweetDreams.sweetDreams.Models.Task;
import com.SweetDreams.sweetDreams.Services.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TaskSpamEmail implements Runnable {

    @Autowired
    private EmailSenderService emailSenderService;

    private static final Logger log = LoggerFactory.getLogger(TaskSpamEmail.class);

    private Task task;

    @Override
    public void run() {

        log.info("enviando email");
        Email mail = new Email();
        mail.setEmailPara("lojadocessweetdreams@gmail.com");
        mail.setEmailAssunto("Spam teste");
        mail.setEmailTexto("Spam teste.\r\nSpam para testar schedule tasks");

        try {
            log.info("email enviado");
            emailSenderService.sendSimpleMessage(mail);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public Task getTaskDef() {
        return task;
    }

    public void setTaskDef(Task task) {
        this.task = task;
    }
}
