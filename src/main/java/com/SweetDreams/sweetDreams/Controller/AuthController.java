package com.SweetDreams.sweetDreams.Controller;


import com.SweetDreams.sweetDreams.Models.DTOs.ClienteAuthDto;
import com.SweetDreams.sweetDreams.Models.Task;
import com.SweetDreams.sweetDreams.Security.JWTUtil;
import com.SweetDreams.sweetDreams.Security.UserSS;
import com.SweetDreams.sweetDreams.Services.Impl.TaskExecutorSout;
import com.SweetDreams.sweetDreams.Services.Impl.UserDetailsServiceImpl;
import com.SweetDreams.sweetDreams.Services.TaskSchedulingService;
import com.SweetDreams.sweetDreams.Services.UserService;
import com.SweetDreams.sweetDreams.Services.Impl.TaskSpamEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.ScheduledTaskHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value="/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    TaskSchedulingService taskSchedulingService;
    @Autowired
    ScheduledTaskHolder taskHolder;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping(value = "/refreshToken")
    public ResponseEntity<Object> refreshToken(HttpServletResponse response){
        log.info("Reiniciando tempo do token");
        UserSS userSS = UserService.authenticated();
        String token = jwtUtil.generateToken(userSS.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        log.info("Novo token criado");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login (@RequestBody @Valid ClienteAuthDto clienteAuthDto, HttpServletResponse res){
        log.info("Tentando logar");
        String token = userDetailsService.logar(clienteAuthDto);
        res.addHeader("Authorization", token);
        log.info("Logado com sucesso");
        return new ResponseEntity<>("Logado com sucesso",HttpStatus.OK);
    }

    @PostMapping(value = "/new")
    public ResponseEntity<Object> task (@RequestBody Task task){
        try {
            log.info("Adicionando nova task");
            TaskSpamEmail taskExecutor = new TaskSpamEmail();
            taskExecutor.setTaskDef(task);
            String jobId = UUID.randomUUID().toString().replace( "-","");
            taskSchedulingService.scheduleATask(jobId,task.getNomeTask() ,taskExecutor,task.getCronExp());
            log.info("Task adicionada");
            return new ResponseEntity<>("Task adicionada, jobId: "+jobId, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Falha ao adicionar task", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(path="/remove")
    public ResponseEntity<Object> removeJob(@RequestParam("jobId") String jobid) {
        log.info("Removendo task");
        taskSchedulingService.removeScheduledTask(jobid);
        log.info("Task removida");
        return new ResponseEntity<>("Task removida", HttpStatus.ACCEPTED);
    }
    @GetMapping(path = "/lista")
    public ResponseEntity<Object> listaTask(){
        log.info("Listando todas as tasks encontradas\n\rForam encontradas {} tasks",taskHolder.getScheduledTasks().size());
        return new ResponseEntity<>(taskSchedulingService.getAllTasks(), HttpStatus.OK);
    }
}
