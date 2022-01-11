package com.SweetDreams.sweetDreams.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;

@Document(collection = "Vendas")
public class CompraVenda {

    @Id
    private String id;

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

    private String totalPago;

    private String data;


    public CompraVenda() {
    }

    public CompraVenda(String id, Long codigoVendedor, String cpfCliente, String nomeProduto, String sabor,
                       Long quantidade, String totalPago, String data) {
        this.id = id;
        this.codigoVendedor = codigoVendedor;
        this.cpfCliente = cpfCliente;
        this.nomeProduto = nomeProduto;
        this.sabor = sabor;
        this.quantidade = quantidade;
        this.totalPago = totalPago;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(String totalPago) {
        this.totalPago = totalPago;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
