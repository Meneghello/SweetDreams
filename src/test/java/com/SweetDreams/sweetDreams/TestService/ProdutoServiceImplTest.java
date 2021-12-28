package com.SweetDreams.sweetDreams.TestService;

import com.SweetDreams.sweetDreams.Model.Produto;
import com.SweetDreams.sweetDreams.Repository.ProdutoRepository;
import com.SweetDreams.sweetDreams.Services.ProdutoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
public class ProdutoServiceImplTest {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    private Produto produtoTest(){
        Produto produtoTest = new Produto();
        produtoTest.setNomeProduto("produto teste");
        produtoTest.setPreco(5d);
        produtoTest.setDataValidade("25/12/2021");
        produtoTest.setQuantidade(50l);
        produtoTest.setSabor(new String[]{"chocolate", "Doce de leite"});
        produtoRepository.save(produtoTest);
        return produtoTest;
    }

    @Test
    public void buscarPorNomeProdutoTest(){

        Produto produtoEnc = produtoService.findByNomeProduto(produtoTest().getNomeProduto());
        Assertions.assertNotNull(produtoEnc);
        Assertions.assertEquals("produto teste", produtoEnc.getNomeProduto());
        produtoRepository.delete(produtoEnc);

    }

    @Test
    public void deleteProdutoTest(){
        Produto produtoTest = produtoTest();
        produtoService.delete(produtoTest);
        Produto nomeProduto = produtoRepository.findByNomeProduto(produtoTest.getNomeProduto());
        assertNull(nomeProduto);
    }

    @Test
    public void findAllTest(){

        List<Produto> produtos = produtoService.findAll();
        Produto produtoTest = produtoTest();
        List<Produto> produtos2 = produtoService.findAll();

        assertTrue(produtos2.size()==produtos.size()+1);
        produtoRepository.delete(produtoTest);
    }

    @Test
    public void updateTest(){
        Produto produtoTest = produtoTest();
        produtoTest.setQuantidade(10l);
        produtoService.update(produtoTest, produtoTest.getNomeProduto());

        assertEquals("10", String.valueOf(produtoTest.getQuantidade()));
        produtoRepository.delete(produtoTest);
    }

    @Test
    public void saveTest(){
        Produto produtoTest = produtoTest();
        produtoService.save(produtoTest);
        assertNotNull(produtoService.findByNomeProduto(produtoTest.getNomeProduto()));
        produtoRepository.delete(produtoTest);

    }

}
