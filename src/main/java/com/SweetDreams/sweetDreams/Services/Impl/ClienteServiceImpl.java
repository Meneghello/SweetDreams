package com.SweetDreams.sweetDreams.Services.Impl;


import com.SweetDreams.sweetDreams.Controller.ClienteController;
import com.SweetDreams.sweetDreams.Handle.AuthorizationExceptionHandle;
import com.SweetDreams.sweetDreams.Models.Email;
import com.SweetDreams.sweetDreams.Models.Perfil;
import com.SweetDreams.sweetDreams.Security.UserSS;
import com.SweetDreams.sweetDreams.Services.EmailSenderService;
import com.SweetDreams.sweetDreams.Services.UserService;
import com.SweetDreams.sweetDreams.SweetDreamsApplication;
import com.SweetDreams.sweetDreams.Models.Cliente;
import com.SweetDreams.sweetDreams.Models.DTOs.ClienteDto;
import com.SweetDreams.sweetDreams.Models.DTOs.NovoClienteDto;
import com.SweetDreams.sweetDreams.Repository.ClienteRepository;
import com.SweetDreams.sweetDreams.Services.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    private Environment env;

    private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    @Override
    public Cliente findByCpf(String cpf) {
//        UserSS userSS = UserService.authenticated();
//        if(userSS==null || !userSS.hasRole(Perfil.admin) && !cpf.equals(userSS.getUsername())){
//            throw new AuthorizationExceptionHandle("Acesso negado");
//        }
        return clienteRepository.findByCpf(cpf);
    }

    @Override
    public Cliente save(Cliente cliente) {
        //enviarEmailCadastro(cliente);
        return clienteRepository.save(cliente);
    }

    @Override
    public void delete(Cliente cliente) {
        clienteRepository.delete(cliente);
    }

    @Override
    public Cliente update(Cliente clienteDto, String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf);
        cliente.setNome(clienteDto.getNome().toLowerCase());
        cliente.setCelular(clienteDto.getCelular());
        cliente.setEmail(clienteDto.getEmail());
        cliente.setEndere??o(clienteDto.getEndere??o());
        return clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente cadastroDto(NovoClienteDto novoClienteDto) {
        Cliente cliente = new Cliente();
        cliente.setEndere??o(novoClienteDto.getEndere??o());
        cliente.setEmail(novoClienteDto.getEmail());
        cliente.setCelular(novoClienteDto.getCelular());
        cliente.setNome(novoClienteDto.getNome().toLowerCase());
        cliente.setCpf(novoClienteDto.getCpf());
        cliente.setSenha(bCryptPasswordEncoder.encode(novoClienteDto.getSenha()));
        cliente.setDataNascimento(novoClienteDto.getDataNascimento());
        return cliente;

    }
    @Override
    public Cliente atualizacaoDto(ClienteDto clienteDto) {
        Cliente cliente = new Cliente();
        cliente.setEmail(clienteDto.getEmail());
        cliente.setNome(clienteDto.getNome());
        cliente.setCelular(clienteDto.getCelular());
        cliente.setEndere??o(clienteDto.getEndere??o());
        return cliente;
    }


    public void enviarEmailCadastro(Cliente cliente){
        log.info("Enviando email");
        Email mail = new Email();
        mail.setEmailDe(env.getProperty("spring.mail.username"));
        mail.setEmailPara("lojadocessweetdreams@gmail.com");
        mail.setEmailAssunto("Novo cadastro");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("Nome", cliente.getNome());
        model.put("Cpf", cliente.getCpf());
        model.put("Email", cliente.getEmail());
        model.put("assinatura", "https://sweet-dreams-loja.herokuapp.com/");
        mail.setModel(model);

        try {
            log.info("Email enviado");
            emailSenderService.sendEmail(mail);
        } catch (Exception e) {
            log.info("Falha no envio do email");
            e.printStackTrace();
        }
    }
}
