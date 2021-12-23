package com.SweetDreams.sweetDreams.Controller;


import com.SweetDreams.sweetDreams.Model.Vendedor;
import com.SweetDreams.sweetDreams.Services.VendedorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> CadastroVendedor (@RequestBody @Valid Vendedor vendedor){
        if (vendedorService.findByCpf(vendedor.getCliente().getCpf())==null){
            vendedor.getCliente().setNome(vendedor.getCliente().getNome().toLowerCase());
            vendedor.setCodigoVendedor(vendedorService.gerarCodigoVendedor());
            vendedorService.save(vendedor);
            log.info("Vendedor " + vendedor.getCliente().getNome()+" cadastrado (código " + vendedor.getCodigoVendedor()+ ")");
            return ResponseEntity.ok("Vendedor " + vendedor.getCliente().getNome()+" cadastrado (código " + vendedor.getCodigoVendedor() + ")");
        }
        log.info("Vendedor " + vendedor.getCliente().getNome() + " já cadastrado");
        return ResponseEntity.badRequest().build();
    }

    //Update vendedor
    @PutMapping(value = "/atualizacao/{cpf}")
    @ApiOperation(value = "Update de cadastro de vendedor")
    public ResponseEntity<Object> UpdateVendedor (@RequestBody @Valid Vendedor vendedor, @PathVariable("cpf") String cpf){
        if (vendedorService.findByCpf(cpf)!=null){
            vendedor.getCliente().setNome(vendedor.getCliente().getNome().toLowerCase());
            vendedorService.update(vendedor,cpf);
            log.info("Vendedor " + vendedor.getCliente().getNome().toLowerCase() + " atualizado");
            return ResponseEntity.ok("Vendedor " + vendedor.getCliente().getNome().toLowerCase() + " atualizado");
        }
        log.info("Vendedor inexistente");
        return ResponseEntity.badRequest().build();
    }

    //delete vendedor por cpf
    @DeleteMapping(value = "/delete/{cpf}")
    @ApiOperation(value = "Deletar um vendedor por cpf")
    public ResponseEntity<Object> DeletarVendedorCpf (@PathVariable("cpf") String cpf){
        if(vendedorService.findByCpf(cpf)!=null){
            String nome = vendedorService.findByCpf(cpf).getCliente().getNome();
            vendedorService.delete(vendedorService.findByCpf(cpf));
            log.info("Vendedor " + nome + " deletado");
            return ResponseEntity.ok("Vendedor " + nome + " deletado");
        }
        log.info("Vendedor não encontrado");
        return ResponseEntity.badRequest().build();
    }

    //Busca por cpf
    @GetMapping(value = "/busca/{cpf}")
    @ApiOperation("Busca vendedor por cpf")
    public ResponseEntity<Object> BuscaVendedorCpf(@PathVariable("cpf") String cpf){
        if (vendedorService.findByCpf(cpf)!=null){
            log.info("Vendedor " + vendedorService.findByCpf(cpf).getCliente().getNome() + " encontrado");
            return ResponseEntity.ok(vendedorService.findByCpf(cpf));
        }
        log.info("Vendedor inexistente");
        return ResponseEntity.badRequest().build();
    }




//    //delete vendedor por codigo
//    @DeleteMapping(value = "/delete/{codigoVendedor}")
//    @ApiOperation(value = "Deletar um vendedor por codigo")
//    public ResponseEntity<Object> DeletarVendedorCpf (@PathVariable("codigoVendedor") Long codigoVendedor){
//        String nome = vendedorService.findByCodigoVendedor(codigoVendedor).getCliente().getNome();
//        if(vendedorService.findByCodigoVendedor(codigoVendedor)!=null){
//            vendedorService.delete(vendedorService.findByCodigoVendedor(codigoVendedor));
//            log.info("Vendedor " + nome + " deletado");
//            return ResponseEntity.ok("Vendedor " + nome + " deletado");
//        }
//        log.info("Vendedor " + nome + " não encontrado");
//        return ResponseEntity.badRequest().build();
//    }


//    //Busca vendedor por codigo vendedor
//    @GetMapping(value = "/busca/{codigoVendedor}")
//    @ApiOperation("Busca vendedor por codigo")
//    public ResponseEntity<Object> BuscaVendedorCodigo(@PathVariable("codigoVendedor") Long codigoVendedor){
//        if (vendedorService.findByCodigoVendedor(codigoVendedor)!=null){
//            log.info("Vendedor " + vendedorService.findByCodigoVendedor(codigoVendedor).getCliente().getNome() + " encontrado");
//            return ResponseEntity.ok(vendedorService.findByCodigoVendedor(codigoVendedor));
//        }
//        log.info("Vendedor inexistente");
//        return ResponseEntity.badRequest().build();
//    }



}
