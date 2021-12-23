package com.SweetDreams.sweetDreams.Controller;

import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Services.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.Arrays;

@RestController
@RequestMapping(value = "/cliente")
@Api(value ="Cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    private static Logger log = LoggerFactory.getLogger(ClienteController.class);

    //Cadastro de novo cliente
    @PostMapping(value = "/cadastro")
    @ApiOperation(value = "Cadastro de cliente")
    public ResponseEntity<Object> CadastroCliente (@RequestBody @Valid Cliente cliente){
        if (clienteService.findByCpf(cliente.getCpf())==null){
            cliente.setNome(cliente.getNome().toLowerCase());
            clienteService.save(cliente);
            log.info("Cliente " + cliente.getNome() + " cadastrado");
            return ResponseEntity.ok("Cliente " + cliente.getNome() + " cadastrado");
        }
        log.info("Cliente " + cliente.getNome() + " já cadastrado");
        return ResponseEntity.badRequest().build();
    }

    //Update de um cliente
    @PutMapping(value = "atualizacao/{cpf}")
    @ApiOperation(value = "Update do cadastro de cliente")
    public ResponseEntity<Object> UpdateCliente (@RequestBody @Valid Cliente cliente, @PathVariable("cpf") String cpf){
        if (clienteService.findByCpf(cpf)!=null){
            cliente.setNome(cliente.getNome().toLowerCase());
            clienteService.update(cliente, cpf);
            log.info("Cliente " + cliente.getNome().toLowerCase() + " atualizado");
            return ResponseEntity.ok("Cliente " + cliente.getNome().toLowerCase() + " atualizado");
        }
        log.info("Cliente inexistente");
        return ResponseEntity.badRequest().build();
    }

    //Delete cliente
    @DeleteMapping(value = "/delete/{cpf}")
    @ApiOperation(value = "Deletar um cliente")
    public ResponseEntity<Object> DeleteCliente(@PathVariable("cpf") String cpf){

        if (clienteService.findByCpf(cpf)!=null){
            String nome = clienteService.findByCpf(cpf).getNome();
            clienteService.delete(clienteService.findByCpf(cpf));
            log.info("Cliente " + nome + " deletado");
            return ResponseEntity.ok("Cliente " + nome + " deletado");
        }
        log.info("Cliente inexistente");
        return ResponseEntity.badRequest().build();
    }

    //Busca por cpf
    @GetMapping(value = "/busca/{cpf}")
    @ApiOperation("Busca cliente por cpf")
    public ResponseEntity<Object> BuscaCliente(@PathVariable("cpf") String cpf){
        if (clienteService.findByCpf(cpf)!=null){
            log.info("Cliente " + clienteService.findByCpf(cpf).getNome() + " encontrado");
            return ResponseEntity.ok(clienteService.findByCpf(cpf));
        }
        log.info("Cliente inexistente");
        return ResponseEntity.badRequest().build();
    }




}
