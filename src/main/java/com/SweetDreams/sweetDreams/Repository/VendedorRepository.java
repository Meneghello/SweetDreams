package com.SweetDreams.sweetDreams.Repository;

import com.SweetDreams.sweetDreams.Models.Vendedor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendedorRepository extends MongoRepository<Vendedor, String> {
    Vendedor findByCpf(String cpf);

    Vendedor findByCodigoVendedor(Long codigoVendedor);

    Vendedor findByClienteId(String id);

}
