package com.SweetDreams.sweetDreams.Models;


import org.springframework.data.mongodb.core.aggregation.VariableOperators;

import java.util.List;
import java.util.Map;

public class Email {

    String emailPara;
    String emailDe;
    String emailAssunto;
    String emailTexto;
    List<Object> attachments;
    Map<String, Object> model;

    public String getEmailDe() {
        return emailDe;
    }

    public void setEmailDe(String emailDe) {
        this.emailDe = emailDe;
    }

    public String getEmailPara() {
        return emailPara;
    }

    public void setEmailPara(String emailPara) {
        this.emailPara = emailPara;
    }

    public String getEmailAssunto() {
        return emailAssunto;
    }

    public void setEmailAssunto(String emailAssunto) {
        this.emailAssunto = emailAssunto;
    }

    public String getEmailTexto() {
        return emailTexto;
    }

    public void setEmailTexto(String emailTexto) {
        this.emailTexto = emailTexto;
    }

    public List<Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    public Email() {
    }

    public Email(String emailPara, String emailDe,String emailAssunto, String emailTexto, List<Object> attachments,
                 Map<String, Object> model) {
        this.emailDe=emailDe;
        this.emailPara = emailPara;
        this.emailAssunto = emailAssunto;
        this.emailTexto = emailTexto;
        this.attachments = attachments;
        this.model = model;
    }
}
