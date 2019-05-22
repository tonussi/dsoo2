package br.ufsc.model;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Pessoa implements Serializable {

    public Pessoa() {
    }

    @Id
    @GeneratedValue
    private Integer id;

    private String nome;

    @Column(length = 20, unique = true)
    private String cpf;

    @Column(length = 20, unique = true)
    private String rg;

    @Temporal(TemporalType.DATE)
    private Calendar dataNascimento;

    @Column(length = 20, unique = true)
    private String telefone;

    private String endr;

    private String senha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Calendar getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Calendar dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndr() {
        return endr;
    }

    public void setEndr(String endr) {
        this.endr = endr;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Pessoa [\nid = " + id + ",\nnome= " + nome + ",\ncpf = " + cpf + ",\nrg = " + rg + ",\ndata_nascimento = "
                + dataNascimento + ",\ntelefone =" + telefone + ",\nendr =" + endr + ",\nsenha =" + senha + "\n]";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.cpf != null ? this.cpf.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pessoa other = (Pessoa) obj;
        if ((this.cpf == null) ? (other.cpf != null) : !this.cpf.equals(other.cpf)) {
            return false;
        }
        return true;
    }
}
