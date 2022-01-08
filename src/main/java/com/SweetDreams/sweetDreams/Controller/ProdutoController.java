package com.SweetDreams.sweetDreams.Controller;


import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Model.DTOs.ProdutoDto;
import com.SweetDreams.sweetDreams.Model.Produto;
import com.SweetDreams.sweetDreams.Services.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(value = "/produto")
@Api(value = "Produto")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    private static Logger log = LoggerFactory.getLogger(ProdutoController.class);

    @PostMapping(value = "/cadastro")
    @ApiOperation(value = "Cadastro Produto")
    public ResponseEntity<Object> CadastroProduto(@RequestBody @Valid ProdutoDto produtoDto) {
        log.info("Cadastrando um novo produto");
        if (produtoService.findByNomeProduto(produtoDto.getNomeProduto()) == null) {
            Produto produto = produtoService.cadastroDto(produtoDto);
            produtoService.save(produto);
            log.info("Produto " + produto.getNomeProduto() + " cadastrado");
            return new ResponseEntity<>(produto, HttpStatus.OK);
        }
        log.info("Produto já cadastrado: " + produtoDto.getNomeProduto());
        return new ResponseEntity<>("Produto já cadastrado", HttpStatus.BAD_REQUEST);

    }

    //Update de um produto
    @PutMapping(value = "/atualizacao/{nomeProduto}")
    @ApiOperation(value = "Update do cadastro de produto")
    public ResponseEntity<Object> UpdateProduto(@RequestBody @Valid ProdutoDto produtoDto,
                                                @PathVariable String nomeProduto) {
        log.info("Atualizando produto");
        if (produtoService.findByNomeProduto(nomeProduto) != null) {
            Produto produto = produtoService.cadastroDto(produtoDto);
            produtoService.update(produto, nomeProduto);

            log.info("Produto " + produto.getNomeProduto() + " atualizado");
            return new ResponseEntity<>(produtoService.findByNomeProduto(nomeProduto), HttpStatus.OK);
        }
        log.info("Produto inexistente");
        return new ResponseEntity<>("Produto Inexistente", HttpStatus.NOT_FOUND);
    }

    //Deleta um produto
    @DeleteMapping(value = "/delete/{nomeProduto}")
    @ApiOperation(value = "Deletar produto")
    @ApiResponses(@ApiResponse(code = 202, message = "Requisição aceita e concluida"))
    public ResponseEntity<Object> DeleteProduto(@PathVariable String nomeProduto) {
        log.info("Deletando produto");
        if (produtoService.findByNomeProduto(nomeProduto) != null) {
            produtoService.delete(produtoService.findByNomeProduto(nomeProduto.toLowerCase()));
            log.info("Produto " + nomeProduto + " deletado");
            return new ResponseEntity<>("Produto " + nomeProduto + " deletado", HttpStatus.ACCEPTED);
        }
        log.info("Produto inexistente");
        return new ResponseEntity<>("Produto Inexistente", HttpStatus.NOT_FOUND);
    }

    //Busca por nome de produto
    @GetMapping(value = "/busca/{nomeProduto}")
    @ApiOperation(value = "Buscar produto por nome")
    public ResponseEntity<Object> BuscaProduto(@PathVariable("nomeProduto") String nomeProduto) {
        log.info("Buscando produto {}", nomeProduto);
        if (produtoService.findByNomeProduto(nomeProduto.toLowerCase()) != null) {
            log.info("Produto " + nomeProduto + " encontrado");
            return new ResponseEntity<>(produtoService.findByNomeProduto(nomeProduto), HttpStatus.OK);
        }
        log.info("Produto inexistente");
        return new ResponseEntity<>("Produto Inexistente", HttpStatus.NOT_FOUND);
    }

    //Lista todos os produtos
    @GetMapping(value = "/")
    @ApiOperation(value = "Lista todos os produtos")
    public ResponseEntity<Object> ListaProdutos() {
        log.info("Listados todos os produtos \r\n {} Produtos encontrados",
                produtoService.findAll().size());
        return new ResponseEntity<>(produtoService.findAll(), HttpStatus.OK);
    }


}
