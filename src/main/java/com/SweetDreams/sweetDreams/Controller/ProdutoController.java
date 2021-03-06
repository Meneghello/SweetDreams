package com.SweetDreams.sweetDreams.Controller;


import com.SweetDreams.sweetDreams.Models.DTOs.ProdutoDto;
import com.SweetDreams.sweetDreams.Models.Operadores;
import com.SweetDreams.sweetDreams.Models.Produto;
import com.SweetDreams.sweetDreams.Services.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

@RestController
@Validated
@RequestMapping(value = "/produto")
@Api(value = "Produto")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    private static Logger log = LoggerFactory.getLogger(ProdutoController.class);

    //@PreAuthorize("hasAnyRole('admin')")
    @PostMapping(value = "/cadastro")
    @ApiOperation(value = "Cadastro Produto")
    public ResponseEntity<Object> CadastroProduto(@RequestBody @Valid ProdutoDto produtoDto) {
        log.info("Cadastrando um novo produto");
        if (produtoService.findByNomeProduto(produtoDto.getNomeProduto().toLowerCase()) == null) {
            Produto produto = produtoService.cadastroDto(produtoDto);
            produtoService.save(produto);
            log.info("Produto " + produto.getNomeProduto() + " cadastrado");
            return new ResponseEntity<>(produto, HttpStatus.OK);
        }
        log.info("Produto já cadastrado: " + produtoDto.getNomeProduto());
        return new ResponseEntity<>("Produto já cadastrado", HttpStatus.BAD_REQUEST);

    }

    //Update de um produto
    //@PreAuthorize("hasAnyRole('admin')")
    @PutMapping(value = "/atualizacao/{nomeProduto}")
    @ApiOperation(value = "Update do cadastro de produto")
    public ResponseEntity<Object> UpdateProduto(@RequestBody @Valid ProdutoDto produtoDto,
                                                @PathVariable(value = "nomeProduto") String nomeProduto) {
        log.info("Atualizando produto");
        if (produtoService.findByNomeProduto(nomeProduto.toLowerCase()) != null) {
            Produto produto = produtoService.cadastroDto(produtoDto);
            produtoService.update(produto, nomeProduto);

            log.info("Produto " + produto.getNomeProduto() + " atualizado");
            return new ResponseEntity<>(produtoService.findByNomeProduto(nomeProduto.toLowerCase()), HttpStatus.OK);
        }
        log.info("Produto inexistente");
        return new ResponseEntity<>("Produto Inexistente", HttpStatus.NOT_FOUND);
    }

    //Atualizar quantidade de produto
    //@PreAuthorize("hasAnyRole('admin','vendedor')")
    @PutMapping(value = "/reposicao/{nomeProduto}")
    @ApiOperation(value = "Atualizar quantidade de produto")
    public ResponseEntity<Object> reposicaoProduto(@PositiveOrZero @RequestParam(value = "quantidade",
            defaultValue = "0") Long quantidade,
                                                   @RequestParam("operador") Operadores operadores,
                                                   @PathVariable(value = "nomeProduto") String nomeProduto) {
        log.info("Atualizando quantidade de produto");
        if (produtoService.findByNomeProduto(nomeProduto.toLowerCase()) != null) {
            Produto produto = produtoService.findByNomeProduto(nomeProduto.toLowerCase());
            Long quantidadeFinal = produto.getQuantidade();
            String mensagem = null;
            switch (operadores) {
                case adicao:
                    log.info("Adicionando produtos");
                    quantidadeFinal += quantidade;
                    mensagem =
                            "Foram adicionadas " + quantidade + " unidade do produto " + nomeProduto.toLowerCase() +
                                    ".\nQuantidade atualizada: ";
                    break;
                case retirada:
                    if (quantidadeFinal >= quantidade) {
                        log.info("Retirando produtos");
                        quantidadeFinal -= quantidade;
                        mensagem = "Foram retiradas " + quantidade + " unidades do produto " + nomeProduto.toLowerCase() +
                                ".\nQuantidade atualizada: ";
                        break;
                    }
                    log.info("Quantidade Insuficiente");
                    return new ResponseEntity<>("Quantidade Insuficiente", HttpStatus.BAD_REQUEST);
            }
            produto.setQuantidade(quantidadeFinal);
            produtoService.save(produto);
            log.info(mensagem + produtoService.findByNomeProduto(nomeProduto.toLowerCase()).getQuantidade());
            return new ResponseEntity<>(mensagem + produtoService.findByNomeProduto(nomeProduto.toLowerCase()).getQuantidade(), HttpStatus.OK);
        }
        log.info("Produto inexistente");
        return new ResponseEntity<>("Produto Inexistente", HttpStatus.NOT_FOUND);
    }


    //Deleta um produto
    //@PreAuthorize("hasAnyRole('admin')")
    @DeleteMapping(value = "/delete/{nomeProduto}")
    @ApiOperation(value = "Deletar produto")
    @ApiResponses(@ApiResponse(code = 202, message = "Requisição aceita e concluida"))
    public ResponseEntity<Object> DeleteProduto(@PathVariable(value =
            "nomeProduto") String nomeProduto) {
        log.info("Deletando produto");
        if (produtoService.findByNomeProduto(nomeProduto.toLowerCase()) != null) {
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
            return new ResponseEntity<>(produtoService.findByNomeProduto(nomeProduto.toLowerCase()), HttpStatus.OK);
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
