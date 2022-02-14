package com.SweetDreams.sweetDreams.Controller;


import com.SweetDreams.sweetDreams.Models.DTOs.ClienteAuthDto;
import com.SweetDreams.sweetDreams.Security.JWTUtil;
import com.SweetDreams.sweetDreams.Security.UserSS;
import com.SweetDreams.sweetDreams.Services.Impl.UserDetailsServiceImpl;
import com.SweetDreams.sweetDreams.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value="/auth")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    UserDetailsServiceImpl userDetailsService;


    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping(value = "/refreshToken")
    public ResponseEntity<Object> refreshToken(HttpServletResponse response){
        log.info("Reiniciando tempo do token");
        UserSS userSS = UserService.authenticated();
        String token = jwtUtil.generateToken(userSS.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        log.info("Novo token criado");
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login (@RequestBody @Valid ClienteAuthDto clienteAuthDto, HttpServletResponse res){
        log.info("Tentando logar");
        String token = userDetailsService.logar(clienteAuthDto);
        res.addHeader("Authorization", token);
        log.info("Logado com sucesso");
        return new ResponseEntity<>("Logado com sucesso",HttpStatus.OK);
    }


}
