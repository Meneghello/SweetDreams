package com.SweetDreams.sweetDreams.TestController;


import com.SweetDreams.sweetDreams.Models.*;
import com.SweetDreams.sweetDreams.Models.DTOs.ClienteAuthDto;
import com.SweetDreams.sweetDreams.Models.DTOs.CompraVendaDto;
import com.SweetDreams.sweetDreams.Repository.ClienteRepository;
import com.SweetDreams.sweetDreams.Repository.ProdutoRepository;
import com.SweetDreams.sweetDreams.Repository.VendedorRepository;
import com.SweetDreams.sweetDreams.Services.CompraVendaService;
import com.SweetDreams.sweetDreams.Services.Impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompraVendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CompraVendaService compraVendaService;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

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
        cliente.setCpf("222");
        cliente.setSenha("abc");
        clienteRepository.save(cliente);
        return cliente;
    }

    private Vendedor vendedorTest() {

        Vendedor vendedor = new Vendedor();
        vendedor.setCodigoVendedor(50L);
        vendedor.setCliente(clienteTest());
        vendedor.setCpf(vendedor.getCliente().getCpf());
        vendedorRepository.save(vendedor);
        return vendedor;
    }

    private CompraVendaDto compraVendaDto() {
        CompraVendaDto compraVendaDto = new CompraVendaDto();
        compraVendaDto.setCodigoVendedor(50L);
        compraVendaDto.setCpfCliente("222");
        compraVendaDto.setQuantidade(10L);
        compraVendaDto.setSabor("Chocolate");
        compraVendaDto.setNomeProduto("teste");
        return compraVendaDto;
    }
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
        produtoRepository.save(produtoTest);
        return produtoTest;
    }


    @Test
    public void listarTodasVendasTest() throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/negocio/").header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        System.out.println(resultCase);
        Assertions.assertTrue(resultCase.length()>0);
    }

    @Test
    public void historicoClienteTest() throws Exception {
        Vendedor vendedor = vendedorTest();
        CompraVenda compraVenda = compraVendaService.save(compraVendaService.vendaDto(compraVendaDto()));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/negocio/cliente")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("cpf","222"))
                .andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        System.out.println(resultCase);
        Assertions.assertNotNull(resultCase);
        clienteRepository.delete(vendedor.getCliente());
        vendedorRepository.delete(vendedor);
        compraVendaService.delete(compraVenda);
    }
    @Test
    public void historicoClienteVazioTest() throws Exception {
        Vendedor vendedor = vendedorTest();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/negocio/cliente")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("cpf","222"))
                .andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        System.out.println(resultCase);
        Assertions.assertEquals("Nenhuma compra encontrada", resultCase);
        clienteRepository.delete(vendedor.getCliente());
        vendedorRepository.delete(vendedor);
    }
    @Test
    public void historicoClienteInexistenteTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/negocio/cliente")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("cpf","222"))
                .andExpect(status().is4xxClientError()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        System.out.println(resultCase);
        Assertions.assertEquals("Cliente não encontrado", resultCase);
    }
    @Test
    public void historicoVendedorTest() throws Exception {
        Vendedor vendedor = vendedorTest();
        CompraVenda compraVenda = compraVendaService.save(compraVendaService.vendaDto(compraVendaDto()));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/negocio/vendedor")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("codigoVendedor",String.valueOf(vendedor.getCodigoVendedor())))
                .andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        System.out.println(resultCase);
        Assertions.assertNotNull(resultCase);
        clienteRepository.delete(vendedor.getCliente());
        vendedorRepository.delete(vendedor);
        compraVendaService.delete(compraVenda);
    }
    @Test
    public void historicoVendedorVazioTest() throws Exception {
        Vendedor vendedor = vendedorTest();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/negocio/vendedor")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("codigoVendedor", String.valueOf(vendedor.getCodigoVendedor())))
                .andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        System.out.println(resultCase);
        Assertions.assertEquals("Nenhuma venda realizada", resultCase);
        clienteRepository.delete(vendedor.getCliente());
        vendedorRepository.delete(vendedor);
    }
    @Test
    public void historicoVendedorInexistenteTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/negocio/vendedor")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param("codigoVendedor", String.valueOf(111L)))
                .andExpect(status().is4xxClientError()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        System.out.println(resultCase);
        Assertions.assertEquals("Vendedor não encontrado", resultCase);
    }

    @Test
    public void vendaProdutoSucessTest() throws Exception {
        Vendedor vendedor = vendedorTest();
        Produto produto = produtoTest();
        CompraVendaDto compraVendaDto = new CompraVendaDto();
        compraVendaDto.setCodigoVendedor(50L);
        compraVendaDto.setCpfCliente("222");
        compraVendaDto.setQuantidade(2L);
        compraVendaDto.setSabor("Chocolate");
        compraVendaDto.setNomeProduto("produtoteste");
        compraVendaDto.setCupom("string");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/negocio/venda")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(objectMapper.writeValueAsString(compraVendaDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertNotNull(resultCase);
        System.out.println(resultCase);
        clienteRepository.delete(vendedor.getCliente());
        vendedorRepository.delete(vendedor);
        produtoRepository.delete(produto);
        compraVendaService.deleteById(compraVendaService.findByCodigoVendedor(50L).get(0).getId());
    }
    @Test
    public void vendaProdutoErrorTest() throws Exception{
        Vendedor vendedor = vendedorTest();
        CompraVendaDto compraVendaDto = new CompraVendaDto();
        compraVendaDto.setCodigoVendedor(50L);
        compraVendaDto.setCpfCliente("222");
        compraVendaDto.setQuantidade(20L);
        compraVendaDto.setSabor("Chocolate");
        compraVendaDto.setNomeProduto("teste");
        compraVendaDto.setCupom("string");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/negocio/venda")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(objectMapper.writeValueAsString(compraVendaDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertNotNull(resultCase);
        System.out.println(resultCase);
        clienteRepository.delete(vendedor.getCliente());
        vendedorRepository.delete(vendedor);
    }
}

