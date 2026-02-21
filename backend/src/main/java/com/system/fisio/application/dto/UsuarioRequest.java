package com.system.fisio.application.dto;

import com.system.fisio.domain.enums.AtivoInativoEnum;
import com.system.fisio.domain.enums.TipoUsuario;
import java.time.LocalDateTime;

public class UsuarioRequest {
    private String nome;
    private String email;
    private String login;
    private String senha;
    private AtivoInativoEnum stUsuario;
    private TipoUsuario tipo;
    private LocalDateTime dataCadastro;

    public UsuarioRequest() {
        this.dataCadastro = LocalDateTime.now();
    }

    public UsuarioRequest(
            String nome,
            String email,
            String login,
            String senha,
            AtivoInativoEnum stUsuario,
            TipoUsuario tipo
    ) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.stUsuario = stUsuario;
        this.tipo = tipo;
        this.dataCadastro = LocalDateTime.now();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public AtivoInativoEnum getStUsuario() {
        return stUsuario;
    }

    public void setStUsuario(AtivoInativoEnum stUsuario) {
        this.stUsuario = stUsuario;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}
