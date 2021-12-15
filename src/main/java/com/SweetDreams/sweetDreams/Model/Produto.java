package com.SweetDreams.sweetDreams.Model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "Produtos")
public class Produto{

    @Id
    private String idProduto;
    private String nomeProduto;
    private String[] sabor;
    private Long quantidade;
    private Double preço;
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

    public Double getPreço() {
        return preço;
    }

    public void setPreço(Double preço) {
        this.preço = preço;
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(String dataValidade) {
        this.dataValidade = dataValidade;
    }

    public Produto(String nomeProduto, String[] sabor, Long quantidade, Double preço, String dataValidade) {
        this.nomeProduto = nomeProduto;
        this.sabor = sabor;
        this.quantidade = quantidade;
        this.preço = preço;
        this.dataValidade = dataValidade;
    }
}
