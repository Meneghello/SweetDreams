package com.SweetDreams.sweetDreams.Services.Impl;

import com.SweetDreams.sweetDreams.Models.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskExecutorPrint implements Runnable{
    private Task task;

    @Override
    public void run() {
        System.out.println("nome da task: " + task.getNomeTask());
        System.out.println("Task Print");
    }

    public Task getTaskDef() {
        return task;
    }

    public void setTaskDef(Task task) {
        this.task = task;
    }
}
