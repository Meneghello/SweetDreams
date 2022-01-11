package com.SweetDreams.sweetDreams.Repository;

import com.SweetDreams.sweetDreams.Models.Produto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {
    Produto findByNomeProduto(String nomeProduto);


}
