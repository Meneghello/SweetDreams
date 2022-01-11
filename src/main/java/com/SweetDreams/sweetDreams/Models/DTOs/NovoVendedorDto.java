package com.SweetDreams.sweetDreams.Models.DTOs;


import com.SweetDreams.sweetDreams.Models.Cliente;

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
