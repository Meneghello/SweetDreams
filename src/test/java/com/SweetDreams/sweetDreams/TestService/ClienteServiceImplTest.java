package com.SweetDreams.sweetDreams.TestService;

import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Model.DTOs.ClienteDto;
import com.SweetDreams.sweetDreams.Model.DTOs.NovoClienteDto;
import com.SweetDreams.sweetDreams.Model.Endereço;
import com.SweetDreams.sweetDreams.Repository.ClienteRepository;
import com.SweetDreams.sweetDreams.Services.ClienteService;
import org.junit.jupiter.api.Assertions;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClienteServiceImplTest {

    @Autowired
    ClienteService clienteService;

    @Autowired
    ClienteRepository clienteRepository;

    private Cliente clienteTest(){
        Cliente cliente = new Cliente();
        cliente.setDataNascimento("25/11/1998");
        cliente.setCelular("11911111111");
        cliente.setEmail("abc@abc.com");
        cliente.setEndereço(new Endereço("Teste","45","abc","09110830","Sao Paulo", "Sao paulo"));
        cliente.setNome("Cliente teste");
        cliente.setCpf("359.128.528-57");
        clienteRepository.save(cliente);
        return cliente;
    }

    @Test
    public void findbyCpfTest(){
        Cliente clienteTest = clienteTest();
        assertNotNull(clienteService.findByCpf(clienteTest.getCpf()));
        assertEquals("359.128.528-57",clienteService.findByCpf(clienteTest.getCpf()).getCpf());
        clienteRepository.delete(clienteTest);
    }

    @Test
    public void saveTest(){
        Cliente clienteTest = clienteTest();

        clienteService.save(clienteTest);
        assertNotNull(clienteRepository.findByCpf(clienteTest.getCpf()));

        clienteRepository.delete(clienteTest);
    }

    @Test
    public void deleteTest(){
        Cliente clienteTest = clienteTest();
        clienteService.delete(clienteTest);
        assertNull(clienteRepository.findByCpf(clienteTest.getCpf()));
    }

    @Test
    public void updateTest(){
        Cliente clienteTest = clienteTest();
        clienteTest.setNome("Gabriel");
        clienteService.update(clienteTest,clienteTest.getCpf());
        assertEquals("Gabriel",clienteTest.getNome());
        clienteRepository.delete(clienteTest);
    }

    @Test
    public void cadastroDtoTest(){
        NovoClienteDto novoClienteDto = new NovoClienteDto();
        novoClienteDto.setDataNascimento("25/11/1998");
        novoClienteDto.setCelular("11911111111");
        novoClienteDto.setEmail("abc@abc.com");
        novoClienteDto.setEndereço(new Endereço("Teste","45","abc","09110830","Sao Paulo", "Sao paulo"));
        novoClienteDto.setNome("Cliente teste");
        novoClienteDto.setCpf("35912852857");
        Cliente cliente = clienteService.cadastroDto(novoClienteDto);
        assertTrue(cliente.getNome().equalsIgnoreCase("Cliente teste"));
        assertEquals("35912852857", cliente.getCpf());
    }

    @Test
    public void atualizcaoDtoTest(){
        ClienteDto clienteDto =  new ClienteDto();
        clienteDto.setCelular("1111");
        clienteDto.setEmail("abc@abc.com");
        clienteDto.setEndereço(new Endereço("Teste","45","abc","09110830","Sao Paulo", "Sao paulo"));
        clienteDto.setNome("Cliente teste");
        Cliente cliente = clienteService.atualizacaoDto(clienteDto);
        assertTrue(cliente.getNome().equalsIgnoreCase("Cliente teste"));
        assertNull(cliente.getCpf());
    }
}
