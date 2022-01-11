package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Models.DTOs.ProdutoDto;
import com.SweetDreams.sweetDreams.Models.Produto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProdutoService {

    Produto save(Produto produto);

    Produto findByNomeProduto(String nomeProduto);

    List<Produto> findAll();

    Produto update(Produto produto, String nomeProduto);

    void delete(Produto produto);

    Produto cadastroDto(ProdutoDto produtoDto);
}
