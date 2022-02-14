package com.SweetDreams.sweetDreams.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "Tasks")
public class Task {

    @Id
    private String id;

    private String cronExp;
    private String nomeTask;
    private String descricaoTask;
    private Boolean active;
    private Boolean save;
    private String jobId;
    private Integer taskNum;

    public Task(String id,String cronExp, String nomeTask, String descricaoTask, Boolean active, Boolean save,
                String jobId, Integer taskNum) {
        this.id = id;
        this.cronExp = cronExp;
        this.nomeTask = nomeTask;
        this.descricaoTask = descricaoTask;
        this.active = active;
        this.save = save;
        this.jobId = jobId;
        this.taskNum = taskNum;
    }

    public Task() {
    }

    public Integer getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(Integer taskNum) {
        this.taskNum = taskNum;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getSave() {
        return save;
    }

    public void setSave(Boolean save) {
        this.save = save;
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
