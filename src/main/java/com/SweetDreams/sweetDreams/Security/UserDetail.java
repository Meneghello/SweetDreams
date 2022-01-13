package com.SweetDreams.sweetDreams.Security;

import com.SweetDreams.sweetDreams.Models.Perfil;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetail implements UserDetails {

    @Id
    private String id;
    private String senha;
    private String cpf;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetail() {
    }

    public UserDetail(String id, String senha, String cpf, Set<Perfil> perfils) {
        this.id = id;
        this.senha = senha;
        this.cpf = cpf;
        this.authorities = perfils.stream().map(x->new SimpleGrantedAuthority(x.getRole())).collect(Collectors.toList());
    }

    public String getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
