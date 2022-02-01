package com.SweetDreams.sweetDreams.Models;

public class Task {

    private String cronExp;
    private String nomeTask;

    public Task(String cronExp, String nomeTask) {
        this.cronExp = cronExp;
        this.nomeTask = nomeTask;
    }

    public Task() {
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
