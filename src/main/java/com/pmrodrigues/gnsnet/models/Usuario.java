package com.pmrodrigues.gnsnet.models;

import com.pmrodrigues.gnsnet.utilities.MD5;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Marceloo on 27/09/2014.
 */
@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
@DynamicUpdate()
public class Usuario implements Serializable {

    private static final long QUANTIDADE_TENTATIVAS_PERMITIDAS = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "E-mail é obrigatório")
    @Column(name= "email" , nullable = false , unique = true , length = 200)
    private String email;

    @Column( name = "password" , nullable = false)
    private String password;

    @Column
    private boolean bloqueado;

    @Column
    private long tentativas = 0L;

    @ManyToOne(optional = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinTable(name = "membro", joinColumns = @JoinColumn(name = "membro_id"), inverseJoinColumns = @JoinColumn(name = "grupo_id"))
    @LazyCollection(LazyCollectionOption.EXTRA)
    private Set<Grupo> grupos = new LinkedHashSet<>();

    @Transient
    private String cleanPassword;

    public Usuario(){
        this.generateNewPassword();
    }

    @PrePersist
    public void preInsert() {
        this.generateNewPassword();
    }

    public String getPassword() {
        return password;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void incrementarTentativasInvalidas() {
        this.tentativas++;
        this.bloqueado = (this.tentativas >= QUANTIDADE_TENTATIVAS_PERMITIDAS);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public Set<Grupo> getGrupos() {
        return grupos;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getCleanPassword() {
        return cleanPassword;
    }

    public void associar(final Collection<Grupo> grupos) {
        this.grupos.clear();
        this.grupos.addAll(grupos);
    }

    public void generateNewPassword() {
        try {
            this.cleanPassword = RandomStringUtils.randomAlphanumeric(8);
            this.password = MD5.encrypt(this.cleanPassword);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isNovo() {
        return this.getId() == null || this.getId() == 0L;
    }
}
