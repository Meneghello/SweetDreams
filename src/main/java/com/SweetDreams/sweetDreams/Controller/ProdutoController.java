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
    public ResponseEntity CadastroProduto(@RequestBody Produto produto){

        if (produtoService.findByNomeProduto(produto.getNomeProduto().toLowerCase())!=null){
            log.info("Produto já cadastrado: " + produto.getNomeProduto());
            return ResponseEntity.badRequest().build();
        }
        else{
            produto.setNomeProduto(produto.getNomeProduto().toLowerCase());
            produtoService.save(produto);
            log.info("Produto " + produto.getNomeProduto() + " cadastrado");
            return ResponseEntity.ok("Produto " + produto.getNomeProduto() + " cadastrado");
        }
    }

    //Update de um produto ja existente
    @PutMapping(value = "/atualizacao/{nomeProduto}")
    @ApiOperation(value = "Update do cadastro de produto")
    public ResponseEntity UpdateProduto(@RequestBody Produto produto, @PathVariable String nomeProduto){
        if(produtoService.findByNomeProduto(nomeProduto.toLowerCase())==null){
            log.info("Produto inexistente");
            return ResponseEntity.badRequest().build();
        }
        produto.setNomeProduto(produto.getNomeProduto().toLowerCase());
        produtoService.update(produto, nomeProduto);
        log.info("Produto " + produto.getNomeProduto() + " atualizado");
        return ResponseEntity.ok("Produto " + produto.getNomeProduto() + " atualizado");
    }

//    public ResponseEntity DeleteProduto(){
//
//    }
//    public ResponseEntity BuscaProduto(@RequestParam("nomeProduto") String nomeProduto){
//
//    }

}
