package com.SweetDreams.sweetDreams.Controller;


import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Model.CompraVenda;
import com.SweetDreams.sweetDreams.Model.DTOs.CompraVendaDto;
import com.SweetDreams.sweetDreams.Services.ClienteService;
import com.SweetDreams.sweetDreams.Services.CompraVendaService;
import com.SweetDreams.sweetDreams.Services.VendedorService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/negocio")
@Api(value = "Transações")
public class CompraVendaController {

    @Autowired
    private CompraVendaService compraVendaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VendedorService vendedorService;

    private static Logger log = LoggerFactory.getLogger(VendedorController.class);

    @GetMapping(value = "/cliente")
    @ApiOperation(value = "Historico de compras de um Cliente")
    public ResponseEntity<Object> historicoCompra(@RequestParam("cpf") String cpf) {
        if (clienteService.findByCpf(cpf) == null) {
            log.info("Cliente não encontrado, CPF inválido");
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        } else {
            List<CompraVenda> historico = compraVendaService.findByCpfCliente(cpf);
            if (historico.isEmpty()) {
                log.info("Nenhuma compra realizada no cpf {}", cpf);
                return new ResponseEntity<>("Nenhuma compra encontrada", HttpStatus.NOT_FOUND);
            }
            log.info("{} compras encontrada", historico.size());
            return new ResponseEntity<>(historico, HttpStatus.OK);
        }

    }

    @GetMapping(value = "/vendedor")
    @ApiOperation(value = "Historico de venda de um Vendedor")
    public ResponseEntity<Object> historicoVenda(@RequestParam("codigoVendedor") Long codigoVendedor) {
        if (vendedorService.findByCodigoVendedor(codigoVendedor) == null) {
            log.info("Vendedor não encontrado, Código do vendedor inválido");
            return new ResponseEntity<>("Vendedor não encontrado", HttpStatus.NOT_FOUND);
        } else {
            List<CompraVenda> historico = compraVendaService.findByCodigoVendedor(codigoVendedor);
            if (historico.isEmpty()) {
                log.info("Nenhuma venda realizada para o vendedor {}", codigoVendedor);
                return new ResponseEntity<>("Nenhuma venda realizada", HttpStatus.NOT_FOUND);
            }
            log.info("{} vendas realizadas", historico.size());
            return new ResponseEntity<>(historico, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/venda")
    @ApiOperation(value = "Venda de um produto")
    public ResponseEntity<Object> vendaProduto(@Valid @RequestBody CompraVendaDto vendaDto) {
        if (compraVendaService.verificacao(vendaDto)) {
            CompraVenda venda = compraVendaService.vendaDto(vendaDto);
            compraVendaService.save(venda);
            log.info("Venda realizada com sucesso, cliente: {}, vendedor {}",
                    clienteService.findByCpf(venda.getCpfCliente()).getNome(),
                    venda.getCodigoVendedor());
            return new ResponseEntity<>(venda, HttpStatus.OK);
        }
        log.info("Venda não realizada");
        return compraVendaService.verificacaoHandle(vendaDto);
    }

}
