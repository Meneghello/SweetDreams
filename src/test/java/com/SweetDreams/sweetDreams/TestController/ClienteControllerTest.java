package com.SweetDreams.sweetDreams.TestController;

import com.SweetDreams.sweetDreams.Models.Cliente;
import com.SweetDreams.sweetDreams.Models.DTOs.ClienteAuthDto;
import com.SweetDreams.sweetDreams.Models.DTOs.NovoClienteDto;
import com.SweetDreams.sweetDreams.Models.Endereço;
import com.SweetDreams.sweetDreams.Services.ClienteService;

import com.SweetDreams.sweetDreams.Services.Impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private String token;

    @BeforeEach
    public void tokenGenerate(){
        ClienteAuthDto clienteAuthDto = new ClienteAuthDto();
        clienteAuthDto.setCpf("111");
        clienteAuthDto.setSenha("111");
        token = userDetailsService.logar(clienteAuthDto);
        System.out.println(token);
    }

    private Cliente clienteTest() {
        Cliente cliente = new Cliente();
        cliente.setDataNascimento("25/11/1998");
        cliente.setCelular("11911111111");
        cliente.setEmail("abc@abc.com");
        cliente.setEndereço(new Endereço("Teste", "45", "abc", "09110830", "Sao Paulo", "Sao paulo"));
        cliente.setNome("Cliente teste");
        cliente.setCpf("35912852857");
        cliente.setSenha("abc");
        clienteService.save(cliente);
        return cliente;
    }

    @Test
    public void listaClientesTest() throws Exception {
        Cliente cliente = clienteTest();
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/cliente/").header("Authorization", token))
                        .andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertTrue(resultCase.length() > 0);
        clienteService.delete(cliente);
    }

    @Test
    public void buscarClienteCpfSucessoTest() throws Exception {
        Cliente clienteTest = clienteTest();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(
                        "/cliente/busca").header(HttpHeaders.AUTHORIZATION, token).param("cpf", "35912852857"))
                .andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertTrue(resultCase.contains("Cliente teste"));
        assertTrue(resultCase.contains("11911111111"));
        assertTrue(resultCase.contains("abc@abc.com"));
        clienteService.delete(clienteTest);
    }
    @Test
    public void buscarClienteCpfFalhaTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(
                        "/cliente/busca").header(HttpHeaders.AUTHORIZATION, token).param("cpf", "852857"))
                .andExpect(status().isNotFound()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        System.out.println(resultCase);
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    public void deletarClienteTest() throws Exception {
        Cliente clienteTest = clienteTest();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(
                        "/cliente/delete").header(HttpHeaders.AUTHORIZATION, token).param("cpf", "35912852857"))
                .andExpect(status().isAccepted()).andReturn();
        String resultCase = result.getResponse().getContentAsString();

        assertNull(clienteService.findByCpf(clienteTest.getCpf()));

        clienteService.delete(clienteTest);
    }
    @Test
    public void deletarClienteFalhaTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(
                        "/cliente/delete").header(HttpHeaders.AUTHORIZATION, token).param("cpf", "2857"))
                .andExpect(status().isNotFound()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        System.out.println(resultCase);
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    public void updateClienteTest() throws Exception {
        Cliente clienteTest = clienteTest();
        clienteTest.setEmail("cba@abc.com");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/cliente/atualizacao/35912852857")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(objectMapper.writeValueAsString(clienteTest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
        assertTrue(clienteService.findByCpf("35912852857").getEmail().equals("cba@abc.com"));
        clienteService.delete(clienteTest);
    }
    @Test
    public void updateClienteFalhaTest() throws Exception {
        Cliente clienteTest = clienteTest();
        clienteTest.setEmail("cba@abc.com");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/cliente/atualizacao/3592857")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(objectMapper.writeValueAsString(clienteTest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        System.out.println(resultCase);
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        clienteService.delete(clienteTest);

    }

    @Test
    public void cadastroClienteTestSucess() throws Exception {
        NovoClienteDto novoClienteDto = new NovoClienteDto();
        novoClienteDto.setDataNascimento("25/11/1998");
        novoClienteDto.setCelular("11911111111");
        novoClienteDto.setEmail("abc@abc.com");
        novoClienteDto.setEndereço(new Endereço("Teste", "45", "abc", "09110830", "Sao Paulo", "Sao paulo"));
        novoClienteDto.setNome("Cliente teste");
        novoClienteDto.setCpf("35912852857");
        novoClienteDto.setSenha("abc");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/cliente/cadastro")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(objectMapper.writeValueAsString(novoClienteDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertNotNull(resultCase);
        assertEquals("cliente teste",
                clienteService.findByCpf(novoClienteDto.getCpf()).getNome());
        System.out.println(resultCase);
        clienteService.delete(clienteService.findByCpf("35912852857"));
    }



    @Test
    public void cadastroClienteTestError() throws Exception {
        NovoClienteDto novoClienteDto = new NovoClienteDto();
        novoClienteDto.setDataNascimento("25/11/1998");
        novoClienteDto.setCelular("11911111111");
        novoClienteDto.setEmail("abcabc.com");
        novoClienteDto.setEndereço(new Endereço("Teste", "45", "abc", "09110830", "São Paulo", "São paulo"));
        novoClienteDto.setNome("Cl2iente teste");
        novoClienteDto.setCpf("35912852857");
        novoClienteDto.setSenha("abc");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/cliente/cadastro")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(objectMapper.writeValueAsString(novoClienteDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        System.out.println(resultCase);
        assertNotNull(resultCase);
    }
}
