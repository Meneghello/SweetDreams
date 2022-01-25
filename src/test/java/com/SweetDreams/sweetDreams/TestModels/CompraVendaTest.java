package com.SweetDreams.sweetDreams.TestModels;

import com.SweetDreams.sweetDreams.Models.CompraVenda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CompraVendaTest {

    @Test
    public void setIdTest(){
        CompraVenda compraVenda = new CompraVenda();
        compraVenda.setId("123abc");
        Assertions.assertNotNull(compraVenda.getId());
    }


}
