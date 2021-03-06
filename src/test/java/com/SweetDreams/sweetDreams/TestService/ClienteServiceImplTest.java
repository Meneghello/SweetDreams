package com.SweetDreams.sweetDreams.TestService;

import com.SweetDreams.sweetDreams.Models.Cliente;
import com.SweetDreams.sweetDreams.Models.DTOs.ClienteAuthDto;
import com.SweetDreams.sweetDreams.Models.DTOs.ClienteDto;
import com.SweetDreams.sweetDreams.Models.DTOs.NovoClienteDto;
import com.SweetDreams.sweetDreams.Models.Endereço;
import com.SweetDreams.sweetDreams.Repository.ClienteRepository;
import com.SweetDreams.sweetDreams.Services.ClienteService;
import com.SweetDreams.sweetDreams.Services.Impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClienteServiceImplTest {

    @Autowired
    ClienteService clienteService;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    UserDetailsServiceImpl userDetailsService;


    private Cliente clienteTest() {
        Cliente cliente = new Cliente();
        cliente.setDataNascimento("25/11/1998");
        cliente.setCelular("11911111111");
        cliente.setEmail("abc@abc.com");
        cliente.setEndereço(new Endereço("Teste", "45", "abc", "09110830", "Sao Paulo", "Sao paulo"));
        cliente.setNome("Cliente teste");
        cliente.setCpf("359.128.528-57");
        cliente.setSenha("abc");
        clienteRepository.save(cliente);
        return cliente;
    }

    @Test
    public void findbyCpfTest() {
        Cliente clienteTest = clienteTest();
        Assertions.assertNotNull(clienteService.findByCpf(clienteTest.getCpf()));
        Assertions.assertEquals("359.128.528-57", clienteService.findByCpf(clienteTest.getCpf()).getCpf());
        clienteRepository.delete(clienteTest);
    }

    @Test
    public void saveTest() {
        Cliente clienteTest = clienteTest();

        clienteService.save(clienteTest);
        Assertions.assertNotNull(clienteRepository.findByCpf(clienteTest.getCpf()));

        clienteRepository.delete(clienteTest);
    }

    @Test
    public void deleteTest() {
        Cliente clienteTest = clienteTest();
        clienteService.delete(clienteTest);
        Assertions.assertNull(clienteRepository.findByCpf(clienteTest.getCpf()));
    }

    @Test
    public void updateTest() {
        Cliente clienteTest = clienteTest();
        clienteTest.setNome("Gabriel");
        clienteService.update(clienteTest, clienteTest.getCpf());
        Assertions.assertEquals("Gabriel", clienteTest.getNome());
        clienteRepository.delete(clienteTest);
    }

    @Test
    public void cadastroDtoTest() {
        NovoClienteDto novoClienteDto = new NovoClienteDto();
        novoClienteDto.setDataNascimento("25/11/1998");
        novoClienteDto.setCelular("11911111111");
        novoClienteDto.setEmail("abc@abc.com");
        novoClienteDto.setEndereço(new Endereço("Teste", "45", "abc", "09110830", "Sao Paulo", "Sao paulo"));
        novoClienteDto.setNome("Cliente teste");
        novoClienteDto.setCpf("35912852857");
        novoClienteDto.setSenha("abc");
        Cliente cliente = clienteService.cadastroDto(novoClienteDto);
        Assertions.assertTrue(cliente.getNome().equalsIgnoreCase("Cliente teste"));
        Assertions.assertEquals("35912852857", cliente.getCpf());
    }

    @Test
    public void atualizcaoDtoTest() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setCelular("1111");
        clienteDto.setEmail("abc@abc.com");
        clienteDto.setEndereço(new Endereço("Teste", "45", "abc", "09110830", "Sao Paulo", "Sao paulo"));
        clienteDto.setNome("Cliente teste");
        Cliente cliente = clienteService.atualizacaoDto(clienteDto);
        Assertions.assertTrue(cliente.getNome().equalsIgnoreCase("Cliente teste"));
        Assertions.assertNull(cliente.getCpf());
    }
}
