package com.SweetDreams.sweetDreams.Model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;


@Document(value = "Vendedores")
public class Vendedor {

    @Id
    private String id;

    private String cpf;

    @Valid
    @DBRef
    private Cliente cliente;
    private Long codigoVendedor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Long getCodigoVendedor() {
        return codigoVendedor;
    }

    public void setCodigoVendedor(Long codigoVendedor) {
        this.codigoVendedor = codigoVendedor;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Vendedor(String id, String cpf, Cliente cliente, Long codigoVendedor) {
        this.id = id;
        this.cpf = cpf;
        this.cliente = cliente;
        this.codigoVendedor = codigoVendedor;
    }

    public Vendedor() {
    }
}
