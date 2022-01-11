package com.SweetDreams.sweetDreams.Services;


import com.SweetDreams.sweetDreams.Models.CompraVenda;
import com.SweetDreams.sweetDreams.Models.DTOs.CompraVendaDto;
import com.SweetDreams.sweetDreams.Models.DTOs.HistoricoClienteDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CompraVendaService {

    void delete(CompraVenda compraVenda);

    List<CompraVenda> findByCodigoVendedor(Long codigoVendedor);

    List<HistoricoClienteDto> findByCpfCliente(String cpfCliente);

    CompraVenda save(CompraVenda compraVenda);

    CompraVenda vendaDto(CompraVendaDto compraVendaDto);

    boolean verificacao(CompraVendaDto venda);

    ResponseEntity<Object> verificacaoHandle(CompraVendaDto venda);

    List<CompraVenda> findAll();

    Optional<CompraVenda> findById(String id);

    void deleteById(String id);
}
