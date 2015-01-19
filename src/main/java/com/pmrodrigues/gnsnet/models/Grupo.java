package com.pmrodrigues.gnsnet.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Marceloo on 29/09/2014.
 */
@Entity
@Table
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEquals = false;
        if( obj instanceof Grupo ){
            Grupo other = (Grupo) obj;
            isEquals = this.id == other.id;
        }
        return isEquals;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
