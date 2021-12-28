package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Model.Produto;
import com.SweetDreams.sweetDreams.Model.Vendedor;

import com.SweetDreams.sweetDreams.Repository.ClienteRepository;
import com.SweetDreams.sweetDreams.Repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class VendedorServiceImpl implements VendedorService{

    @Autowired
    VendedorRepository vendedorRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public Vendedor findByCpf(String cpf){
        Cliente cliente = clienteRepository.findByCpf(cpf);

        return vendedorRepository.findByCliente(cliente);
    }

    @Override
    public Vendedor findByCliente(Cliente cliente){return vendedorRepository.findByCliente(cliente);}

    @Override
    public List<Vendedor> findAll(){return vendedorRepository.findAll();}

    @Override
    public Vendedor findByCodigoVendedor(Long codigoVendedor){return vendedorRepository.findByCodigoVendedor(codigoVendedor);};

    @Override
    public Vendedor save(Vendedor vendedor){

        Cliente cliente = vendedor.getCliente();
        clienteRepository.save(cliente);
        vendedor.setCliente(cliente);
        vendedor.setCpf(cliente.getCpf());
        return vendedorRepository.save(vendedor);
    }

    @Override
    public void delete(Vendedor vendedor){
        clienteRepository.delete(clienteRepository.findByCpf(vendedor.getCpf()));
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
