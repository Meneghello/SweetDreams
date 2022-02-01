package com.SweetDreams.sweetDreams.Models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(value = "Cupom")
public class Cupom {

    @Id
    private String id;

    private String nomeCupom;

    private String descricao;

    private Double porcentagem;

    private String dataInicial;

    private LocalDateTime dataExpiracao;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeCupom() {
        return nomeCupom;
    }

    public void setNomeCupom(String nomeCupom) {
        this.nomeCupom = nomeCupom;
    }

    public Double getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(Double porcentagem) {
        this.porcentagem = porcentagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(String dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDateTime getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Cupom(String id, String nomeCupom, String descricao, Double porcentagem, String dataInicial, LocalDateTime dataExpiracao) {
        this.id = id;
        this.nomeCupom = nomeCupom;
        this.descricao = descricao;
        this.porcentagem = porcentagem;
        this.dataInicial = dataInicial;
        this.dataExpiracao = dataExpiracao;
    }

    public Cupom() {
    }

}
