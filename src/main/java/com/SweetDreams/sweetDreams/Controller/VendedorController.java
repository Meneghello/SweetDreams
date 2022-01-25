package com.SweetDreams.sweetDreams.Controller;


import com.SweetDreams.sweetDreams.Models.Cliente;
import com.SweetDreams.sweetDreams.Models.DTOs.ClienteDto;
import com.SweetDreams.sweetDreams.Models.DTOs.NovoVendedorDto;
import com.SweetDreams.sweetDreams.Models.Vendedor;
import com.SweetDreams.sweetDreams.Services.VendedorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/vendedor")
@Api(value = "Vendedores")
public class VendedorController {

    @Autowired
    VendedorService vendedorService;

    private static Logger log = LoggerFactory.getLogger(VendedorController.class);

    //Cadastro novo vendedor
    @PostMapping(value = "/cadastro")
    @ApiOperation(value = "Cadastro novo vendedor")
    public ResponseEntity<Object> CadastroVendedor(@RequestBody @Valid NovoVendedorDto novoVendedorDto) {
        log.info("Cadastrando novo vendedor");
        if (vendedorService.findByCpf(novoVendedorDto.getCliente().getCpf()) == null) {
            Vendedor vendedor = vendedorService.cadastroDto(novoVendedorDto);
            vendedorService.save(vendedor);
            log.info("Vendedor {} Cadastrado", novoVendedorDto.getCliente().getNome());
            return new ResponseEntity<>(vendedor, HttpStatus.OK);
        }
        log.info("Vendedor {} já Cadastrado", novoVendedorDto.getCliente().getNome());
        return new ResponseEntity<>("Vendedor já cadastrado", HttpStatus.BAD_REQUEST);
    }

    //Update vendedor
    @PutMapping(value = "/atualizacao/{cpf}")
    @ApiOperation(value = "Update de cadastro de vendedor")
    public ResponseEntity<Object> UpdateVendedor(@RequestBody @Valid ClienteDto vendedorDto, @PathVariable("cpf") String cpf) {
        log.info("Atualizando vendedor");
        if (vendedorService.findByCpf(cpf) != null) {
            Cliente cliente = vendedorService.atualizacaoDto(vendedorDto);
            vendedorService.update(cliente, cpf);
            log.info("Vendedor atualizado");
            return new ResponseEntity<>(vendedorService.findByCpf(cpf), HttpStatus.OK);
        }
        log.info("Vendedor não encontrado");
        return new ResponseEntity<>("Vendedor não encontrado", HttpStatus.NOT_FOUND);

    }

    //Busca por cpf
    @GetMapping(value = "/busca")
    @ApiOperation("Busca vendedor por cpf")
    public ResponseEntity<Object> BuscaVendedorCpf(@RequestParam("cpf") String cpf) {
        log.info("Buscando vendedor CPF: {}", cpf);
        if (vendedorService.findByCpf(cpf) != null) {
            log.info("Vendedor {} encontrado ", cpf);
            return new ResponseEntity<>(vendedorService.findByCpf(cpf), HttpStatus.OK);
        }
        log.info("Vendedor não encontrado");
        return new ResponseEntity<>("Vendedor não encontrado", HttpStatus.NOT_FOUND);
    }

    //Busca vendedor por codigo vendedor
    @GetMapping(value = "/busca/{codigoVendedor}")
    @ApiOperation("Busca vendedor por codigo")
    public ResponseEntity<Object> BuscaVendedorCodigo(@PathVariable("codigoVendedor") Long codigoVendedor) {
        log.info("Buscando vendedor código: {}", codigoVendedor);
        if (vendedorService.findByCodigoVendedor(codigoVendedor) != null) {
            log.info("Vendedor {} encontrado", codigoVendedor);
            return new ResponseEntity<>(vendedorService.findByCodigoVendedor(codigoVendedor), HttpStatus.OK);
        }
        log.info("Vendedor não encontrado");
        return new ResponseEntity<>("Vendedor não encontrado", HttpStatus.NOT_FOUND);
    }

    //delete vendedor por codigo
    //@PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/delete/{codigoVendedor}")
    @ApiOperation(value = "Deletar um vendedor por codigo")
    @ApiResponses(@ApiResponse(code = 202, message = "Requisição aceita e concluida"))
    public ResponseEntity<Object> DeletarVendedorCodigo(@PathVariable("codigoVendedor") Long codigoVendedor) {
        log.info("Deletando vendedor código: {}", codigoVendedor);
        if (vendedorService.findByCodigoVendedor(codigoVendedor) != null) {
            vendedorService.delete(vendedorService.findByCodigoVendedor(codigoVendedor));
            log.info("Vendedor {} deletado", codigoVendedor);
            return new ResponseEntity<>("Vendedor " + codigoVendedor + " deletado", HttpStatus.ACCEPTED);
        }
        log.info("Vendedor não encontrado");
        return new ResponseEntity<>("Vendedor não encontrado", HttpStatus.NOT_FOUND);
    }

    //delete vendedor por cpf
    //@PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "Deletar um vendedor por cpf")
    @ApiResponses(@ApiResponse(code = 202, message = "Requisição aceita e concluida"))
    public ResponseEntity<Object> DeletarVendedorCpf(@RequestParam("cpf") String cpf) {
        log.info("Deletando vendedor CPF: {}", cpf);
        if (vendedorService.findByCpf(cpf) != null) {
            vendedorService.delete(vendedorService.findByCpf(cpf));
            log.info("Vendedor {} deletado", cpf);
            return new ResponseEntity<>("Vendedor " + cpf + " deletado", HttpStatus.ACCEPTED);
        }
        log.info("Vendedor não encontrado");
        return new ResponseEntity<>("Vendedor não encontrado", HttpStatus.NOT_FOUND);
    }

    //Lista todos os vendedores
    //@PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/")
    @ApiOperation(value = "Lista todos os vendedores")
    public ResponseEntity<Object> ListaVendedores() {
        log.info("Listados todos os vendedores \r\n {} Vendedores encontrados",
                vendedorService.findAll().size());
        return new ResponseEntity<>(vendedorService.findAll(), HttpStatus.OK);
    }
}

