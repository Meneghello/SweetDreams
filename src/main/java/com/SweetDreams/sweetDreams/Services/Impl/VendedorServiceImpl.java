package com.SweetDreams.sweetDreams.Services.Impl;

import com.SweetDreams.sweetDreams.Handle.AuthorizationExceptionHandle;
import com.SweetDreams.sweetDreams.Models.Cliente;
import com.SweetDreams.sweetDreams.Models.DTOs.ClienteDto;
import com.SweetDreams.sweetDreams.Models.DTOs.NovoVendedorDto;
import com.SweetDreams.sweetDreams.Models.Perfil;
import com.SweetDreams.sweetDreams.Models.Vendedor;

import com.SweetDreams.sweetDreams.Repository.ClienteRepository;
import com.SweetDreams.sweetDreams.Repository.VendedorRepository;
import com.SweetDreams.sweetDreams.Security.UserSS;
import com.SweetDreams.sweetDreams.Services.ClienteService;
import com.SweetDreams.sweetDreams.Services.UserService;
import com.SweetDreams.sweetDreams.Services.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class VendedorServiceImpl implements VendedorService {

    @Autowired
    VendedorRepository vendedorRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ClienteService clienteService;

    @Override
    public Vendedor findByCpf(String cpf) {
//        UserSS userSS = UserService.authenticated();
//        if(userSS==null || !userSS.hasRole(Perfil.admin) && !cpf.equals(userSS.getUsername())){
//            throw new AuthorizationExceptionHandle("Acesso negado");
//        }
        return vendedorRepository.findByCpf(cpf);
    }

    @Override
    public Vendedor findByClienteId(String id) {
        return vendedorRepository.findByClienteId(id);
    }

    @Override
    public List<Vendedor> findAll() {
        return vendedorRepository.findAll();
    }

    @Override
    public Vendedor findByCodigoVendedor(Long codigoVendedor) {
//        UserSS userSS = UserService.authenticated();
//        if(userSS==null || !userSS.hasRole(Perfil.admin) && !codigoVendedor.equals(findByCpf(userSS.getUsername()).getCodigoVendedor())){
//            throw new AuthorizationExceptionHandle("Acesso negado");
//        }
        return vendedorRepository.findByCodigoVendedor(codigoVendedor);
    }


    @Override
    public Vendedor save(Vendedor vendedor) {
        return vendedorRepository.save(vendedor);
    }

    @Override
    public void delete(Vendedor vendedor) {
        vendedorRepository.delete(vendedor);
    }

    @Override
    public Vendedor update(Cliente clienteDto, String cpf) {
        Vendedor vendedor = findByCpf(cpf);
        vendedor.getCliente().setEndereço(clienteDto.getEndereço());
        vendedor.getCliente().setCelular(clienteDto.getCelular());
        vendedor.getCliente().setNome(clienteDto.getNome());
        vendedor.getCliente().setEmail(clienteDto.getEmail());
        vendedor.setCpf(cpf);
        clienteSave(clienteDto, cpf);
        return vendedorRepository.save(vendedor);

    }

    @Override
    public Long gerarCodigoVendedor() {
        long codigoVendedor = new Random().nextInt(999 - 100 + 1) + 100;
        if (vendedorRepository.findByCodigoVendedor(codigoVendedor) != null) {
            return gerarCodigoVendedor();
        }
        return codigoVendedor;
    }

    @Override
    public Vendedor cadastroDto(String cpf) {
        Vendedor vendedor = new Vendedor();
        vendedor.setCodigoVendedor(gerarCodigoVendedor());
        vendedor.setCliente(findCliente(cpf));
        vendedor.setCpf(cpf);
        vendedor.setRole(Perfil.vendedor);
        findCliente(cpf).setRole(Perfil.vendedor);
        return vendedor;
    }

    @Override
    public Cliente atualizacaoDto(ClienteDto vendedorDto) {
        return clienteService.atualizacaoDto(vendedorDto);
    }


    public Cliente findCliente(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

    public void clienteSave(Cliente clienteDTO, String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf);
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setNome(clienteDTO.getNome().toLowerCase());
        cliente.setCelular(clienteDTO.getCelular());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setEndereço(clienteDTO.getEndereço());
        cliente.setSenha(clienteDTO.getSenha());
        clienteRepository.save(cliente);
    }
}
