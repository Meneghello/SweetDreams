package com.SweetDreams.sweetDreams.Models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.*;


@Document(value = "Clientes")
public class Cliente {

    @Id
    private String id;

    @NotBlank(message = "Nome é obrigatório")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "O campo nome deve conter apenas letras")
    private String nome;

    @Valid
    @NotNull(message = "Campo endereço é obrigatório")
    private Endereço endereço;

    @NotBlank(message = "Data de nascimento é obrigatório")
    @Pattern(regexp = "^[0-9-/. ]+$", message = "O campo data de nascimento deve apenas conter números, -/.")
    private String dataNascimento;

    @NotBlank(message = "Celular é obrigatório")
    @Pattern(regexp = "^[0-9-().+ ]+$", message = "O campo celular deve conter apenas números e (.-+)")
    private String celular;

    @NotEmpty(message = "Campo cpf é obrigatório")
    //@CPF(message = "Digite um cpf valido")
    @Indexed(unique = true)
    private String cpf;

    @NotEmpty(message = "Campo email é obrigatório")
    //@Email(message = "Digite um email valido")
    @Indexed(unique = true)
    private String email;

    private String senha;

    public Cliente() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public Cliente(String id, String nome, Endereço endereço, String dataNascimento, String celular, String cpf,
                   String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.endereço = endereço;
        this.dataNascimento = dataNascimento;
        this.celular = celular;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }

}
