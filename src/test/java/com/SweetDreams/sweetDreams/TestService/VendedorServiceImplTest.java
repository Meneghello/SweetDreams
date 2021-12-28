package com.SweetDreams.sweetDreams.TestService;

import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Model.Endereço;
import com.SweetDreams.sweetDreams.Model.Vendedor;
import com.SweetDreams.sweetDreams.Repository.ClienteRepository;
import org.junit.jupiter.api.Assertions;
import static org.junit.Assert.*;
import com.SweetDreams.sweetDreams.Repository.VendedorRepository;
import com.SweetDreams.sweetDreams.Services.VendedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VendedorServiceImplTest {

    @Autowired
    VendedorService vendedorService;

    @Autowired
    VendedorRepository vendedorRepository;

    @Autowired
    ClienteRepository clienteRepository;

    private Cliente clienteTest(){
        Cliente cliente = new Cliente();
        cliente.setDataNascimento("25/11/1998");
        cliente.setCelular("11911111111");
        cliente.setEmail("abc@abc.com");
        cliente.setEndereço(new Endereço("Teste","45","abc","09110830","São Paulo", "São paulo"));
        cliente.setNome("Cliente teste");
        cliente.setCpf("359.128.528-57");
        clienteRepository.save(cliente);
        return cliente;
    }

    private Vendedor vendedorTest(){

        Vendedor vendedor = new Vendedor();
        vendedor.setCodigoVendedor(2523l);
        vendedor.setCliente(clienteTest());
        vendedor.setCpf("359.128.528-57");
        vendedorRepository.save(vendedor);
        return vendedor;
    }

    @Test
    public void findByCpfTest(){
        Vendedor vendedorTest = vendedorTest();
        assertNotNull(vendedorService.findByCpf(vendedorTest.getCpf()));
        assertEquals("359.128.528-57", vendedorService.findByCpf(vendedorTest.getCpf()).getCpf());
        clienteRepository.delete(vendedorTest.getCliente());
        vendedorRepository.delete(vendedorTest);
    }

    @Test
    public void findByClienteTest(){
        Vendedor vendedorTest = vendedorTest();

        assertNotNull(vendedorService.findByCliente(vendedorTest.getCliente()));

        clienteRepository.delete(vendedorTest.getCliente());
        vendedorRepository.delete(vendedorTest);
    }

    @Test
    public void findByCodigoVendedor(){
        Vendedor vendedorTest = vendedorTest();

        assertNotNull(vendedorService.findByCodigoVendedor(vendedorTest.getCodigoVendedor()));

        clienteRepository.delete(vendedorTest.getCliente());
        vendedorRepository.delete(vendedorTest);
    }

    @Test
    public void saveTest(){
        Vendedor vendedorTest = vendedorTest();

        vendedorService.save(vendedorTest);
        assertNotNull(vendedorRepository.findByCpf(vendedorTest.getCpf()));

        clienteRepository.delete(vendedorTest.getCliente());
        vendedorRepository.delete(vendedorTest);
    }

    @Test
    public void deleteTest(){
        Vendedor vendedorTest = vendedorTest();

        clienteRepository.delete(vendedorTest.getCliente());
        vendedorRepository.delete(vendedorTest);

        assertNull(vendedorRepository.findByCpf(vendedorTest.getCpf()));
    }

    @Test
    public void updateTest(){
        Vendedor vendedorTest = vendedorTest();

        vendedorTest.setCodigoVendedor(800l);
        vendedorTest.getCliente().setNome("Gabriel");

        vendedorService.update(vendedorTest,vendedorTest.getCpf());

        assertEquals("800", String.valueOf(vendedorTest.getCodigoVendedor()));
        assertEquals("Gabriel", vendedorTest.getCliente().getNome());

        clienteRepository.delete(vendedorTest.getCliente());
        vendedorRepository.delete(vendedorTest);
    }

    @Test
    public void gerarCodigoVendedorTest(){

        assertNotNull(vendedorService.gerarCodigoVendedor());
    }
}
