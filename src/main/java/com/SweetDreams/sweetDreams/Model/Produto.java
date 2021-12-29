package com.SweetDreams.sweetDreams.Model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Document(value = "Produtos")
public class Produto{

    @Id
    private String id;
    @NotBlank
    @Indexed(unique = true)
    private String nomeProduto;
    private String[] sabor;
    @PositiveOrZero
    private Long quantidade;
    @PositiveOrZero
    private Double preco;
    @NotBlank
    private String dataValidade;

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String[] getSabor() {
        return sabor;
    }

    public void setSabor(String[] sabor) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Produto(String id, String nomeProduto, String[] sabor, Long quantidade, Double preco, String dataValidade) {
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.sabor = sabor;
        this.quantidade = quantidade;
        this.preco = preco;
        this.dataValidade = dataValidade;
    }

    public Produto() {
    }
}
