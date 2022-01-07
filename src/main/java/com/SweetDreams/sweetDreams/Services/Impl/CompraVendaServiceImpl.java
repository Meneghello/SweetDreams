package com.SweetDreams.sweetDreams.Services.Impl;

import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Model.CompraVenda;
import com.SweetDreams.sweetDreams.Model.DTOs.CompraVendaDto;
import com.SweetDreams.sweetDreams.Model.Produto;
import com.SweetDreams.sweetDreams.Model.Vendedor;
import com.SweetDreams.sweetDreams.Repository.CompraVendaRepository;
import com.SweetDreams.sweetDreams.Services.ClienteService;
import com.SweetDreams.sweetDreams.Services.CompraVendaService;
import com.SweetDreams.sweetDreams.Services.ProdutoService;
import com.SweetDreams.sweetDreams.Services.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

@Service
public class CompraVendaServiceImpl implements CompraVendaService {

    @Autowired
    CompraVendaRepository compraVendaRepository;

    @Autowired
    ClienteService clienteService;

    @Autowired
    VendedorService vendedorService;

    @Autowired
    ProdutoService produtoService;

    @Override
    public List<CompraVenda> findByCodigoVendedor(Long codigoVendedor) {
        return compraVendaRepository.findByCodigoVendedor(codigoVendedor);
    }

    @Override
    public List<CompraVenda> findByCpfCliente(String cpfCliente) {
        return compraVendaRepository.findByCpfCliente(cpfCliente);
    }

    @Override
    public CompraVenda save(CompraVenda compraVenda) {
        return compraVendaRepository.save(compraVenda);
    }

    @Override
    public CompraVenda vendaDto(CompraVendaDto compraVendaDto) {
        CompraVenda compraVenda = new CompraVenda();
        compraVenda.setCodigoVendedor(compraVendaDto.getCodigoVendedor());
        compraVenda.setCpfCliente(compraVendaDto.getCpfCliente());
        compraVenda.setQuantidade(compraVendaDto.getQuantidade());
        compraVenda.setSabor(compraVendaDto.getSabor());
        compraVenda.setNomeProduto(compraVendaDto.getNomeProduto());
        compraVenda.setData((LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        compraVenda.setTotalPago(totalPago(compraVendaDto));
        return compraVenda;
    }

    public String totalPago(CompraVendaDto compraVendaDto) {
        Long quantidade = compraVendaDto.getQuantidade();
        Double preco = produtoService.findByNomeProduto(compraVendaDto.getNomeProduto()).getPreco();
        return new DecimalFormat("##.00").format(quantidade * preco);

    }

    public boolean verificacao(CompraVendaDto venda) {
        Cliente cliente = clienteService.findByCpf(venda.getCpfCliente());
        Vendedor vendedor = vendedorService.findByCodigoVendedor(venda.getCodigoVendedor());
        Produto produto = produtoService.findByNomeProduto(venda.getNomeProduto().toLowerCase());
        if (cliente != null && vendedor != null && produto != null) {
            return true;
        }
        return false;
    }

    public ResponseEntity<Object> verificacaoHandle(CompraVendaDto venda) {
        Cliente cliente = clienteService.findByCpf(venda.getCpfCliente());
        Vendedor vendedor = vendedorService.findByCodigoVendedor(venda.getCodigoVendedor());
        Produto produto = produtoService.findByNomeProduto(venda.getNomeProduto().toLowerCase());
        if (cliente == null) {
            return new ResponseEntity<Object>("Cpf não encontrado", HttpStatus.NOT_FOUND);
        } else if (vendedor == null) {
            return new ResponseEntity<Object>("Vendedor não encontrado", HttpStatus.NOT_FOUND);
        } else if (produto == null) {
            return new ResponseEntity<Object>("Produto não encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>("Venda não realizada", HttpStatus.BAD_REQUEST);
    }


}
