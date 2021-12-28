package com.SweetDreams.sweetDreams.Model;



import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.*;


@Document(value = "Clientes")
public class Cliente {

    @Id
    private String id;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z ]+$")
    private String nome;

    @Valid
    @NotNull
    private Endereço endereço;

    @NotBlank
    @Pattern(regexp = "^[0-9-/. ]+$")
    private String dataNascimento;

    @NotBlank
    @Pattern(regexp = "^[0-9-().+ ]+$")
    private String celular;

    @NotEmpty
    //@CPF
    @Indexed(unique = true)
    private String cpf;

    @NotEmpty
    @Email
    @Indexed(unique = true)
    private String email;

    public Cliente() {

    }

    public String getIdCliente() {
        return id;
    }

    public void setIdCliente(String idCliente) {
        this.id = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public Endereço getEndereço() {
        return endereço;
    }

    public void setEndereço(Endereço endereço) {
        this.endereço = endereço;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cliente(String id, String nome, Endereço endereço, String dataNascimento, String celular, String cpf, String email) {
        this.id = id;
        this.nome = nome;
        this.endereço = endereço;
        this.dataNascimento = dataNascimento;
        this.celular = celular;
        this.cpf = cpf;
        this.email = email;
    }

}
