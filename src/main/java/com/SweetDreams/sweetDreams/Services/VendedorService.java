package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Model.Produto;
import com.SweetDreams.sweetDreams.Model.Vendedor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VendedorService {
    Vendedor findByCpf(String cpf);
    Vendedor findByCliente(Cliente cliente);
    Vendedor findByCodigoVendedor(Long codigoVendedor);
    Vendedor save(Vendedor vendedor);
    Vendedor update(Vendedor vendedor,String cpf);
    void delete(Vendedor vendedor);
    List<Vendedor> findAll();
    Long gerarCodigoVendedor();
}
