package com.SweetDreams.sweetDreams.TestController;

import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Model.Endereço;
import com.SweetDreams.sweetDreams.Services.ClienteService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

    private Cliente clienteTest(){
        Cliente cliente = new Cliente();
        cliente.setDataNascimento("25/11/1998");
        cliente.setCelular("11911111111");
        cliente.setEmail("abc@abc.com");
        cliente.setEndereço(new Endereço("Teste","45","abc","09110830","São Paulo", "São paulo"));
        cliente.setNome("Cliente teste");
        cliente.setCpf("35912852857");
        clienteService.save(cliente);
        return cliente;
    }

    @Test
    public void listaClientesTest() throws Exception{
        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders.get("/cliente/"))
                        .andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertTrue(resultCase.length()>0);
    }

    @Test
    public void buscarClienteCpfTest() throws Exception{
        Cliente clienteTest = clienteTest();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(
                "/cliente/busca/35912852857")).andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertTrue(resultCase.contains("Cliente teste"));
        assertTrue(resultCase.contains("11911111111"));
        assertTrue(resultCase.contains("abc@abc.com"));
        clienteService.delete(clienteTest);
    }

    @Test
    public void deletarClienteTest() throws Exception{
        Cliente clienteTest = clienteTest();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(
                "/cliente/delete/35912852857")).andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();

        assertNull(clienteService.findByCpf(clienteTest.getCpf()));

        clienteService.delete(clienteTest);
    }

    @Test
    public void updateClienteTest() throws Exception {
        Cliente clienteTest = clienteTest();
        clienteTest.setEmail("cba@abc.com");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/cliente/atualizacao/35912852857")
                        .content(objectMapper.writeValueAsString(clienteTest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertTrue(clienteService.findByCpf("35912852857").getEmail().equals("cba@abc.com"));
        clienteService.delete(clienteTest);

    }

    @Test
    public void cadastroClienteTestSucess() throws Exception{
        Cliente cliente = new Cliente();
        cliente.setDataNascimento("25/11/1998");
        cliente.setCelular("11911111111");
        cliente.setEmail("abc@abc.com");
        cliente.setEndereço(new Endereço("Teste","45","abc","09110830","São Paulo", "São paulo"));
        cliente.setNome("Cliente teste");
        cliente.setCpf("35912852857");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/cliente/cadastro")
                        .content(objectMapper.writeValueAsString(cliente))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertNotNull(resultCase);
        assertEquals("cliente teste",
                clienteService.findByCpf(cliente.getCpf()).getNome());
        clienteService.delete(clienteService.findByCpf("35912852857"));
    }

    @Test
    public void cadastroClienteTestError() throws Exception{
        Cliente cliente = new Cliente();
        cliente.setDataNascimento("25/11/1998");
        cliente.setCelular("11911111111");
        cliente.setEmail("abcabc.com");
        cliente.setEndereço(new Endereço("Teste","45","abc","09110830","São Paulo", "São paulo"));
        cliente.setNome("Cliente teste");
        cliente.setCpf("35912852857");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/cliente/cadastro")
                        .content(objectMapper.writeValueAsString(cliente))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertNotNull(resultCase);
    }
}
