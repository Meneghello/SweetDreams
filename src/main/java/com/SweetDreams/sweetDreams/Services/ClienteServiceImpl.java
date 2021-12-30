package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Model.DTOs.ClienteDto;
import com.SweetDreams.sweetDreams.Model.DTOs.NovoClienteDto;
import com.SweetDreams.sweetDreams.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public Cliente findByCpf(String cpf){return clienteRepository.findByCpf(cpf);}

    @Override
    public Cliente save(Cliente cliente){return clienteRepository.save(cliente);}

    @Override
    public void delete(Cliente cliente){clienteRepository.delete(cliente);}

    @Override
    public Cliente update(Cliente clienteDto, String cpf){
        Cliente cliente = clienteRepository.findByCpf(cpf);
        cliente.setNome(clienteDto.getNome().toLowerCase());
        cliente.setCelular(clienteDto.getCelular());
        cliente.setEmail(clienteDto.getEmail());
        cliente.setEndereço(clienteDto.getEndereço());
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> findAll(){return clienteRepository.findAll();}

    @Override
    public Cliente cadastroDto(NovoClienteDto novoClienteDto){
        Cliente cliente = new Cliente();
        cliente.setEndereço(novoClienteDto.getEndereço());
        cliente.setEmail(novoClienteDto.getEmail());
        cliente.setCelular(novoClienteDto.getCelular());
        cliente.setNome(novoClienteDto.getNome().toLowerCase());
        cliente.setCpf(novoClienteDto.getCpf());
        cliente.setDataNascimento(novoClienteDto.getDataNascimento());
        return cliente;

    }

    @Override
    public Cliente atualizacaoDto(ClienteDto clienteDto){
        Cliente cliente = new Cliente();
        cliente.setEmail(clienteDto.getEmail());
        cliente.setNome(clienteDto.getNome());
        cliente.setCelular(clienteDto.getCelular());
        cliente.setEndereço(clienteDto.getEndereço());
        return cliente;
    }





}
