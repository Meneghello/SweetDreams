package com.SweetDreams.sweetDreams.Repository;


import com.SweetDreams.sweetDreams.Models.Cupom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CupomRepository extends MongoRepository<Cupom, String> {
    Cupom findByNomeCupom(String nome);
}
