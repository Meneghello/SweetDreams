package com.SweetDreams.sweetDreams.Model.DTOs;


import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Model.Endereço;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class ClienteDto {

    String id;

    @NotBlank(message = "Nome é obrigatório")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "O campo nome deve conter apenas letras")
    private String nome;


    @Valid
    @NotNull(message = "Campo endereço é obrigatório")
    private Endereço endereço;

    @NotBlank(message = "Celular é obrigatório")
    @Pattern(regexp = "^[0-9-().+ ]+$", message = "O campo celular deve conter apenas números e (.-+)")
    private String celular;

    @NotEmpty(message = "Campo email é obrigatório")
    //@Email(message = "Digite um email valido")
    @Indexed(unique = true)
    private String email;

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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClienteDto(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.endereço = getEndereço();
        this.celular = getCelular();
        this.email = getEmail();
    }

    public ClienteDto() {
    }
}
