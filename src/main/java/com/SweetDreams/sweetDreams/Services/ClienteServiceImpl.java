package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Cliente update(Cliente novoCliente, String cpf){
        Cliente cliente = clienteRepository.findByCpf(cpf);
        cliente.setNome(novoCliente.getNome().toLowerCase());
        cliente.setCelular(novoCliente.getCelular());
        cliente.setEmail(novoCliente.getEmail());
        cliente.setEndereço(novoCliente.getEndereço());
        cliente.setDataNascimento(novoCliente.getDataNascimento());
        return clienteRepository.save(cliente);
    }



}
