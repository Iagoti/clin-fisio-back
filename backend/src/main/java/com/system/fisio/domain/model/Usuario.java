package com.system.fisio.domain.model;

import com.system.fisio.domain.enums.AtivoInativoEnum;
import com.system.fisio.domain.enums.TipoUsuario;
import com.system.fisio.domain.exception.UsuarioException;
import java.time.LocalDateTime;

public class Usuario {

    private Integer cdUsuario;
    private String nmUsuario;
    private String email;
    private String login;
    private String senha;
    private TipoUsuario tpUsuario;
    private AtivoInativoEnum stUsuario;
    private final LocalDateTime dataCadastro;

    public Usuario(
            Integer cdUsuario,
            String nmUsuario,
            String email,
            String login,
            String senha,
            TipoUsuario tpUsuario,
            AtivoInativoEnum stUsuario
            
    ) {
        this.cdUsuario = cdUsuario;
        this.nmUsuario = nmUsuario;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.tpUsuario = tpUsuario;
        this.stUsuario = AtivoInativoEnum.ATIVO;
        this.dataCadastro = LocalDateTime.now();
        validar();
    }

    public void validarUsuarioAtivo() {
        if(!this.stUsuario.equals(AtivoInativoEnum.ATIVO)) {
            throw new UsuarioException("Usuário inativo.");
        }
    }

    private void validar() {
        if (nmUsuario == null || nmUsuario.isBlank()) throw new UsuarioException("Nome obrigatório");
        if (login == null || login.isBlank()) throw new UsuarioException("Login obrigatório");
        if (senha == null || senha.isBlank()) throw new UsuarioException("Senha obrigatória");
        if (tpUsuario == null) throw new UsuarioException("Tipo de usuário obrigatório");
    }

    public void inativar() {
        this.stUsuario = AtivoInativoEnum.INATIVO;
    }

    public void ativar() {
        this.stUsuario = AtivoInativoEnum.ATIVO;
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

    public AtivoInativoEnum getStUsuario() {
        return stUsuario;
    }

    public TipoUsuario getTpUsuario() {
        return tpUsuario;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}
