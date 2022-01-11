package com.SweetDreams.sweetDreams.Services.Impl;

import com.SweetDreams.sweetDreams.Models.DTOs.ProdutoDto;
import com.SweetDreams.sweetDreams.Models.Produto;
import com.SweetDreams.sweetDreams.Repository.ProdutoRepository;
import com.SweetDreams.sweetDreams.Services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Override
    public Produto update(Produto novoProduto, String nomeProduto) {
        Produto produto = produtoRepository.findByNomeProduto(nomeProduto);
        produto.setNomeProduto(novoProduto.getNomeProduto().toLowerCase());
        produto.setDataValidade(novoProduto.getDataValidade());
        produto.setPreco(novoProduto.getPreco());
        produto.setQuantidade(novoProduto.getQuantidade());
        produto.setSabor(novoProduto.getSabor().stream().map(String::toLowerCase).collect(Collectors.toList()));
        return produtoRepository.save(produto);
    }

    @Override
    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    @Override
    public void delete(Produto produto) {
        produtoRepository.delete(produto);
    }

    @Override
    public Produto findByNomeProduto(String nomeProduto) {
        return produtoRepository.findByNomeProduto(nomeProduto);
    }

    @Override
    public Produto cadastroDto(ProdutoDto produtoDto) {
        Produto produto = new Produto();
        produto.setQuantidade(produtoDto.getQuantidade());
        produto.setNomeProduto(produtoDto.getNomeProduto().toLowerCase());
        produto.setSabor(produtoDto.getSabor().stream().map(String::toLowerCase).collect(Collectors.toList()));
        produto.setPreco(produtoDto.getPreco());
        produto.setDataValidade(produtoDto.getDataValidade());
        return produto;
    }


}
