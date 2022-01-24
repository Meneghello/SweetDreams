package com.SweetDreams.sweetDreams.Services.Impl;

import com.SweetDreams.sweetDreams.Controller.ClienteController;
import com.SweetDreams.sweetDreams.Handle.AuthorizationExceptionHandle;
import com.SweetDreams.sweetDreams.Models.Cliente;
import com.SweetDreams.sweetDreams.Models.DTOs.ClienteAuthDto;
import com.SweetDreams.sweetDreams.Repository.ClienteRepository;
import com.SweetDreams.sweetDreams.Security.JWTUtil;
import com.SweetDreams.sweetDreams.Security.UserSS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    JWTUtil jwtUtil;

    private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByCpf(cpf);
        if (cliente==null){
            throw new UsernameNotFoundException(cpf);
        }
        return new UserSS(cliente.getId(),cliente.getSenha(),cliente.getCpf(),cliente.getRole());
    }

    public String logar (ClienteAuthDto clienteAuthDto){
        BCryptPasswordEncoder encoder  = new BCryptPasswordEncoder();
        Cliente cliente = clienteRepository.findByCpf(clienteAuthDto.getCpf());
        if (cliente != null){
            if (encoder.matches(clienteAuthDto.getSenha(), cliente.getSenha())){
                String token = "Bearer " + jwtUtil.generateToken(clienteAuthDto.getCpf());
                log.info("Token gerado");
                return token;
            }
            else {
                log.info("Senha incorreta");
                throw new AuthorizationExceptionHandle("Senha incorreta");
            }
        }
        log.info("Usuario não encontrado");
        throw new AuthorizationExceptionHandle("Usuario não encontrado");
    }
}
