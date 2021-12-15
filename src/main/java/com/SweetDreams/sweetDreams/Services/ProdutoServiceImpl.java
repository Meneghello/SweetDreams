package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Model.Produto;
import com.SweetDreams.sweetDreams.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProdutoServiceImpl implements ProdutoService{

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public Produto save(Produto produto){return produtoRepository.save(produto);}

    @Override
    public Produto update(Produto novoProduto, String nomeProduto){
        Produto produto = produtoRepository.findByNomeProduto(nomeProduto.toLowerCase());
        produto.setNomeProduto(novoProduto.getNomeProduto().toLowerCase());
        produto.setDataValidade(novoProduto.getDataValidade());
        produto.setPreço(novoProduto.getPreço());
        produto.setQuantidade(novoProduto.getQuantidade());
        produto.setSabor(novoProduto.getSabor());
        return  produtoRepository.save(produto);
    }

    @Override
    public Produto findByNomeProduto(String nomeProduto){return produtoRepository.findByNomeProduto(nomeProduto);}


}
