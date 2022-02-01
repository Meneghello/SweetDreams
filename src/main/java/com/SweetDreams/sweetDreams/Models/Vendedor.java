package com.SweetDreams.sweetDreams.Models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Document(value = "Vendedores")
public class Vendedor {

    @Id
    private String id;

    private String cpf;
    @Valid
    @DBRef
    private Cliente cliente;
    private Long codigoVendedor;
    private Set<Integer> role = new HashSet<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cliente.getCpf();
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

    public Set<Perfil> getRole() {
        return role.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void setRole(Perfil roles) {
        role.add(roles.getCode());
    }

    public Vendedor(String id, String cpf, Cliente cliente, Long codigoVendedor) {
        this.cpf=cliente.getCpf();
        this.id = id;
        this.cliente = cliente;
        this.codigoVendedor = codigoVendedor;
        setRole(Perfil.vendedor);
    }

    public Vendedor() {
        setRole(Perfil.vendedor);
    }
}
