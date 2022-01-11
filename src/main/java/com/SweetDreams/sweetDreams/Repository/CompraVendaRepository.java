package com.SweetDreams.sweetDreams.Repository;

import com.SweetDreams.sweetDreams.Models.CompraVenda;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraVendaRepository extends MongoRepository<CompraVenda, String> {
    List<CompraVenda> findByCodigoVendedor(long codigoVendedor);

    List<CompraVenda> findByCpfCliente(String cpfCliente);
}
