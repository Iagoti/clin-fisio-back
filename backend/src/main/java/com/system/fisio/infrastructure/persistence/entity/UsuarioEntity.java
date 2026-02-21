package com.system.fisio.infrastructure.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_usuario")
    private Integer cdUsuario;

    @Column(name = "nm_usuario")
    private String nmUsuario;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "login")
    private String login;

    @Column(name = "ds_senha_hash", nullable = false)
    private String senha;

    @Column(name = "tp_usuario")
    private Integer tpUsuario;

    @Column(name = "st_usuario")
    private Integer stUsuario;

    @Column(name = "dt_cadastro")
    private LocalDateTime dtCadastro;

    public UsuarioEntity() {
    }

    public UsuarioEntity(Integer cdUsuario, String nmUsuario, String email, String login,
                         String senha, Integer tpUsuario, Integer stUsuario) {
        this.cdUsuario = cdUsuario;
        this.nmUsuario = nmUsuario;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.tpUsuario = tpUsuario;
        this.stUsuario = stUsuario;
    }

    public Integer getCdUsuario() {
        return cdUsuario;
    }

    public String getNmUsuario() {
        return nmUsuario;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public Integer getTpUsuario() {
        return tpUsuario;
    }

    public Integer getStUsuario() {
        return stUsuario;
    }

    public LocalDateTime getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(LocalDateTime dtCadastro) {
        this.dtCadastro = dtCadastro;
    }
}