package com.SweetDreams.sweetDreams.Models.DTOs;

import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class NewTaskDto {
    @NotEmpty
    @Pattern(regexp = "^[0-9*?/]+$")
    private String segundos;
    @NotEmpty
    @Pattern(regexp = "^[0-9*?/]+$")
    private String minutos;
    @NotEmpty
    @Pattern(regexp = "^[0-9*?/]+$")
    private String horas;
    @NotEmpty
    @Pattern(regexp = "^[0-9*?/]+$")
    private String dia;
    @NotEmpty
    @Pattern(regexp = "^[0-9*?/]+$")
    private String mes;
    @NotEmpty
    @Pattern(regexp = "^[0-9*?/]+$")
    private String diaDaSemana;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9 ._]+$")
    private String nomeTask;
    private String descricaoTask;
    private Boolean save;

    public NewTaskDto(String segundos, String minutos, String horas, String dia, String mes, String diaDaSemana,
                      String nomeTask, String descricaoTask, Boolean save) {
        this.segundos = segundos;
        this.minutos = minutos;
        this.horas = horas;
        this.dia = dia;
        this.mes = mes;
        this.diaDaSemana = diaDaSemana;
        this.nomeTask = nomeTask;
        this.descricaoTask = descricaoTask;
        this.save = save;
    }

    public NewTaskDto() {
    }

    public Boolean getSave() {
        return save;
    }

    public void setSave(Boolean save) {
        this.save = save;
    }

    public String getSegundos() {
        return segundos;
    }

    public void setSegundos(String segundos) {
        this.segundos = segundos;
    }

    public String getMinutos() {
        return minutos;
    }

    public void setMinutos(String minutos) {
        this.minutos = minutos;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getDiaDaSemana() {
        return diaDaSemana;
    }

    public void setDiaDaSemana(String diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public String getNomeTask() {
        return nomeTask;
    }

    public void setNomeTask(String nomeTask) {
        this.nomeTask = nomeTask;
    }

    public String getDescricaoTask() {
        return descricaoTask;
    }

    public void setDescricaoTask(String descricaoTask) {
        this.descricaoTask = descricaoTask;
    }
}
