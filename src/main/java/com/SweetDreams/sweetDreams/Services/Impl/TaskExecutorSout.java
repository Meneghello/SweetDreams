package com.SweetDreams.sweetDreams.Services.Impl;

import com.SweetDreams.sweetDreams.Models.Task;
import com.SweetDreams.sweetDreams.Services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;


public class TaskExecutorSout implements Runnable {
    private Task task;

    @Override
    public void run() {
        System.out.println("nome da task: " + task.getNomeTask());

    }

    public Task getTaskDef() {
        return task;
    }

    public void setTaskDef(Task task) {
        this.task = task;
    }
}
