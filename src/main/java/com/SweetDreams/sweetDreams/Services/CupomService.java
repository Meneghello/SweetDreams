package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Models.Cupom;
import com.SweetDreams.sweetDreams.Models.DTOs.NovoCupomDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CupomService {

    Cupom cadastroDto(NovoCupomDto novoCupomDto);
    void save(Cupom cupom);

    List<Cupom> findAll();

    void delete(String nome);

    Cupom findByNomeCupom(String nome);
}
