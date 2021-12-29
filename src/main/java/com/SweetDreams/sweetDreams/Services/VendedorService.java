package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Model.DTOs.ClienteDto;
import com.SweetDreams.sweetDreams.Model.DTOs.NovoVendedorDto;
import com.SweetDreams.sweetDreams.Model.Produto;
import com.SweetDreams.sweetDreams.Model.Vendedor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VendedorService {
    Vendedor findByCpf(String cpf);
    Vendedor findByCodigoVendedor(Long codigoVendedor);
    Vendedor findByClienteId(String id);

    Vendedor save(Vendedor vendedor);
    Vendedor update(Cliente cliente,String cpf);
    void delete(Vendedor vendedor);

    List<Vendedor> findAll();


    Vendedor cadastroDto(NovoVendedorDto vendedorDto);
    Cliente atualizacaoDto(ClienteDto vendedorDto);

    Long gerarCodigoVendedor();
}
