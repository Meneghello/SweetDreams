package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Model.Produto;
import org.springframework.stereotype.Service;

@Service
public interface ClienteService {


    Cliente findByCpf(String cpf);
    Cliente save(Cliente cliente);
    Cliente update(Cliente cliente,String cpf);
    void delete(Cliente cliente);
}
