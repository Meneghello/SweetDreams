package com.SweetDreams.sweetDreams.Controller;


import com.SweetDreams.sweetDreams.Model.Produto;
import com.SweetDreams.sweetDreams.Services.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

import java.util.Locale;

@RestController
@RequestMapping(value = "/produto")
@Api(value ="Produto")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;


    private static Logger log = LoggerFactory.getLogger(ProdutoController.class);


    //Cadastro novo produto -> se já existe retorna erro badRequest(erro 400)
    @PostMapping(value = "/cadastro")
    @ApiOperation(value = "Cadastro Produto")
    public ResponseEntity<Object> CadastroProduto(@RequestBody Produto produto){

        if (produtoService.findByNomeProduto(produto.getNomeProduto().toLowerCase())==null){
            produto.setNomeProduto(produto.getNomeProduto().toLowerCase());
            produtoService.save(produto);
            log.info("Produto " + produto.getNomeProduto() + " cadastrado");
            return ResponseEntity.ok("Produto " + produto.getNomeProduto() + " cadastrado");
        }
        log.info("Produto já cadastrado: " + produto.getNomeProduto());
        return ResponseEntity.badRequest().build();

    }

    //Update de um produto
    @PutMapping(value = "/atualizacao/{nomeProduto}")
    @ApiOperation(value = "Update do cadastro de produto")
    public ResponseEntity<Object> UpdateProduto(@RequestBody Produto produto, @PathVariable String nomeProduto){
        if(produtoService.findByNomeProduto(nomeProduto.toLowerCase())!=null){
            produto.setNomeProduto(produto.getNomeProduto().toLowerCase());
            produtoService.update(produto, nomeProduto);
            log.info("Produto " + produto.getNomeProduto().toLowerCase() + " atualizado");
            return ResponseEntity.ok("Produto " + produto.getNomeProduto().toLowerCase() + " atualizado");
        }
        log.info("Produto inexistente");
        return ResponseEntity.badRequest().build();
    }

    //Deleta um produto
    @DeleteMapping(value = "/delete/{nomeProduto}")
    @ApiOperation(value = "Deletar produto")
    public ResponseEntity<Object> DeleteProduto(@PathVariable String nomeProduto){
        if(produtoService.findByNomeProduto(nomeProduto.toLowerCase())!=null){
            produtoService.delete(produtoService.findByNomeProduto(nomeProduto.toLowerCase()));
            log.info("Produto " + nomeProduto.toLowerCase() + " deletado");
            return ResponseEntity.ok("Produto " + nomeProduto.toLowerCase() + " deletado");
        }
        log.info("Produto inexistente");
        return ResponseEntity.badRequest().build();
    }

    //Busca por nome de produto
    @GetMapping(value = "/busca/{nomeProduto}")
    @ApiOperation(value = "Buscar produto por nome")
    public ResponseEntity<Object> BuscaProduto(@PathVariable("nomeProduto") String nomeProduto){
        if (produtoService.findByNomeProduto(nomeProduto.toLowerCase())!=null){
            log.info("Produto " + nomeProduto + " encontrado");
            return ResponseEntity.ok(produtoService.findByNomeProduto(nomeProduto.toLowerCase()));
        }
        log.info("Produto inexistente");
        return ResponseEntity.badRequest().build();
    }

}
