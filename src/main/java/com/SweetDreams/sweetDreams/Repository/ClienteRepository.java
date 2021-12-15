package com.SweetDreams.sweetDreams.Repository;

import com.SweetDreams.sweetDreams.Model.Cliente;
import com.mongodb.client.MongoCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {
}
