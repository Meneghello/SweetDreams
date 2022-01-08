package com.SweetDreams.sweetDreams.Controller;

import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Model.DTOs.ClienteDto;
import com.SweetDreams.sweetDreams.Model.DTOs.NovoClienteDto;
import com.SweetDreams.sweetDreams.Services.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping(value = "/cliente")
@Api(value = "Cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

    //Cadastro de novo cliente
    @PostMapping(value = "/cadastro")
    @ApiOperation(value = "Cadastro de cliente")
    public ResponseEntity<Object> CadastroCliente(@RequestBody @Valid NovoClienteDto novoClienteDto) {
        log.info("Cadastrando novo cliente");
        if (clienteService.findByCpf(novoClienteDto.getCpf()) == null) {
            Cliente cliente = clienteService.cadastroDto(novoClienteDto);
            clienteService.save(cliente);
            log.info("Cliente " + cliente.getNome() + " cadastrado");
            return new ResponseEntity<>(clienteService.findByCpf(cliente.getCpf()), HttpStatus.OK);
        }
        log.info("Cliente " + novoClienteDto.getNome() + " já cadastrado");
        return new ResponseEntity<>("Cliente já cadastrado", HttpStatus.BAD_REQUEST);
    }


    //Update de um cliente
    @PutMapping(value = "atualizacao/{cpf}")
    @ApiOperation(value = "Update do cadastro de cliente")
    public ResponseEntity<Object> UpdateCliente(@RequestBody @Valid ClienteDto clienteDto, @PathVariable("cpf") String cpf) {
        log.info("Atualizando cliente");
        if (clienteService.findByCpf(cpf) != null) {
            Cliente cliente = clienteService.atualizacaoDto(clienteDto);
            clienteService.update(cliente, cpf);
            log.info("Cliente " + cliente.getNome() + " atualizado");
            return new ResponseEntity<>(clienteService.findByCpf(cpf), HttpStatus.OK);
        }
        log.info("Cliente {} não encontrado", clienteDto.getNome());
        return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
    }

    //Delete cliente
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "Deletar um cliente")
    @ApiResponses(@ApiResponse(code = 202, message = "Requisição aceita e concluida"))
    public ResponseEntity<Object> DeleteCliente(@RequestParam("cpf") String cpf) {
        log.info("Deletando cliente");
        if (clienteService.findByCpf(cpf) != null) {
            String nome = clienteService.findByCpf(cpf).getNome();
            clienteService.delete(clienteService.findByCpf(cpf));
            log.info("Cliente " + nome + " deletado");
            return new ResponseEntity<>("Cliente " + nome + " deletado", HttpStatus.ACCEPTED);
        }
        log.info("Cliente não encontrado");
        return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
    }

    //Busca por cpf
    @GetMapping(value = "/busca")
    @ApiOperation("Busca cliente por cpf")
    public ResponseEntity<Object> BuscaCliente(@RequestParam("cpf") String cpf) {
        log.info("Buscando cliente com CPF {}", cpf);
        if (clienteService.findByCpf(cpf) != null) {
            log.info("Cliente " + clienteService.findByCpf(cpf).getNome() + " encontrado");
            return new ResponseEntity<>(clienteService.findByCpf(cpf), HttpStatus.OK);
        }
        log.info("Cliente não encontrado");
        return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
    }

    //Lista todos os produtos
    @GetMapping(value = "/")
    @ApiOperation(value = "Lista todos os clientes")
    public ResponseEntity<Object> ListaClientes() {
        log.info("Listados todos os clientes \r\n {} Clientes encontrados",
                clienteService.findAll().size());
        return new ResponseEntity<>(clienteService.findAll(), HttpStatus.OK);
    }


}
