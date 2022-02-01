package com.SweetDreams.sweetDreams.TestService;

import com.SweetDreams.sweetDreams.Models.Cupom;
import com.SweetDreams.sweetDreams.Models.DTOs.NovoCupomDto;
import com.SweetDreams.sweetDreams.Models.Produto;
import com.SweetDreams.sweetDreams.Repository.CupomRepository;
import com.SweetDreams.sweetDreams.Services.CupomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CupomServiceImplTest {

    @Autowired
    CupomService cupomService;

    @Autowired
    CupomRepository cupomRepository;

    @Test
    public void cadastroDtoTest() {
        NovoCupomDto novoCupomDto = new NovoCupomDto();
        novoCupomDto.setNomeCupom("teste");
        novoCupomDto.setDataExpiracao(5L);
        novoCupomDto.setDescricao("teste teste");
        novoCupomDto.setPorcentagem(5D);
        Cupom cupom = cupomService.cadastroDto(novoCupomDto);
        Assertions.assertEquals("teste", cupom.getNomeCupom());
    }

    @Test
    public void saveTest() {
        NovoCupomDto novoCupomDto = new NovoCupomDto();
        novoCupomDto.setNomeCupom("teste");
        novoCupomDto.setDataExpiracao(5L);
        novoCupomDto.setDescricao("teste teste");
        novoCupomDto.setPorcentagem(5D);
        Cupom cupom = cupomService.cadastroDto(novoCupomDto);
        cupomService.save(cupom);
        Assertions.assertNotNull(cupomRepository.findByNomeCupom("teste"));
        cupomRepository.delete(cupom);
    }

    @Test
    public void findAllTest() {
        List<Cupom> cupoms = cupomService.findAll();
        Assertions.assertTrue(cupoms.size()>=0);
    }

    @Test
    public void deleteTest() {
        NovoCupomDto novoCupomDto = new NovoCupomDto();
        novoCupomDto.setNomeCupom("teste");
        novoCupomDto.setDataExpiracao(5L);
        novoCupomDto.setDescricao("teste teste");
        novoCupomDto.setPorcentagem(5D);
        Cupom cupom = cupomService.cadastroDto(novoCupomDto);
        cupomRepository.save(cupom);
        Assertions.assertNotNull(cupomRepository.findByNomeCupom("teste"));
        cupomService.delete("teste");
    }

    @Test
    public void findByNomeCupomTest() {
        NovoCupomDto novoCupomDto = new NovoCupomDto();
        novoCupomDto.setNomeCupom("teste");
        novoCupomDto.setDataExpiracao(5L);
        novoCupomDto.setDescricao("teste teste");
        novoCupomDto.setPorcentagem(5D);
        Cupom cupom = cupomService.cadastroDto(novoCupomDto);
        cupomRepository.save(cupom);
        Assertions.assertNotNull(cupomRepository.findByNomeCupom("teste"));
        cupomRepository.delete(cupom);
    }


}
