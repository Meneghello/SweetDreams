package com.SweetDreams.sweetDreams.Controller;


import com.SweetDreams.sweetDreams.Models.Cupom;
import com.SweetDreams.sweetDreams.Models.DTOs.NovoClienteDto;
import com.SweetDreams.sweetDreams.Models.DTOs.NovoCupomDto;
import com.SweetDreams.sweetDreams.Services.CupomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/cupom")
@Api(value = "Cupom")
public class CupomController {

    @Autowired
    private CupomService cupomService;

    private static final Logger log = LoggerFactory.getLogger(CupomController.class);


    @PostMapping(value = "/cadastro")
    @ApiOperation(value = "Cadastro de novo cupom")
    public ResponseEntity<Object> cadastroCupon(@RequestBody @Valid NovoCupomDto novoCupomDto) {
        if (cupomService.findByNomeCupom(novoCupomDto.getNomeCupom().toLowerCase())==null) {
            log.info("Cadastrando novo cupom");
            Cupom cupom = cupomService.cadastroDto(novoCupomDto);
            cupomService.save(cupom);
            log.info("Cupom cadastrado");
            return new ResponseEntity<>(cupom, HttpStatus.OK);
        }
        log.info("Cupom ja cadastrado");
        return new ResponseEntity<>("Cupom ja cadastrado", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/")
    @ApiOperation(value = "Lista todos os cupoms")
    public ResponseEntity<Object> listaCupons() {
        log.info("Listados todos os cupons \r\n {} cupons encontrados",
                cupomService.findAll().size());
        return new ResponseEntity<>(cupomService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{nome}")
    @ApiOperation(value = "Deleta cupom")
    public ResponseEntity<Object> deleteCupom(@PathVariable("nome") String nome) {
        log.info("Deletando cupom");
        if (cupomService.findByNomeCupom(nome) != null) {
            cupomService.delete(nome);
            log.info("Cupom deletado");
            return new ResponseEntity<>("Cupom deletado", HttpStatus.NO_CONTENT);
        }
        log.info("Cupom nao encontrado");
        return new ResponseEntity<>("Cupom nao encontrado", HttpStatus.NOT_FOUND);
    }
}
