package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Model.Produto;
import org.springframework.stereotype.Service;

@Service
public interface ProdutoService {

    Produto save(Produto produto);
    Produto findByNomeProduto(String nomeProduto);

    Produto update(Produto produto, String nomeProduto);
}
