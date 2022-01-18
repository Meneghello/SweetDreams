package com.SweetDreams.sweetDreams.TestController;

import com.SweetDreams.sweetDreams.Models.Cliente;
import com.SweetDreams.sweetDreams.Models.DTOs.ClienteDto;
import com.SweetDreams.sweetDreams.Models.DTOs.NovoVendedorDto;
import com.SweetDreams.sweetDreams.Models.Endereço;
import com.SweetDreams.sweetDreams.Models.Vendedor;
import com.SweetDreams.sweetDreams.Services.ClienteService;
import com.SweetDreams.sweetDreams.Services.VendedorService;
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

@SpringBootTest(properties = {"security.basic.enabled=false"})
@AutoConfigureMockMvc
public class VendedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VendedorService vendedorService;

    @Autowired
    private ClienteService clienteService;

    private Cliente clienteTest() {
        Cliente cliente = new Cliente();
        cliente.setDataNascimento("25/11/1998");
        cliente.setCelular("11911111111");
        cliente.setEmail("abc@abc.com");
        cliente.setEndereço(new Endereço("Teste", "45", "abc", "09110830", "Sao Paulo", "Sao paulo"));
        cliente.setNome("Cliente teste");
        cliente.setCpf("35912852857");
        clienteService.save(cliente);
        return cliente;
    }

    private Vendedor vendedorTest() {

        Vendedor vendedor = new Vendedor();
        vendedor.setCodigoVendedor(2523l);
        vendedor.setCliente(clienteTest());
        vendedor.setCpf(vendedor.getCliente().getCpf());
        vendedorService.save(vendedor);
        return vendedor;
    }

    private ClienteDto clienteDto() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNome("Gabriel");
        clienteDto.setEndereço(new Endereço("Teste", "45", "abc", "09110830", "Sao Paulo", "Sao paulo"));
        clienteDto.setCelular("1234");
        clienteDto.setEmail("abdsac@abc.com");
        return clienteDto;
    }

    private NovoVendedorDto novoVendedorDto() {
        NovoVendedorDto vendedorDto = new NovoVendedorDto();
        vendedorDto.setCliente(new Cliente(
                null,
                "Gabriel",
                (new Endereço("Teste", "45", "abc", "09110830", "Sao Paulo", "Sao paulo")),
                "25/11/1998",
                "1111",
                "35912852857",
                "abc@abcv.com",
                "abc"
        ));
        return vendedorDto;
    }


    @Test
    public void listaVendedoresTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/vendedor/"))
                .andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertTrue(resultCase.length() > 0);
    }

    @Test
    public void updateVendedorTest() throws Exception {
        Vendedor vendedor = vendedorTest();
        ClienteDto clienteDto = clienteDto();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/vendedor/atualizacao/35912852857")
                        .content(objectMapper.writeValueAsString(clienteDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertTrue(vendedorService.findByCpf("35912852857").getCliente().getNome().equalsIgnoreCase("Gabriel"));
        System.out.println(result.getResponse().getContentAsString());
        clienteService.delete(vendedor.getCliente());
        vendedorService.delete(vendedor);

    }

    @Test
    public void buscaVendedorCpfTest() throws Exception {
        Vendedor vendedor = vendedorTest();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/vendedor/busca")
                        .param("cpf", "35912852857"))
                .andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();

        System.out.println(result.getResponse().getContentAsString());

        assertTrue(resultCase.contains("Cliente teste"));
        assertTrue(resultCase.contains("11911111111"));
        assertTrue(resultCase.contains("abc@abc.com"));

        clienteService.delete(vendedor.getCliente());
        vendedorService.delete(vendedor);
    }

    @Test
    public void buscaVendedorCodigoTest() throws Exception {
        Vendedor vendedor = vendedorTest();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/vendedor/busca/2523"))
                .andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();

        System.out.println(result.getResponse().getContentAsString());

        assertTrue(resultCase.contains("Cliente teste"));
        assertTrue(resultCase.contains("11911111111"));
        assertTrue(resultCase.contains("abc@abc.com"));

        clienteService.delete(vendedor.getCliente());
        vendedorService.delete(vendedor);
    }

    @Test
    public void deleteVendedorCpfTest() throws Exception {
        Vendedor vendedor = vendedorTest();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/vendedor/delete")
                        .param("cpf", "35912852857"))
                .andExpect(status().isAccepted())
                .andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertNull(vendedorService.findByCpf(vendedor.getCpf()));
        clienteService.delete(vendedor.getCliente());
    }

    @Test
    public void deleteVendedorCodigoTest() throws Exception {
        Vendedor vendedor = vendedorTest();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/vendedor/delete/2523"))
                .andExpect(status().isAccepted())
                .andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertNull(vendedorService.findByCpf(vendedor.getCpf()));
        clienteService.delete(vendedor.getCliente());
    }

    @Test
    public void cadastroVendedorTestSucess() throws Exception {
        NovoVendedorDto novoVendedorDto = novoVendedorDto();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/vendedor/cadastro")
                        .content(objectMapper.writeValueAsString(novoVendedorDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String resulCase = result.getResponse().getContentAsString();
        assertNotNull(resulCase);
        assertEquals("Gabriel", vendedorService.findByCpf("35912852857").getCliente().getNome());
        System.out.println(resulCase);
        Long codigo = vendedorService.findByCpf("35912852857").getCodigoVendedor();
        clienteService.delete(vendedorService.findByCpf("35912852857").getCliente());
        vendedorService.delete(vendedorService.findByCodigoVendedor(codigo));
    }

    @Test
    public void cadastroVendedorTestError() throws Exception {
        NovoVendedorDto novoVendedorDto = novoVendedorDto();
        novoVendedorDto.getCliente().setNome("gabr1el");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/vendedor/cadastro")
                        .content(objectMapper.writeValueAsString(novoVendedorDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();
        String resulCase = result.getResponse().getContentAsString();
        assertNotNull(resulCase);
        System.out.println(resulCase);
    }

}
