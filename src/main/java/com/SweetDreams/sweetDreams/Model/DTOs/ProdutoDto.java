package com.SweetDreams.sweetDreams.Model.DTOs;

import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.*;
import java.util.List;

public class ProdutoDto {


    @NotBlank(message = "Nome do produto é obrigatório")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "O campo nome do produto deve conter apenas letras")
    @Indexed(unique = true)
    private String nomeProduto;

    @NotNull(message = "Nome do sabor é obrigatório")
    private List<@Pattern(regexp = "^[A-Za-z ]+$", message = "O campo sabor do produto deve conter apenas letras")String> sabor;

    @NotNull(message = "Quantidade é obrigatória")
    @PositiveOrZero(message = "Quantidade não pode ser negativa")
    private Long quantidade;

    @NotNull(message = "Preço é obrigatório")
    @PositiveOrZero(message = "Preço não pode ser negativo")
    @DecimalMin("0.0")
    private Double preco;

    @NotBlank(message = "Data de validade é obrigatória")
    @Pattern(regexp = "^[0-9-/. ]+$", message = "O campo data de validade deve apenas conter números, -/.")
    private String dataValidade;

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public List<String> getSabor() {
        return sabor;
    }

    public void setSabor(List<String> sabor) {
        this.sabor = sabor;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(String dataValidade) {
        this.dataValidade = dataValidade;
    }

    public ProdutoDto(String nomeProduto, List<String> sabor, Long quantidade, Double preco, String dataValidade) {
        this.nomeProduto = nomeProduto;
        this.sabor = sabor;
        this.quantidade = quantidade;
        this.preco = preco;
        this.dataValidade = dataValidade;
    }

    public ProdutoDto() {
    }
}
