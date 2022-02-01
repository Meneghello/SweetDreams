package com.SweetDreams.sweetDreams.Models.DTOs;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

public class NovoCupomDto {

    @NotEmpty
    private String nomeCupom;

    @NotEmpty
    private String descricao;

    @PositiveOrZero
    private Double porcentagem;

    private Long dataExpiracao;

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

    public Long getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Long dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public NovoCupomDto(String nomeCupom, String descricao, Double porcentagem, Long dataExpiracao) {
        this.nomeCupom = nomeCupom;
        this.descricao = descricao;
        this.porcentagem = porcentagem;
        this.dataExpiracao = dataExpiracao;
    }

    public NovoCupomDto() {
    }
}
