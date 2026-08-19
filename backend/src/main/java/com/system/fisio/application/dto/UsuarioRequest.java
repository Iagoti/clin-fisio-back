package com.system.fisio.application.dto;

import com.system.fisio.domain.enums.AtivoInativoEnum;
import java.time.LocalDateTime;
import java.util.Set;

public class UsuarioRequest {
    private Integer cdUsuario;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private AtivoInativoEnum stUsuario;
    private Set<Integer> cdRoles;
    private LocalDateTime dataCadastro;

    public UsuarioRequest() {
        this.dataCadastro = LocalDateTime.now();
    }

    public UsuarioRequest(
            Integer cdUsuario,
            String nome,
            String email,
            String login,
            String senha,
            AtivoInativoEnum stUsuario,
            Set<Integer> cdRoles
    ) {
        this.cdUsuario = cdUsuario;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.stUsuario = stUsuario;
        this.cdRoles = cdRoles;
        this.dataCadastro = LocalDateTime.now();
    }

    public Integer getCdUsuario() {
        return cdUsuario;
    }

    public void setCdUsuario(Integer cdUsuario) {
        this.cdUsuario = cdUsuario;
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

    public Set<Integer> getCdRoles() {
        return cdRoles;
    }

    public void setCdRoles(Set<Integer> cdRoles) {
        this.cdRoles = cdRoles;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}
