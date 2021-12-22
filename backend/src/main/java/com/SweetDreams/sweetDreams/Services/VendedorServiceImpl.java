package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Model.Vendedor;

import com.SweetDreams.sweetDreams.Repository.ClienteRepository;
import com.SweetDreams.sweetDreams.Repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class VendedorServiceImpl implements VendedorService{

    @Autowired
    VendedorRepository vendedorRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public Vendedor findByCpf(String cpf){return vendedorRepository.findByCpf(cpf);}


    @Override
    public Vendedor findByCodigoVendedor(Long codigoVendedor){return vendedorRepository.findByCodigoVendedor(codigoVendedor);};

    @Override
    public Vendedor save(Vendedor vendedor){

        Cliente cliente = new Cliente();
        cliente.setCpf(vendedor.getCliente().getCpf());
        cliente.setNome(vendedor.getCliente().getNome());
        cliente.setEndereço(vendedor.getCliente().getEndereço());
        cliente.setEmail(vendedor.getCliente().getEmail());
        cliente.setCelular(vendedor.getCliente().getCelular());
        cliente.setDataNascimento(vendedor.getCliente().getDataNascimento());
        clienteRepository.save(cliente);
        vendedor.setCliente(cliente);
        vendedor.setCpf(cliente.getCpf());
        return vendedorRepository.save(vendedor);
    }

    @Override
    public void delete(Vendedor vendedor){
        //clienteRepository.delete(clienteRepository.findByCpf(vendedor.getCpf()));
        vendedorRepository.delete(vendedor);
    }

    @Override
    public Vendedor update(Vendedor novoVendedor, String cpf){
        Vendedor vendedor = vendedorRepository.findByCpf(cpf);
        vendedor.getCliente().setNome(novoVendedor.getCliente().getNome().toLowerCase());
        vendedor.getCliente().setCelular(novoVendedor.getCliente().getCelular());
        vendedor.getCliente().setEmail(novoVendedor.getCliente().getEmail());
        vendedor.getCliente().setEndereço(novoVendedor.getCliente().getEndereço());
        vendedor.getCliente().setDataNascimento(novoVendedor.getCliente().getDataNascimento());
        return vendedorRepository.save(vendedor);
    }

    @Override
    public Long gerarCodigoVendedor(){
        long codigoVendedor = new Random().nextInt(999-100+1)+100;
        if (vendedorRepository.findByCodigoVendedor(codigoVendedor)!=null){
            return gerarCodigoVendedor();
        }
        return codigoVendedor;
    }
}
