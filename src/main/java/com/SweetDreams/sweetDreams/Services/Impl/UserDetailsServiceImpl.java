package com.SweetDreams.sweetDreams.Services.Impl;

import com.SweetDreams.sweetDreams.Models.Cliente;
import com.SweetDreams.sweetDreams.Repository.ClienteRepository;
import com.SweetDreams.sweetDreams.Security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    ClienteRepository clienteRepository;


    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByCpf(cpf);
        if (cliente==null){
            throw new UsernameNotFoundException(cpf);
        }
        return new UserSS(cliente.getId(),cliente.getSenha(),cliente.getCpf(),cliente.getRole());
    }
}
