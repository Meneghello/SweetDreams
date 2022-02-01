package com.SweetDreams.sweetDreams.TestService;

import com.SweetDreams.sweetDreams.Models.CompraVenda;
import com.SweetDreams.sweetDreams.Models.DTOs.CompraVendaDto;
import com.SweetDreams.sweetDreams.Repository.ClienteRepository;
import com.SweetDreams.sweetDreams.Repository.CompraVendaRepository;
import com.SweetDreams.sweetDreams.Repository.VendedorRepository;
import com.SweetDreams.sweetDreams.Services.CompraVendaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@SpringBootTest
public class CompraVendaServiceImplTest {

    @Autowired
    private CompraVendaService compraVendaService;

    @Autowired
    private CompraVendaRepository compraVendaRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private ClienteRepository clienteRepository;


    private CompraVendaDto compraVendaDto() {
        CompraVendaDto compraVendaDto = new CompraVendaDto();
        compraVendaDto.setCodigoVendedor(50L);
        compraVendaDto.setCpfCliente("111");
        compraVendaDto.setQuantidade(20L);
        compraVendaDto.setSabor("Chocolate");
        compraVendaDto.setNomeProduto("teste");
        compraVendaDto.setCupom("string");
        return compraVendaDto;
    }


    private CompraVenda compraVenda() {
        CompraVenda compraVenda = new CompraVenda();
        compraVenda.setNomeProduto("teste");
        compraVenda.setCodigoVendedor(50L);
        compraVenda.setSabor("chocolate");
        compraVenda.setCpfCliente("111");
        compraVenda.setQuantidade(20L);
        compraVenda.setTotalPago("11");
        compraVenda.setData("11/11/1111");
        return compraVenda;
    }

    @Test
    public void buscaVendedorTest() {
        CompraVenda compraVenda = compraVenda();
        compraVendaRepository.save(compraVenda);
        Assertions.assertNotNull(compraVendaService.findByCodigoVendedor(compraVenda.getCodigoVendedor()));
        compraVendaRepository.delete(compraVenda);
    }

    @Test
    public void buscaCpfClienteTeste() {
        CompraVenda compraVenda = compraVenda();
        compraVendaRepository.save(compraVenda);
        Assertions.assertNotNull(compraVendaService.findByCpfCliente(compraVenda.getCpfCliente()));
        compraVendaRepository.delete(compraVenda);
    }

    @Test
    public void buscaIdTest(){
        CompraVenda compraVenda = compraVenda();
        compraVendaRepository.save(compraVenda);
        Assertions.assertNotNull(compraVendaService.findById(compraVenda.getId()));
        System.out.println(compraVendaService.findById(compraVenda.getId()));
        compraVendaRepository.delete(compraVenda);
    }

    @Test
    public void deleteIdTest(){
        CompraVenda compraVenda = compraVenda();
        compraVendaRepository.save(compraVenda);
        compraVendaService.deleteById(compraVenda.getId());
        Assertions.assertEquals(Optional.empty(),compraVendaRepository.findById(compraVenda.getId()));
    }

    @Test
    public void saveTest(){
        CompraVenda compraVenda = compraVenda();
        compraVendaService.save(compraVenda);
        Assertions.assertNotNull(compraVendaService.findByCpfCliente(compraVenda.getCpfCliente()));
        compraVendaRepository.delete(compraVenda);
    }

    @Test
    public void vendaDtoTest(){
        CompraVendaDto compraVendaDto = compraVendaDto();
        CompraVenda compraVenda = compraVendaService.vendaDto(compraVendaDto);
        Assertions.assertEquals("111", compraVenda.getCpfCliente());
        Assertions.assertEquals(50L,compraVenda.getCodigoVendedor());
    }

    @Test
    public void verificacaoTest(){
        CompraVendaDto compraVendaDto = compraVendaDto();
        boolean verificacao = compraVendaService.verificacao(compraVendaDto);
        Assertions.assertFalse(verificacao);
    }

    @Test
    public void verificacaoHandleTest(){
        CompraVendaDto compraVendaDto = compraVendaDto();
        ResponseEntity<Object> resposta = compraVendaService.verificacaoHandle(compraVendaDto);
        Assertions.assertEquals(404, resposta.getStatusCodeValue());
    }



}
