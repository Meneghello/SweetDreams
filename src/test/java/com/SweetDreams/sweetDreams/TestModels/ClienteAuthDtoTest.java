package com.SweetDreams.sweetDreams.TestModels;

import com.SweetDreams.sweetDreams.Models.DTOs.ClienteAuthDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClienteAuthDtoTest {

    @Test
    public void getCpfTest() {
        ClienteAuthDto clienteAuthDto = new ClienteAuthDto();
        Assertions.assertNull(clienteAuthDto.getCpf());
    }

    @Test
    public void getSenhaTest() {
        ClienteAuthDto clienteAuthDto = new ClienteAuthDto();
        Assertions.assertNull(clienteAuthDto.getSenha());
    }

    @Test
    public void setCpfTest() {
        ClienteAuthDto clienteAuthDto = new ClienteAuthDto();
        clienteAuthDto.setCpf("123");
        Assertions.assertEquals("123", clienteAuthDto.getCpf());
    }

    @Test
    public void setSenhaTest() {
        ClienteAuthDto clienteAuthDto = new ClienteAuthDto();
        clienteAuthDto.setSenha("123");
        Assertions.assertEquals("123", clienteAuthDto.getSenha());
    }

    @Test
    public void constructorClienteAuthDto() {
        ClienteAuthDto clienteAuthDto = new ClienteAuthDto(
                "123",
                "123"
        );
        Assertions.assertNotNull(clienteAuthDto);
    }
}
