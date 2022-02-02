package com.SweetDreams.sweetDreams.Controller;

import com.SweetDreams.sweetDreams.Models.Task;
import com.SweetDreams.sweetDreams.Services.Impl.TaskSpamEmail;
import com.SweetDreams.sweetDreams.Services.TaskSchedulingService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.ScheduledTaskHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value="/task")
public class TaskController {
    @Autowired
    TaskSchedulingService taskSchedulingService;
    @Autowired
    ScheduledTaskHolder taskHolder;
    @Autowired
    TaskSpamEmail taskSpamEmail;

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    @PostMapping(value = "/new")
    @ApiOperation(value = "Cadastrar nova task")
    public ResponseEntity<Object> task (@RequestBody Task task){
        try {
            log.info("Adicionando nova task");
            taskSpamEmail.setTaskDef(task);
            String jobId = UUID.randomUUID().toString().replace( "-","");
            taskSchedulingService.scheduleATask(jobId,task.getNomeTask() ,taskSpamEmail,task.getCronExp());
            log.info("Task adicionada");
            return new ResponseEntity<>("Task adicionada, jobId: "+jobId, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Falha ao adicionar task", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(path="/remove")
    @ApiOperation(value = "Remover task por jobId")
    public ResponseEntity<Object> removeJob(@RequestParam("jobId") String jobid) {
        log.info("Removendo task");
        taskSchedulingService.removeScheduledTask(jobid);
        log.info("Task removida");
        return new ResponseEntity<>("Task removida", HttpStatus.ACCEPTED);
    }
    @GetMapping(path = "/lista")
    @ApiOperation(value = "Listar todas as tasks")
    public ResponseEntity<Object> listaTask(){
        log.info("Listando todas as tasks encontradas\n\rForam encontradas {} tasks",taskSchedulingService.getAllTasks().size());
        return new ResponseEntity<>(taskSchedulingService.getAllTasks(), HttpStatus.OK);
    }
}
