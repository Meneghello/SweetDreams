package com.SweetDreams.sweetDreams.TestHandle;

import com.SweetDreams.sweetDreams.Models.Cliente;
import com.SweetDreams.sweetDreams.Models.DTOs.NovoClienteDto;
import com.SweetDreams.sweetDreams.Models.Endereço;
import com.SweetDreams.sweetDreams.Models.Operadores;
import com.SweetDreams.sweetDreams.Models.Produto;
import com.SweetDreams.sweetDreams.Services.ClienteService;
import com.SweetDreams.sweetDreams.Services.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"security.basic.enabled=false"})
@AutoConfigureMockMvc
public class RestExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @Test
    public void RestExceptionFieldErrorHandlerTest() throws Exception {
        NovoClienteDto novoClienteDto = new NovoClienteDto();
        novoClienteDto.setDataNascimento("25/11/1998");
        novoClienteDto.setCelular("11911111111");
        novoClienteDto.setEmail("abcabc.com");
        novoClienteDto.setEndereço(new Endereço("Teste", "45", "abc", "09110830", "São Paulo", "São paulo"));
        novoClienteDto.setNome("Cl2iente teste");
        novoClienteDto.setCpf("35912852857");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/cliente/cadastro")
                        .content(objectMapper.writeValueAsString(novoClienteDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        System.out.println(resultCase);
        assertEquals(HttpStatus.BAD_REQUEST.value(),result.getResponse().getStatus());
    }
    @Test
    public void RestExceptionObjectErrorHandlerTest() throws Exception {
        NovoClienteDto novoClienteDto = new NovoClienteDto();
        novoClienteDto.setDataNascimento("25/11/1998");
        novoClienteDto.setCelular("11911111111");
        novoClienteDto.setEmail(null);
        novoClienteDto.setEndereço(new Endereço("Teste", "45", "abc", "09110830", "São Paulo", "São paulo"));
        novoClienteDto.setNome("Cl2iente teste");
        novoClienteDto.setCpf("35912852857");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/cliente/cadastro")
                        .content(objectMapper.writeValueAsString(novoClienteDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        System.out.println(resultCase);
        assertEquals(HttpStatus.BAD_REQUEST.value(),result.getResponse().getStatus());
    }
    @Test
    public void handleConstraintViolationExceptionTest() throws Exception{
        ArrayList<String> sabor = new ArrayList<>();
        sabor.add("chocolate");
        sabor.add("doce de leite");
        Produto produtoTest = new Produto();
        produtoTest.setNomeProduto("produtoteste");
        produtoTest.setPreco(5d);
        produtoTest.setDataValidade("25/12/2021");
        produtoTest.setQuantidade(50L);
        produtoTest.setSabor(sabor);
        produtoService.save(produtoTest);
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("quantidade", String.valueOf(-50));
        params.add("operador", String.valueOf(Operadores.adicao));

        MvcResult result =mockMvc.perform(MockMvcRequestBuilders.put("/produto/reposicao/produtoteste")
                        .params(params)
                        .content(objectMapper.writeValueAsString(produtoTest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andReturn();
        String resultcase = result.getResponse().getContentAsString();
        System.out.println(resultcase);
        assertEquals(HttpStatus.BAD_REQUEST.value(),result.getResponse().getStatus());
        produtoService.delete(produtoTest);
    }

    @Test
    public void handleMissingServletRequestParameterTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(
                        "/cliente/delete"))
                .andExpect(status().is4xxClientError()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        System.out.println(resultCase);
        assertEquals(HttpStatus.BAD_REQUEST.value(),result.getResponse().getStatus());

    }
}
