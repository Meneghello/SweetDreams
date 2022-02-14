package com.SweetDreams.sweetDreams.Services.Impl;

import com.SweetDreams.sweetDreams.Models.DTOs.NewTaskDto;
import com.SweetDreams.sweetDreams.Models.Task;
import com.SweetDreams.sweetDreams.Repository.TaskSchedulingRepository;
import com.SweetDreams.sweetDreams.Services.TaskSchedulingService;
import com.SweetDreams.sweetDreams.Tasks.TaskExecutorPrint;
import com.SweetDreams.sweetDreams.Tasks.TaskExecutorSout;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

@Service
public class TaskSchedulingServiceImpl implements TaskSchedulingService {

    @Autowired
    TaskSchedulingService taskSchedulingService;
    @Autowired
    private TaskScheduler taskScheduler;
    @Autowired
    private TaskExecutorSout taskExecutorSout;
    @Autowired
    private TaskExecutorPrint taskExecutorPrint;
    @Autowired
    TaskSchedulingRepository taskSchedulingRepository;

    private static final Logger log = LoggerFactory.getLogger(TaskSchedulingService.class);

    Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();
    Map<String, String> jobsMapName = new HashMap<>();

    @Override
    public void scheduleATask(String jobId, String nomeTask, Runnable task, String cronExpression) {
        log.info("Scheduling task com job id: " + jobId + " e cron: " + cronExpression);
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(task, new CronTrigger(cronExpression, TimeZone.getTimeZone(TimeZone.getDefault().getID())));
        jobsMap.put(jobId, scheduledTask);
        jobsMapName.put(jobId, nomeTask);
    }

    @Override
    public void removeScheduledTask(String jobId) {
        ScheduledFuture<?> scheduledTask = jobsMap.get(jobId);
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
            jobsMap.put(jobId, null);
        }
    }

    @Override
    public List<Map.Entry<String, String>> getAllTasks() {
        return jobsMapName.entrySet().stream().collect(Collectors.toList());
    }

    @Override
    public Task fromDto(NewTaskDto newTaskDto, Integer taskNumero) {
        Task task = new Task();
        task.setNomeTask(newTaskDto.getNomeTask());
        task.setDescricaoTask(newTaskDto.getDescricaoTask());
        task.setCronExp(cronGenerate(newTaskDto.getSegundos(), newTaskDto.getMinutos(), newTaskDto.getHoras(),
                newTaskDto.getDia(), newTaskDto.getMes(), newTaskDto.getDiaDaSemana()));
        task.setActive(true);
        task.setSave(newTaskDto.getSave());
        task.setTaskNum(taskNumero);
        return task;
    }

    private String cronGenerate(String segundos, String minutos, String horas, String dia, String mes, String diaDaSemana) {
        return segundos + " " + minutos + " " + horas + " " + dia + " " + mes + " " + diaDaSemana;
    }

    @Override
    public Runnable taskRunnable(Task task, Integer taskNumero) {
        Runnable taskRun = null;
        switch (taskNumero) {
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

    @Override
    public Task save(Task task, String jobId) {
        task.setJobId(jobId);
        return taskSchedulingRepository.save(task);
    }

    @Override
    public List<Task> listAll() {
        return taskSchedulingRepository.findAll();
    }

    @Override
    public Task findByJobId(String jobId) {
        return taskSchedulingRepository.findByJobId(jobId);
    }

    @Override
    public void deleteTask(Task task) {
        taskSchedulingRepository.delete(task);
    }

    @PostConstruct
    public void verificarTasks() {
        log.info("--------------------Verificando tasks existentes--------------------");
        System.out.println("--------------------Verificando tasks existentes--------------------");
        List<Task> list = listAll();
        Runnable taskRun = null;
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Task task = list.get(i);
                if (task.getActive() == true) {
                    log.info("--------------------Iniciando tasks " + task.getNomeTask()+" --------------------");
                    System.out.println("Iniciando tasks " + task.getNomeTask()+" --------------------");
                    taskRun = taskRunnable(task, task.getTaskNum());
                    scheduleATask(task.getJobId(), task.getNomeTask(), taskRun, task.getCronExp());
                    log.info("--------------------Task iniciada--------------------");
                    System.out.println("--------------------Task iniciada--------------------");
                }
            }
        } else {
            log.info("--------------------Nenhuma task encontrada--------------------");
            System.out.println("--------------------Nenhuma task encontrada--------------------");
        }

    }


}
