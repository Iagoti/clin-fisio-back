package com.system.fisio.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.system.fisio.domain.enums.AtivoInativoEnum;

public class UsuarioResponse {

    private Integer cdUsuario;
    private String nmUsuario;
    private String email;
    private String login;
    private AtivoInativoEnum stUsuario;
    private List<RoleResumoResponse> roles;
    private LocalDateTime dtCadastro;

    public UsuarioResponse() { }

    public UsuarioResponse(Integer cdUsuario, String nmUsuario, String email, String login, AtivoInativoEnum stUsuario,
            List<RoleResumoResponse> roles, LocalDateTime dtCadastro) {
        this.cdUsuario = cdUsuario;
        this.nmUsuario = nmUsuario;
        this.email = email;
        this.login = login;
        this.stUsuario = stUsuario;
        this.roles = roles;
        this.dtCadastro = dtCadastro;
    }

    public Integer getCdUsuario() {
        return cdUsuario;
    }

    public void setCdUsuario(Integer cdUsuario) {
        this.cdUsuario = cdUsuario;
    }

    public String getNmUsuario() {
        return nmUsuario;
    }

    public void setNmUsuario(String nmUsuario) {
        this.nmUsuario = nmUsuario;
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

    public AtivoInativoEnum getStUsuario() {
        return stUsuario;
    }

    public void setStUsuario(AtivoInativoEnum stUsuario) {
        this.stUsuario = stUsuario;
    }

    public List<RoleResumoResponse> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleResumoResponse> roles) {
        this.roles = roles;
    }

    public LocalDateTime getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(LocalDateTime dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

}
