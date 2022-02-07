package com.SweetDreams.sweetDreams.Models;

public class Task {

    private String cronExp;
    private String nomeTask;
    private String descricaoTask;

    public Task(String cronExp, String nomeTask, String descricaoTask) {
        this.cronExp = cronExp;
        this.nomeTask = nomeTask;
        this.descricaoTask = descricaoTask;
    }

    public Task() {
    }

    public String getDescricaoTask() {
        return descricaoTask;
    }

    public void setDescricaoTask(String descricaoTask) {
        this.descricaoTask = descricaoTask;
    }

    public String getCronExp() {
        return cronExp;
    }

    public void setCronExp(String cronExp) {
        this.cronExp = cronExp;
    }

    public String getNomeTask() {
        return nomeTask;
    }

    public void setNomeTask(String nomeTask) {
        this.nomeTask = nomeTask;
    }
}
