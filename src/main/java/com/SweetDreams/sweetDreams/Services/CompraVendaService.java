package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Model.CompraVenda;
import com.SweetDreams.sweetDreams.Model.DTOs.CompraVendaDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompraVendaService {

    List<CompraVenda> findByCodigoVendedor(Long codigoVendedor);

    List<CompraVenda> findByCpfCliente(String cpfCliente);

    CompraVenda save(CompraVenda compraVenda);

    CompraVenda vendaDto(CompraVendaDto compraVendaDto);

    boolean verificacao(CompraVendaDto venda);

    ResponseEntity<Object> verificacaoHandle(CompraVendaDto venda);
}
