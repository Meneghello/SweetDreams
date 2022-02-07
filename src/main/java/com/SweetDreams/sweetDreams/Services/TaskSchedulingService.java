package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Models.DTOs.NewTaskDto;
import com.SweetDreams.sweetDreams.Models.DTOs.TaskDto;
import com.SweetDreams.sweetDreams.Models.Task;
import com.SweetDreams.sweetDreams.Services.Impl.TaskExecutorPrint;
import com.SweetDreams.sweetDreams.Services.Impl.TaskExecutorSout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

@Service
public class TaskSchedulingService {
    @Autowired
    private TaskScheduler taskScheduler;
    @Autowired
    private TaskExecutorSout taskExecutorSout;
    @Autowired
    private TaskExecutorPrint taskExecutorPrint;

    private static final Logger log = LoggerFactory.getLogger(TaskSchedulingService.class);

    Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();
    Map<String, String> jobsMapName = new HashMap<>();

    public void scheduleATask(String jobId, String nomeTask, Runnable task, String cronExpression) {
        log.info("Scheduling task com job id: " + jobId + " e cron: " + cronExpression);
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(task, new CronTrigger(cronExpression,TimeZone.getTimeZone(TimeZone.getDefault().getID())));
        jobsMap.put(jobId, scheduledTask);
        jobsMapName.put(jobId, nomeTask);
    }

    public void removeScheduledTask(String jobId) {
        ScheduledFuture<?> scheduledTask = jobsMap.get(jobId);
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
            jobsMap.put(jobId, null);
        }
    }

    public List<Map.Entry<String, String>> getAllTasks() {
        return jobsMapName.entrySet().stream().collect(Collectors.toList());
    }

    public Task fromDto(NewTaskDto newTaskDto){
        Task task = new Task();
        task.setNomeTask(newTaskDto.getNomeTask());
        task.setDescricaoTask(newTaskDto.getDescricaoTask());
        task.setCronExp(cronGenerate(newTaskDto.getSegundos(), newTaskDto.getMinutos(), newTaskDto.getHoras(),
                newTaskDto.getDia(), newTaskDto.getMes(), newTaskDto.getDiaDaSemana() ));
        return task;
    }

    private String cronGenerate(String segundos, String minutos, String horas, String dia, String mes, String diaDaSemana){
        return segundos + " " + minutos + " " + horas + " " + dia + " " + mes + " " + diaDaSemana;
    }

    public Runnable taskRunnable (Task task, Integer taskNumero){
        Runnable taskRun = null;
        switch (taskNumero){
            case 0:
                taskExecutorPrint.setTaskDef(task);
                 taskRun = taskExecutorPrint;
                 break;
            case 1:
                taskExecutorSout.setTaskDef(task);
                taskRun = taskExecutorSout;
                break;
        }
        return taskRun;
    }
}
