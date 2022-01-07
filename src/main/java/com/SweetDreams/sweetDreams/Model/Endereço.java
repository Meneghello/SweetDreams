package com.SweetDreams.sweetDreams.Model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class Endereço {

    @NotBlank(message = "campo rua é obrigatório")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "O campo rua deve conter apenas letras")
    private String rua;
    @NotBlank(message = "campo número é obrigatório")
    @Pattern(regexp = "^[0-9 ]+$", message = "O campo número deve conter apenas números")
    private String numero;

    @Pattern(regexp = "^[A-Za-z0-9./() ]+$", message = "O campo complemento deve conter apenas letras e números")
    private String complemento;
    @NotBlank(message = "campo CEP é obrigatório")
    @Pattern(regexp = "^[0-9-./ ]+$", message = "O campo cep deve conter apenas números")
    private String cep;

    @NotBlank(message = "campo cidade é obrigatório")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "O campo cidade deve conter apenas letras")
    private String cidade;
    @Pattern(regexp = "^[A-Za-z ]+$", message = "O campo estado deve conter apenas letras")
    @NotBlank(message = "campo estado é obrigatório")
    private String estado;

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Endereço(String rua, String numero, String complemento, String cep, String cidade, String estado) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Endereço() {
    }
}
