package com.SweetDreams.sweetDreams.TestModels;

import com.SweetDreams.sweetDreams.Models.Email;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;

@SpringBootTest
public class EmailTest {
    @Test
    public void setEmailPara(){
        Email email = new Email();
        email.setEmailPara("teste");
        Assertions.assertEquals("teste", email.getEmailPara());
    }

    @Test
    public  void setEmailDe(){
        Email email = new Email();
        email.setEmailDe("teste");
        Assertions.assertEquals("teste", email.getEmailDe());
    }

    @Test
    public void  setEmailAssunto(){
        Email email = new Email();
        email.setEmailAssunto("teste");
        Assertions.assertEquals("teste", email.getEmailAssunto());
    }

    @Test
    public void setEmailTexto(){
        Email email = new Email();
        email.setEmailTexto("teste");
        Assertions.assertEquals("teste", email.getEmailTexto());
    }

    @Test
    public void setAttachments(){
        Email email = new Email();
        email.setAttachments(new ArrayList<>());
        Assertions.assertEquals(new ArrayList<>(), email.getAttachments());
    }

    @Test
    public void setModel(){
        Email email = new Email();
        email.setModel(new HashMap<>());
        Assertions.assertEquals(new HashMap<>(), email.getModel());
    }

    @Test
    public void getEmailPara(){
        Email email = new Email();
        Assertions.assertNull(email.getEmailPara());
    }

    @Test
    public  void getEmailDe(){
        Email email = new Email();
        Assertions.assertNull(email.getEmailDe());
    }

    @Test
    public void  getEmailAssunto(){
        Email email = new Email();
        Assertions.assertNull(email.getEmailAssunto());
    }

    @Test
    public void getEmailTexto(){
        Email email = new Email();
        Assertions.assertNull(email.getEmailTexto());
    }

    @Test
    public void getAttachments(){
        Email email = new Email();
        Assertions.assertNull(email.getAttachments());
    }

    @Test
    public void getModel(){
        Email email = new Email();
        Assertions.assertNull(email.getModel());
    }
}
