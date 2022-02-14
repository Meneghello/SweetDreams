package com.SweetDreams.sweetDreams.TestController;


import com.SweetDreams.sweetDreams.Models.DTOs.ClienteAuthDto;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    public void refreshTokenTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(
                        "/auth/refreshToken")
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isAccepted()).andReturn();
        String resultCase = result.getResponse().getHeader("Authorization");
        System.out.println(resultCase);
        Assertions.assertNotNull(resultCase);
    }

    @Test
    public void loginTestSucesso() throws Exception {
        ClienteAuthDto clienteAuthDto = new ClienteAuthDto();
        clienteAuthDto.setSenha("111");
        clienteAuthDto.setCpf("111");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(
                                "/auth/login")
                        .content(objectMapper.writeValueAsString(clienteAuthDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        System.out.println(resultCase);
        Assertions.assertNotNull(resultCase);
    }
    @Test
    public void loginTestErrorSenha() throws Exception {
        ClienteAuthDto clienteAuthDto = new ClienteAuthDto();
        clienteAuthDto.setSenha("123");
        clienteAuthDto.setCpf("111");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(
                                "/auth/login")
                        .content(objectMapper.writeValueAsString(clienteAuthDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        System.out.println(resultCase);
        Assertions.assertTrue(resultCase.contains("Senha incorreta"));
    }
    @Test
    public void loginTestErrorUsuarioNotFound() throws Exception {
        ClienteAuthDto clienteAuthDto = new ClienteAuthDto();
        clienteAuthDto.setSenha("111");
        clienteAuthDto.setCpf("1231");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(
                                "/auth/login")
                        .content(objectMapper.writeValueAsString(clienteAuthDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andReturn();
        String resultCase = result.getResponse().getContentAsString();
        System.out.println(resultCase);
        Assertions.assertTrue(resultCase.contains("Usuario nao encontrado"));
    }
}
