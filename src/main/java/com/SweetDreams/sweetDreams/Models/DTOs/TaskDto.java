package com.SweetDreams.sweetDreams.Models.DTOs;

import java.util.concurrent.ScheduledFuture;

public class TaskDto {

    private String jobId;
    private ScheduledFuture scheduledFuture;
    private String taskNome;

    public TaskDto() {
    }

    public TaskDto(String jobId, ScheduledFuture scheduledFuture, String taskNome) {
        this.jobId = jobId;
        this.scheduledFuture = scheduledFuture;
        this.taskNome = taskNome;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public ScheduledFuture getScheduledFuture() {
        return scheduledFuture;
    }

    public void setScheduledFuture(ScheduledFuture scheduledFuture) {
        this.scheduledFuture = scheduledFuture;
    }

    public String getTaskNome() {
        return taskNome;
    }

    public void setTaskNome(String taskNome) {
        this.taskNome = taskNome;
    }
}
