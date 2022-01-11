package com.SweetDreams.sweetDreams.Models.DTOs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

public class HistoricoClienteDto {


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

    public HistoricoClienteDto(String nomeProduto, String sabor, Long quantidade, String totalPago, String data) {
        this.nomeProduto = nomeProduto;
        this.sabor = sabor;
        this.quantidade = quantidade;
        this.totalPago = totalPago;
        this.data = data;
    }

    public HistoricoClienteDto() {
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
