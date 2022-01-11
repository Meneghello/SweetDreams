package com.SweetDreams.sweetDreams.Models.DTOs;

import javax.validation.constraints.*;

public class CompraVendaDto {

    @NotNull
    @PositiveOrZero(message = "Codigo do vendedor não pode ser nulo")
    private Long codigoVendedor;

    @NotEmpty(message = "Campo cpf é obrigatório")
    //@CPF(message = "Digite um cpf valido")
    private String cpfCliente;

    @NotBlank(message = "Nome do produto é obrigatório")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "O campo nome do produto deve conter apenas letras")
    private String nomeProduto;

    @NotNull(message = "Nome do sabor é obrigatório")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "O campo sabor do produto deve conter apenas letras")
    private String sabor;

    @NotNull(message = "Quantidade é obrigatória")
    @PositiveOrZero(message = "Quantidade não pode ser negativa")
    private Long quantidade;


    public CompraVendaDto(Long codigoVendedor, String cpfCliente, String nomeProduto, String sabor, Long quantidade) {
        this.codigoVendedor = codigoVendedor;
        this.cpfCliente = cpfCliente;
        this.nomeProduto = nomeProduto;
        this.sabor = sabor;
        this.quantidade = quantidade;
    }

    public CompraVendaDto() {
    }

    public Long getCodigoVendedor() {
        return codigoVendedor;
    }

    public void setCodigoVendedor(Long codigoVendedor) {
        this.codigoVendedor = codigoVendedor;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
}
