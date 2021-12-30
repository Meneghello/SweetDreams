package com.SweetDreams.sweetDreams.Controller;



import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Model.DTOs.ClienteDto;
import com.SweetDreams.sweetDreams.Model.DTOs.NovoVendedorDto;
import com.SweetDreams.sweetDreams.Model.Vendedor;
import com.SweetDreams.sweetDreams.Services.VendedorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<Object> CadastroVendedor (@RequestBody @Valid NovoVendedorDto novoVendedorDto){

        if (vendedorService.findByCpf(novoVendedorDto.getCliente().getCpf())==null){
            Vendedor vendedor = vendedorService.cadastroDto(novoVendedorDto);
            vendedorService.save(vendedor);
            log.info("Vendedor {} Cadastrado", novoVendedorDto.getCliente().getNome());
            return ResponseEntity.ok(vendedor);
        }
        log.info("Vendedor {} já Cadastrado", novoVendedorDto.getCliente().getNome());
        return new ResponseEntity<>("Vendedore já cadastrado", HttpStatus.BAD_REQUEST);
    }

    //Update vendedor
    @PutMapping(value = "/atualizacao/{cpf}")
    @ApiOperation(value = "Update de cadastro de vendedor")
    public ResponseEntity<Object> UpdateVendedor (@RequestBody @Valid ClienteDto vendedorDto, @PathVariable("cpf") String cpf){
        if (vendedorService.findByCpf(cpf)!=null) {
            Cliente cliente = vendedorService.atualizacaoDto(vendedorDto);
            vendedorService.update(cliente, cpf);
            log.info("Vendedor atualizado");
            return ResponseEntity.ok(vendedorService.findByCpf(cpf));
        }
        log.info("Vendedor não encontrado");
        return new ResponseEntity<>("Vendedor não encontrado", HttpStatus.NOT_FOUND);

    }

    //Busca por cpf
    @GetMapping(value = "/busca")
    @ApiOperation("Busca vendedor por cpf")
    public ResponseEntity<Object> BuscaVendedorCpf(@RequestParam("cpf") String cpf){
        if (vendedorService.findByCpf(cpf)!=null){
            log.info("Vendedor {} encontrado ",cpf);
            return ResponseEntity.ok(vendedorService.findByCpf(cpf));
        }
        log.info("Vendedor não encontrado");
        return new ResponseEntity<>("Vendedor não encontrado", HttpStatus.NOT_FOUND);
    }

        //Busca vendedor por codigo vendedor
    @GetMapping(value = "/busca/{codigoVendedor}")
    @ApiOperation("Busca vendedor por codigo")
    public ResponseEntity<Object> BuscaVendedorCodigo(@PathVariable("codigoVendedor") Long codigoVendedor){
        if (vendedorService.findByCodigoVendedor(codigoVendedor)!=null){
            log.info("Vendedor {} encontrado", codigoVendedor);
            return ResponseEntity.ok(vendedorService.findByCodigoVendedor(codigoVendedor));
        }
        log.info("Vendedor não encontrado");
        return new ResponseEntity<>("Vendedor não encontrado", HttpStatus.NOT_FOUND);
    }

    //Lista todos os vendedores
    @GetMapping(value = "/")
    @ApiOperation(value = "Lista todos os vendedores")
    public List<Vendedor> ListaVendedores(){

        log.info("Listados todos os vendedores \r\n {} Vendedores encontrados",
                vendedorService.findAll().size());
        return vendedorService.findAll();
    }

    //delete vendedor por codigo
    @DeleteMapping(value = "/delete/{codigoVendedor}")
    @ApiOperation(value = "Deletar um vendedor por codigo")
    public ResponseEntity<Object> DeletarVendedorCodigo (@PathVariable("codigoVendedor") Long codigoVendedor){
        if (vendedorService.findByCodigoVendedor(codigoVendedor)!=null){
            vendedorService.delete(vendedorService.findByCodigoVendedor(codigoVendedor));
            log.info("Vendedor {} deletado",codigoVendedor);
            return ResponseEntity.ok("Vendedor " + codigoVendedor + " deletado");
        }
        log.info("Vendedor não encontrado");
        return new ResponseEntity<>("Vendedor não encontrado", HttpStatus.NOT_FOUND);
    }

    //delete vendedor por cpf
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "Deletar um vendedor por cpf")
    public ResponseEntity<Object> DeletarVendedorCpf (@RequestParam("cpf") String cpf){
        if (vendedorService.findByCpf(cpf)!=null){
            vendedorService.delete(vendedorService.findByCpf(cpf));
            log.info("Vendedor {} deletado",cpf);
            return ResponseEntity.ok("Vendedor " + cpf + " deletado");
        }
        log.info("Vendedor não encontrado");
        return new ResponseEntity<>("Vendedor não encontrado", HttpStatus.NOT_FOUND);
    }




}
