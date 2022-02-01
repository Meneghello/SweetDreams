package com.SweetDreams.sweetDreams.Services.Impl;

import com.SweetDreams.sweetDreams.Models.Cupom;
import com.SweetDreams.sweetDreams.Models.DTOs.NovoCupomDto;
import com.SweetDreams.sweetDreams.Repository.CupomRepository;
import com.SweetDreams.sweetDreams.Services.CupomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class CupomServiceImpl implements CupomService {

    @Autowired
    CupomRepository cupomRepository;

    @Override
    public Cupom cadastroDto(NovoCupomDto novoCupomDto) {
        Cupom cupom = new Cupom();
        cupom.setNomeCupom(novoCupomDto.getNomeCupom().toLowerCase());
        cupom.setDataInicial(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        cupom.setDataExpiracao((LocalDateTime.parse(cupom.getDataInicial(), DateTimeFormatter.ofPattern("dd-MM-yyyy " +
                "HH:mm:ss")).plusDays(novoCupomDto.getDataExpiracao())));
        cupom.setDescricao(novoCupomDto.getDescricao());
        cupom.setPorcentagem(novoCupomDto.getPorcentagem());
        return cupom;
    }

    @Override
    public void save(Cupom cupom) {
        cupomRepository.save(cupom);
    }

    @Override
    public List<Cupom> findAll() {
        return cupomRepository.findAll();
    }

    @Override
    public void delete(String nome) {
        cupomRepository.delete(findByNomeCupom(nome));
    }

    @Override
    public Cupom findByNomeCupom(String nome) {
        return cupomRepository.findByNomeCupom(nome);
    }


}
