package com.SweetDreams.sweetDreams.Services.Impl;


import com.SweetDreams.sweetDreams.Models.*;
import com.SweetDreams.sweetDreams.Models.DTOs.CompraVendaDto;
import com.SweetDreams.sweetDreams.Models.DTOs.HistoricoClienteDto;
import com.SweetDreams.sweetDreams.Repository.CompraVendaRepository;
import com.SweetDreams.sweetDreams.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    CupomService cupomService;

    @Override
    public void delete(CompraVenda compraVenda) {
        compraVendaRepository.delete(compraVenda);
    }

    @Override
    public List<CompraVenda> findByCodigoVendedor(Long codigoVendedor) {
        return compraVendaRepository.findByCodigoVendedor(codigoVendedor);
    }

    @Override
    public List<CompraVenda> findAll() {
        return compraVendaRepository.findAll();
    }

    @Override
    public Optional<CompraVenda> findById(String id) {
        return compraVendaRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        compraVendaRepository.deleteById(id);
    }

    @Override
    public List<HistoricoClienteDto> findByCpfCliente(String cpfCliente) {
        List<CompraVenda> historicoCliente = compraVendaRepository.findByCpfCliente(cpfCliente);
        List<HistoricoClienteDto> historicoClienteDtos = new ArrayList<HistoricoClienteDto>(historicoCliente.size());
        for (CompraVenda compra : historicoCliente) {
            historicoClienteDtos.add(historicoDto(compra));
        }
        return historicoClienteDtos;
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
        compraVenda.setSabor(compraVendaDto.getSabor().toLowerCase());
        compraVenda.setNomeProduto(compraVendaDto.getNomeProduto().toLowerCase());
        compraVenda.setData((LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        compraVenda.setTotalPago(totalPago(compraVendaDto));
        compraVenda.setCupom(compraVendaDto.getCupom());
        return compraVenda;
    }


    @Override
    public boolean verificacao(CompraVendaDto venda) {
        Cliente cliente = clienteService.findByCpf(venda.getCpfCliente());
        Vendedor vendedor = vendedorService.findByCodigoVendedor(venda.getCodigoVendedor());
        Produto produto = produtoService.findByNomeProduto(venda.getNomeProduto().toLowerCase());
        Cupom cupom = cupomService.findByNomeCupom(venda.getCupom().toLowerCase());

        if (cupom != null) {
            if (cliente != null && vendedor != null && produto != null && venda.getQuantidade() <= produto.getQuantidade()
                    && cupom.getDataExpiracao().isAfter(LocalDateTime.now())
                    && produto.getSabor().stream().anyMatch((String s) -> produto.getNomeProduto().equalsIgnoreCase(venda.getNomeProduto()))) {
                posCompra(venda);
                return true;
            }
        } else if (cupom == null) {
            if (cliente != null && vendedor != null && produto != null && venda.getQuantidade() <= produto.getQuantidade()
                    && produto.getSabor().stream().anyMatch((String s) -> produto.getNomeProduto().equalsIgnoreCase(venda.getNomeProduto()))) {
                posCompra(venda);
                return true;
            }
            return false;

        }
        return false;
    }

    @Override
    public ResponseEntity<Object> verificacaoHandle(CompraVendaDto venda) {
        Cliente cliente = clienteService.findByCpf(venda.getCpfCliente());
        Vendedor vendedor = vendedorService.findByCodigoVendedor(venda.getCodigoVendedor());
        Produto produto = produtoService.findByNomeProduto(venda.getNomeProduto().toLowerCase());
        Cupom cupom = cupomService.findByNomeCupom(venda.getCupom().toLowerCase());

        if (cliente == null) {
            return new ResponseEntity<Object>("Cpf não encontrado", HttpStatus.NOT_FOUND);
        } else if (vendedor == null) {
            return new ResponseEntity<Object>("Vendedor não encontrado", HttpStatus.NOT_FOUND);
        } else if (produto == null) {
            return new ResponseEntity<Object>("Produto não encontrado", HttpStatus.NOT_FOUND);
        } else if (produto.getSabor().stream().noneMatch(s -> produto.getNomeProduto().equalsIgnoreCase(venda.getNomeProduto()))) {
            return new ResponseEntity<Object>("Sabor não encontrado", HttpStatus.NOT_FOUND);
        } else if (venda.getQuantidade() > produto.getQuantidade()) {
            return new ResponseEntity<Object>("Quantidade insuficiente do produto", HttpStatus.BAD_REQUEST);
        } else if (cupom.getDataExpiracao().isBefore(LocalDateTime.now())) {
            return new ResponseEntity<Object>("Cupom invalido/expirado", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>("Venda não realizada", HttpStatus.BAD_REQUEST);
    }

    public HistoricoClienteDto historicoDto(CompraVenda compra) {
        HistoricoClienteDto historico = new HistoricoClienteDto();
        historico.setData(compra.getData());
        historico.setNomeProduto(compra.getNomeProduto().toLowerCase());
        historico.setQuantidade(compra.getQuantidade());
        historico.setSabor(compra.getSabor().toLowerCase());
        historico.setTotalPago(compra.getTotalPago());
        return historico;
    }

    public void posCompra(CompraVendaDto venda) {
        Produto produto = produtoService.findByNomeProduto(venda.getNomeProduto().toLowerCase());
        Long quantidadeFinal = produto.getQuantidade() - venda.getQuantidade();
        produto.setQuantidade(quantidadeFinal);
        produtoService.save(produto);
    }

    public String totalPago(CompraVendaDto compraVendaDto) {
        Long quantidade = compraVendaDto.getQuantidade();
        if (produtoService.findByNomeProduto(compraVendaDto.getNomeProduto().toLowerCase()) == null) {
            return "";
        }
        Double preco = produtoService.findByNomeProduto(compraVendaDto.getNomeProduto().toLowerCase()).getPreco();
        if (cupomService.findByNomeCupom(compraVendaDto.getCupom())!=null) {
            Double desconto = ((cupomService.findByNomeCupom(compraVendaDto.getCupom()).getPorcentagem() / 100) - 1) * (-1);
            return new DecimalFormat("##.00").format((quantidade * preco) * desconto);
        }
        else {
            return new DecimalFormat("##.00").format((quantidade * preco));
        }

    }
}
