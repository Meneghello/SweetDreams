package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Model.DTOs.ClienteDto;
import com.SweetDreams.sweetDreams.Model.DTOs.NovoClienteDto;
import com.SweetDreams.sweetDreams.Model.Produto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClienteService {

    List<Cliente> findAll();
    Cliente findByCpf(String cpf);
    Cliente save(Cliente cliente);
    Cliente update(Cliente cliente,String cpf);
    void delete(Cliente cliente);

    Cliente cadastroDto(NovoClienteDto novoClienteDto);

    Cliente atualizacaoDto(ClienteDto clienteDto);
}
