package com.SweetDreams.sweetDreams.TestService;

import com.SweetDreams.sweetDreams.Models.Cliente;
import com.SweetDreams.sweetDreams.Models.DTOs.ClienteDto;
import com.SweetDreams.sweetDreams.Models.DTOs.NovoVendedorDto;
import com.SweetDreams.sweetDreams.Models.Endereço;
import com.SweetDreams.sweetDreams.Models.Vendedor;
import com.SweetDreams.sweetDreams.Repository.ClienteRepository;
import org.junit.jupiter.api.Assertions;

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

    private Cliente clienteTest() {
        Cliente cliente = new Cliente();
        cliente.setDataNascimento("25/11/1998");
        cliente.setCelular("11911111111");
        cliente.setEmail("abc@abc.com");
        cliente.setEndereço(new Endereço("Teste", "45", "abc", "09110830", "Sao Paulo", "Sao paulo"));
        cliente.setNome("Cliente teste");
        cliente.setCpf("35912852857");
        cliente.setSenha("abc");
        clienteRepository.save(cliente);
        return cliente;
    }

    private Vendedor vendedorTest() {

        Vendedor vendedor = new Vendedor();
        vendedor.setCodigoVendedor(2523l);
        vendedor.setCliente(clienteTest());
        vendedor.setCpf(vendedor.getCliente().getCpf());
        vendedorRepository.save(vendedor);
        return vendedor;
    }

    @Test
    public void findByCpfTest() {
        Vendedor vendedorTest = vendedorTest();
        Assertions.assertNotNull(vendedorService.findByCpf(vendedorTest.getCpf()));
        Assertions.assertEquals("35912852857", vendedorService.findByCpf(vendedorTest.getCpf()).getCpf());
        clienteRepository.delete(vendedorTest.getCliente());
        vendedorRepository.delete(vendedorTest);
    }

    @Test
    public void findByClienteIdTest() {
        Vendedor vendedorTest = vendedorTest();

        Assertions.assertNotNull(vendedorService.findByClienteId(vendedorTest.getCliente().getId()));
        Assertions.assertEquals("35912852857", vendedorService.findByClienteId(vendedorTest.getCliente().getId()).getCpf());

        clienteRepository.delete(vendedorTest.getCliente());
        vendedorRepository.delete(vendedorTest);
    }

    @Test
    public void findByCodigoVendedor() {
        Vendedor vendedorTest = vendedorTest();

        Assertions.assertNotNull(vendedorService.findByCodigoVendedor(vendedorTest.getCodigoVendedor()));

        clienteRepository.delete(vendedorTest.getCliente());
        vendedorRepository.delete(vendedorTest);
    }

    @Test
    public void saveTest() {
        Vendedor vendedorTest = vendedorTest();

        vendedorService.save(vendedorTest);
        Assertions.assertNotNull(vendedorRepository.findByCpf(vendedorTest.getCpf()));

        clienteRepository.delete(vendedorTest.getCliente());
        vendedorRepository.delete(vendedorTest);
    }

    @Test
    public void deleteTest() {
        Vendedor vendedorTest = vendedorTest();

        clienteRepository.delete(vendedorTest.getCliente());
        vendedorRepository.delete(vendedorTest);

        Assertions.assertNull(vendedorRepository.findByCpf(vendedorTest.getCpf()));
    }

//    @Test
//    public void updateTest() {
//        Vendedor vendedorTest = vendedorTest();
//        vendedorTest.getCliente().setNome("vendedor teste");
//        vendedorService.update(vendedorTest.getCliente(), vendedorTest.getCpf());
//        Assertions.assertNotNull(vendedorRepository.findByCpf(vendedorTest.getCpf()));
//        Assertions.assertNotNull(clienteRepository.findByCpf(vendedorTest.getCpf()));
//        Assertions.assertEquals("vendedor teste", vendedorRepository.findByCpf(vendedorTest.getCpf()).getCliente().getNome());
//
//        clienteRepository.delete(vendedorTest.getCliente());
//        vendedorRepository.delete(vendedorTest);
//    }

    @Test
    public void gerarCodigoVendedorTest() {

        Assertions.assertNotNull(vendedorService.gerarCodigoVendedor());
    }

    @Test
    public void cadastroDtoTest() {
        NovoVendedorDto novoVendedorDto = new NovoVendedorDto();
        Cliente cliente = clienteTest();
        Vendedor vendedor = vendedorService.cadastroDto(cliente.getCpf());

        Assertions.assertTrue(vendedor.getCliente().getNome().equalsIgnoreCase("Cliente teste"));
        Assertions.assertEquals("35912852857", vendedor.getCpf());
        clienteRepository.delete(clienteRepository.findByCpf("35912852857"));
    }

    @Test
    public void atualizcaoDtoTest() {
        ClienteDto vendedorDto = new ClienteDto();
        vendedorDto.setCelular("1111");
        vendedorDto.setEmail("abc@abc.com");
        vendedorDto.setEndereço(new Endereço("Teste", "45", "abc", "09110830", "Sao Paulo", "Sao paulo"));
        vendedorDto.setNome("Cliente teste");
        Cliente cliente = vendedorService.atualizacaoDto(vendedorDto);
        Assertions.assertTrue(cliente.getNome().equalsIgnoreCase("Cliente teste"));
        Assertions.assertNull(cliente.getCpf());
    }


}

