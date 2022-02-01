package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Models.Cliente;
import com.SweetDreams.sweetDreams.Models.DTOs.ClienteDto;
import com.SweetDreams.sweetDreams.Models.DTOs.NovoVendedorDto;
import com.SweetDreams.sweetDreams.Models.Vendedor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VendedorService {
    Vendedor findByCpf(String cpf);

    Vendedor findByCodigoVendedor(Long codigoVendedor);

    Vendedor findByClienteId(String id);

    Vendedor save(Vendedor vendedor);

    Vendedor update(Cliente cliente, String cpf);

    void delete(Vendedor vendedor);

    List<Vendedor> findAll();

    Vendedor cadastroDto(String cpf);

    Cliente atualizacaoDto(ClienteDto vendedorDto);

    Long gerarCodigoVendedor();
}
