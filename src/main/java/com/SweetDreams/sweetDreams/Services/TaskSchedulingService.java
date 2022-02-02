package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Models.DTOs.TaskDto;
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
}
