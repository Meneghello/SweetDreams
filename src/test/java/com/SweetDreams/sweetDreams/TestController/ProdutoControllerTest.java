package com.SweetDreams.sweetDreams.TestController;

import com.SweetDreams.sweetDreams.Model.Produto;
import com.SweetDreams.sweetDreams.Services.ProdutoService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProdutoService produtoService;

    private ArrayList<String> sabor() {
        ArrayList<String> sabor = new ArrayList<>();
        sabor.add("chocolate");
        sabor.add("doce de leite");
        return sabor;
    }

    private Produto produtoTest() {
        Produto produtoTest = new Produto();
        produtoTest.setNomeProduto("produtoteste");
        produtoTest.setPreco(5d);
        produtoTest.setDataValidade("25/12/2021");
        produtoTest.setQuantidade(50l);
        produtoTest.setSabor(sabor());
        produtoService.save(produtoTest);
        return produtoTest;
    }

    @Test
    public void listaProdutosTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(
                "/produto/")).andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertTrue(resultCase.length() > 0);
    }

    @Test
    public void buscarProdutosNomeTest() throws Exception {
        Produto produtoTest = produtoTest();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(
                "/produto/busca/produtoteste")).andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        System.out.println(resultCase);
        assertTrue(resultCase.contains("produtoteste"));
        assertTrue(resultCase.contains("chocolate"));
        produtoService.delete(produtoTest);
    }

    @Test
    public void deletarProdutoTest() throws Exception {
        Produto produtoTest = produtoTest();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(
                "/produto/delete/produtoteste")).andExpect(status().isAccepted()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertNull(produtoService.findByNomeProduto(produtoTest.getNomeProduto()));
        produtoService.delete(produtoTest);
    }

    @Test
    public void updateProdutoTest() throws Exception {
        Produto produtoTest = produtoTest();
        produtoTest.setQuantidade(10l);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(
                                "/produto/atualizacao/produtoteste")
                        .content(objectMapper.writeValueAsString(produtoTest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals("10",
                String.valueOf(produtoService.findByNomeProduto(produtoTest.getNomeProduto()).getQuantidade()));

        produtoService.delete(produtoTest);
    }

    @Test
    public void cadastroProdutoTestSucess() throws Exception {
        Produto produtoTest = new Produto();
        produtoTest.setNomeProduto("produtoteste");
        produtoTest.setPreco(5d);
        produtoTest.setDataValidade("25/12/2021");
        produtoTest.setQuantidade(50l);
        produtoTest.setSabor(sabor());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(
                                "/produto/cadastro")
                        .content(objectMapper.writeValueAsString(produtoTest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertNotNull(resultCase);
        assertEquals("produtoteste",
                produtoService.findByNomeProduto(produtoTest.getNomeProduto()).getNomeProduto());
        produtoService.delete(produtoService.findByNomeProduto(produtoTest.getNomeProduto()));
    }

    @Test
    public void cadastroProdutoTestError() throws Exception {
        Produto produtoTest = new Produto();
        produtoTest.setNomeProduto("produtoteste");
        produtoTest.setPreco(5d);
        produtoTest.setDataValidade(null);
        produtoTest.setQuantidade(50l);
        produtoTest.setSabor(sabor());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(
                                "/produto/cadastro")
                        .content(objectMapper.writeValueAsString(produtoTest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertNotNull(resultCase);
    }
}
