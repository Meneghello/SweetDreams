package com.SweetDreams.sweetDreams.Models.DTOs;

public class ClienteAuthDto {

    private String cpf;
    private String senha;

    public ClienteAuthDto(String cpf, String senha) {
        this.cpf = cpf;
        this.senha = senha;
    }

    public ClienteAuthDto() {
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
