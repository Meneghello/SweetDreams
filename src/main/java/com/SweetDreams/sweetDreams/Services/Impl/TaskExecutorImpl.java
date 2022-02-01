package com.SweetDreams.sweetDreams.Services.Impl;

import com.SweetDreams.sweetDreams.Models.Task;
import org.springframework.stereotype.Service;

@Service
public class TaskExecutorImpl implements Runnable{
    private Task task;

    @Override
    public void run() {
        System.out.println("nome da task: " + task.getNomeTask());
    }
    public Task getTaskDef(){
        return task;
    }
    public void setTaskDef(Task task){
        this.task = task;
    }
}
