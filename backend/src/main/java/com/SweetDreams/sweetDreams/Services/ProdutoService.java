package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Model.Produto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProdutoService {

    Produto save(Produto produto);
    Produto findByNomeProduto(String nomeProduto);
    List<Produto> findAll();

    Produto update(Produto produto, String nomeProduto);
    void delete(Produto produto);
}
