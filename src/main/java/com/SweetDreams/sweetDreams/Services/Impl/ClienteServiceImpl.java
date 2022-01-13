package com.SweetDreams.sweetDreams.Services.Impl;


import com.SweetDreams.sweetDreams.SweetDreamsApplication;
import com.SweetDreams.sweetDreams.Models.Cliente;
import com.SweetDreams.sweetDreams.Models.DTOs.ClienteDto;
import com.SweetDreams.sweetDreams.Models.DTOs.NovoClienteDto;
import com.SweetDreams.sweetDreams.Repository.ClienteRepository;
import com.SweetDreams.sweetDreams.Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public Cliente findByCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public void delete(Cliente cliente) {
        clienteRepository.delete(cliente);
    }

    @Override
    public Cliente update(Cliente clienteDto, String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf);
        cliente.setNome(clienteDto.getNome().toLowerCase());
        cliente.setCelular(clienteDto.getCelular());
        cliente.setEmail(clienteDto.getEmail());
        cliente.setEndereço(clienteDto.getEndereço());
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente cadastroDto(NovoClienteDto novoClienteDto) {
        Cliente cliente = new Cliente();
        cliente.setEndereço(novoClienteDto.getEndereço());
        cliente.setEmail(novoClienteDto.getEmail());
        cliente.setCelular(novoClienteDto.getCelular());
        cliente.setNome(novoClienteDto.getNome().toLowerCase());
        cliente.setCpf(novoClienteDto.getCpf());
        cliente.setSenha(bCryptPasswordEncoder.encode(novoClienteDto.getSenha()));
        cliente.setDataNascimento(novoClienteDto.getDataNascimento());
        return cliente;

    }


    @Override
    public Cliente atualizacaoDto(ClienteDto clienteDto) {
        Cliente cliente = new Cliente();
        cliente.setEmail(clienteDto.getEmail());
        cliente.setNome(clienteDto.getNome());
        cliente.setCelular(clienteDto.getCelular());
        cliente.setEndereço(clienteDto.getEndereço());
        return cliente;
    }
}
