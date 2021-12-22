package com.SweetDreams.sweetDreams.View;

import com.SweetDreams.sweetDreams.Model.Produto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping(value = "/produtoview")
public class ProdutosView {
    @GetMapping(value = "/lista")
    public String listaProdutos(Model model){
        return "Cadastro/cadastroProdutos";
    }
}
