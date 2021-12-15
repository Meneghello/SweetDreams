package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Model.Produto;
import com.SweetDreams.sweetDreams.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    ClienteRepository clienteRepository;


}
