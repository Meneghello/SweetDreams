package com.SweetDreams.sweetDreams.Model.DTOs;


import com.SweetDreams.sweetDreams.Model.Cliente;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.Valid;

public class NovoVendedorDto {

    @Valid
    private Cliente cliente;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    public NovoVendedorDto(Cliente cliente) {
        this.cliente = cliente;
    }

    public NovoVendedorDto() {
    }
}
