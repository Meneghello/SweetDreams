package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Model.Cliente;
import com.SweetDreams.sweetDreams.Model.DTOs.ClienteDto;
import com.SweetDreams.sweetDreams.Model.DTOs.NovoVendedorDto;
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

    @Autowired
    ClienteService clienteService;

    @Override
    public Vendedor findByCpf(String cpf){
        if (clienteRepository.findByCpf(cpf)!=null){
            String id = clienteRepository.findByCpf(cpf).getId();
            if (vendedorRepository.findByClienteId(id)!=null){
                Vendedor vendedor = vendedorRepository.findByClienteId(id);
                return vendedor;
            }
            return null;
        }
        return null;
    }

    @Override
    public Vendedor findByClienteId(String id){return vendedorRepository.findByClienteId(id);}

    @Override
    public List<Vendedor> findAll(){return vendedorRepository.findAll();}

    @Override
    public Vendedor findByCodigoVendedor(Long codigoVendedor){return vendedorRepository.findByCodigoVendedor(codigoVendedor);};

    @Override
    public Vendedor save(Vendedor vendedor){return vendedorRepository.save(vendedor);}

    @Override
    public void delete(Vendedor vendedor){vendedorRepository.delete(vendedor);}

    @Override
    public Vendedor update(Cliente clienteDto, String cpf){
        Vendedor vendedor = findByCpf(cpf);
        vendedor.getCliente().setEndereço(clienteDto.getEndereço());
        vendedor.getCliente().setCelular(clienteDto.getCelular());
        vendedor.getCliente().setNome(clienteDto.getNome());
        vendedor.getCliente().setEmail(clienteDto.getEmail());
        vendedor.setCpf(cpf);
        clienteSave(clienteDto,cpf);
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

    @Override
    public Vendedor cadastroDto(NovoVendedorDto vendedorDto){
        Vendedor vendedor = new Vendedor();
        vendedor.setCodigoVendedor(gerarCodigoVendedor());

        if(findCliente(vendedorDto.getCliente().getCpf())==null){
            Cliente cliente = clienteRepository.save(vendedorDto.getCliente());
            vendedor.setCliente(cliente);
            vendedor.getCliente().setNome(vendedorDto.getCliente().getNome().toLowerCase());
            vendedor.setCpf(vendedorDto.getCliente().getCpf());
            return vendedor;
        }
        vendedor.setCliente(findCliente(vendedorDto.getCliente().getCpf()));
        return vendedor;
    }

    @Override
    public Cliente atualizacaoDto(ClienteDto vendedorDto){
        return clienteService.atualizacaoDto(vendedorDto);
    }

    public Cliente findCliente(String cpf){
        return clienteRepository.findByCpf(cpf);
    }
    public void clienteSave(Cliente clienteDTO , String cpf){
        Cliente cliente = clienteRepository.findByCpf(cpf);
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setNome(clienteDTO.getNome().toLowerCase());
        cliente.setCelular(clienteDTO.getCelular());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setEndereço(clienteDTO.getEndereço());
        clienteRepository.save(cliente);
    }
}
