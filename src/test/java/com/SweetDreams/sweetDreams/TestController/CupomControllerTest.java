package com.SweetDreams.sweetDreams.TestController;

import com.SweetDreams.sweetDreams.Models.Cupom;
import com.SweetDreams.sweetDreams.Models.DTOs.ClienteAuthDto;
import com.SweetDreams.sweetDreams.Models.DTOs.NovoCupomDto;
import com.SweetDreams.sweetDreams.Services.CupomService;
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
public class CupomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CupomService cupomService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private String token;

    @BeforeEach
    public void tokenGenerate() {
        ClienteAuthDto clienteAuthDto = new ClienteAuthDto();
        clienteAuthDto.setCpf("111");
        clienteAuthDto.setSenha("111");
        token = userDetailsService.logar(clienteAuthDto);
        System.out.println(token);
    }

    @Test
    public void cadastroCupomTestSucess() throws Exception {
        NovoCupomDto novoCupomDto = new NovoCupomDto();
        novoCupomDto.setNomeCupom("teste");
        novoCupomDto.setDataExpiracao(5L);
        novoCupomDto.setDescricao("teste teste");
        novoCupomDto.setPorcentagem(5D);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/cupom/cadastro")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(objectMapper.writeValueAsString(novoCupomDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertNotNull(resultCase);
        System.out.println(resultCase);
        cupomService.delete("teste");
    }

    @Test
    public void cadastroCupomTestFail() throws Exception {
        NovoCupomDto novoCupomDto = new NovoCupomDto();
        novoCupomDto.setNomeCupom("teste");
        novoCupomDto.setDataExpiracao(5L);
        novoCupomDto.setDescricao("teste teste");
        novoCupomDto.setPorcentagem(5D);
        Cupom cupom = cupomService.cadastroDto(novoCupomDto);
        cupomService.save(cupom);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/cupom/cadastro")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(objectMapper.writeValueAsString(novoCupomDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertNotNull(resultCase);
        System.out.println(resultCase);
        cupomService.delete("teste");
    }

    @Test
    public void listarCuponsTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cupom/")
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
    }

    @Test
    public void deletarCupomSucessoTest() throws Exception {
        NovoCupomDto novoCupomDto = new NovoCupomDto();
        novoCupomDto.setNomeCupom("teste");
        novoCupomDto.setDataExpiracao(5L);
        novoCupomDto.setDescricao("teste teste");
        novoCupomDto.setPorcentagem(5D);
        Cupom cupom = cupomService.cadastroDto(novoCupomDto);
        cupomService.save(cupom);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/cupom/delete/teste")
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNoContent()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertNull(cupomService.findByNomeCupom("teste"));
    }
    @Test
    public void deletarCupomFalhaTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/cupom/delete/teste")
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

}
