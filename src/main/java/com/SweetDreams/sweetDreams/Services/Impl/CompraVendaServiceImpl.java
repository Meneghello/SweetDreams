package com.SweetDreams.sweetDreams.Services.Impl;

import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Model.CompraVenda;
import com.SweetDreams.sweetDreams.Model.DTOs.CompraVendaDto;
import com.SweetDreams.sweetDreams.Model.DTOs.HistoricoClienteDto;
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

    @Override
    public void delete(CompraVenda compraVenda) {
        compraVendaRepository.delete(compraVenda);
    }

    @Override
    public List<CompraVenda> findByCodigoVendedor(Long codigoVendedor) {
        return compraVendaRepository.findByCodigoVendedor(codigoVendedor);
    }

    @Override
    public  List<CompraVenda> findAll(){return compraVendaRepository.findAll();}

    @Override
    public Optional<CompraVenda> findById(String id){return compraVendaRepository.findById(id);}

    @Override
    public void deleteById(String id){ compraVendaRepository.deleteById(id);}

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
        compraVenda.setSabor(compraVendaDto.getSabor());
        compraVenda.setNomeProduto(compraVendaDto.getNomeProduto());
        compraVenda.setData((LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        compraVenda.setTotalPago(totalPago(compraVendaDto));
        return compraVenda;
    }


    @Override
    public boolean verificacao(CompraVendaDto venda) {
        Cliente cliente = clienteService.findByCpf(venda.getCpfCliente());
        Vendedor vendedor = vendedorService.findByCodigoVendedor(venda.getCodigoVendedor());
        Produto produto = produtoService.findByNomeProduto(venda.getNomeProduto().toLowerCase());
        if (cliente != null && vendedor != null && produto != null && produto.getSabor().stream().anyMatch((String s) -> produto.getNomeProduto().equalsIgnoreCase(venda.getNomeProduto()))) {
            return true;
        }
        return false;
    }

    @Override
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
        else if (produto.getSabor().stream().noneMatch(s -> produto.getNomeProduto().equalsIgnoreCase(venda.getNomeProduto()))){
            return new ResponseEntity<Object>("Sabor não encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>("Venda não realizada", HttpStatus.BAD_REQUEST);
    }

    public HistoricoClienteDto historicoDto(CompraVenda compra) {
        HistoricoClienteDto historico = new HistoricoClienteDto();
        historico.setData(compra.getData());
        historico.setNomeProduto(compra.getNomeProduto());
        historico.setQuantidade(compra.getQuantidade());
        historico.setSabor(compra.getSabor());
        historico.setTotalPago(compra.getTotalPago());
        return historico;
    }

    public String totalPago(CompraVendaDto compraVendaDto) {
        Long quantidade = compraVendaDto.getQuantidade();
        if (produtoService.findByNomeProduto(compraVendaDto.getNomeProduto())==null){
            return "";
        }
        Double preco = produtoService.findByNomeProduto(compraVendaDto.getNomeProduto().toLowerCase()).getPreco();
        return new DecimalFormat("##.00").format(quantidade * preco);

    }
}
