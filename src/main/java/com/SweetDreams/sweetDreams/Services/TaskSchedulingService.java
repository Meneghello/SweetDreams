package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Models.DTOs.NewTaskDto;
import com.SweetDreams.sweetDreams.Models.Task;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface TaskSchedulingService {
    void scheduleATask(String jobId, String nomeTask, Runnable task, String cronExpression);
    void removeScheduledTask(String jobId);
    List<Map.Entry<String, String>> getAllTasks();
    Task fromDto(NewTaskDto newTaskDto, Integer taskNumero);
    Runnable taskRunnable (Task task, Integer taskNumero);
    Task save(Task task, String jobId);
    List<Task> listAll();
    Task findByJobId(String jobId);
    void deleteTask(Task task);
}
