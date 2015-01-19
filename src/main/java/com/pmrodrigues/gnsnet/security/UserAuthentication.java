package com.pmrodrigues.gnsnet.security;

import com.pmrodrigues.gnsnet.models.Grupo;
import com.pmrodrigues.gnsnet.models.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by Marceloo on 29/09/2014.
 */
public class UserAuthentication implements Authentication {

    private boolean authenticated = false;
    private final Usuario usuario;
    private final Collection<SimpleGrantedAuthority> authorities = new LinkedHashSet<>();

    public UserAuthentication(final Usuario usuario) {
        this.usuario = usuario;
        this.authenticated = true;

        for(final Grupo grupo : usuario.getGrupos() ) {
            this.authorities.add(new SimpleGrantedAuthority(grupo.getNome().toUpperCase()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return usuario;
    }

    @Override
    public Object getDetails() {
        return usuario;
    }

    @Override
    public Object getPrincipal() {
        return usuario;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = true;
    }

    @Override
    public String getName() {
        return this.usuario.getNome();
    }
}
