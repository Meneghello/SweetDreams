package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Model.Vendedor;
import org.springframework.stereotype.Service;

@Service
public interface VendedorService {
    Vendedor findByCpf(String cpf);
    Vendedor findByCodigoVendedor(Long codigoVendedor);
    Vendedor save(Vendedor vendedor);
    Vendedor update(Vendedor vendedor,String cpf);
    void delete(Vendedor vendedor);
    Long gerarCodigoVendedor();
}
