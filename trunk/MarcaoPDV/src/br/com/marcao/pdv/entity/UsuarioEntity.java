package br.com.marcao.pdv.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import pedrociarlini.reuse.entity.IEntity;

/**
 * Entity implementation class for Entity: UsuarioEntity
 * 
 */
@Entity
@Table(name="SEG_USUARIO")
@NamedQuery(name = "findUsuarioByLogin", query = "select u from UsuarioEntity u where u.login = :login" )
public class UsuarioEntity implements Serializable, IEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="ID_USUARIO")
    @GeneratedValue
    private int id;

    @Column(name="NM_LOGIN")
    private String login;

    @Column(name="TX_SENHA")
    private String senha;
    
    @Column(name="NM_USUARIO")
    private String nome;

    public UsuarioEntity() {
        super();
    }

    public UsuarioEntity(String login, String senha) {
        setLogin(login);
        setSenha(senha);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
