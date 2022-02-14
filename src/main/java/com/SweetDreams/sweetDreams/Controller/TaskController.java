package com.SweetDreams.sweetDreams.Controller;

import com.SweetDreams.sweetDreams.Handle.RestExceptionHandler;
import com.SweetDreams.sweetDreams.Models.DTOs.NewTaskDto;
import com.SweetDreams.sweetDreams.Models.Task;
import com.SweetDreams.sweetDreams.Services.TaskSchedulingService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value="/task")
public class TaskController {
    @Autowired
    TaskSchedulingService taskSchedulingService;


    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    @PostMapping(value = "/new")
    @ApiOperation(value = "Cadastrar nova task")
    public ResponseEntity<Object> task (@RequestBody @Valid NewTaskDto newTaskDto, Integer taskNumero){
        Task task = taskSchedulingService.fromDto(newTaskDto, taskNumero);
        try {
            log.info("Adicionando nova task");
            Runnable taskRun = taskSchedulingService.taskRunnable(task, taskNumero);

            String jobId = UUID.randomUUID().toString().replace( "-","");
            taskSchedulingService.scheduleATask(jobId,task.getNomeTask() ,taskRun,task.getCronExp());

            if (task.getSave()==true){
                taskSchedulingService.save(task, jobId);
            }

            log.info("Task adicionada");
            return new ResponseEntity<>("Task adicionada, jobId: "+jobId, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            log.info("Task nao adicionada");
            RestExceptionHandler restExceptionHandler = new RestExceptionHandler();
            return  restExceptionHandler.illegalArgument(e);
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

    @DeleteMapping
    @ApiOperation(value = "Deletar task do banco de dados")
    public ResponseEntity<Object> deletarTask(@RequestParam("jobId") String jobId){
        log.info("Deletando task");
        if (taskSchedulingService.findByJobId(jobId)!=null){
            taskSchedulingService.deleteTask(taskSchedulingService.findByJobId(jobId));
            log.info("Task deletada");
            return new  ResponseEntity<Object>("Task deletada",HttpStatus.ACCEPTED);
        }
        log.info("Task nao encontrada");
        return new  ResponseEntity<Object>("Task nao encontrada",HttpStatus.NOT_FOUND);
    }

}


//TODO
//Melhorar a lista de tasks
//colocar um alterar tasks do BD

