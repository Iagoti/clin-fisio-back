package com.system.fisio.application.dto;

import java.time.LocalDateTime;
import com.system.fisio.domain.enums.AtivoInativoEnum;
import com.system.fisio.domain.enums.TipoUsuario;

public class UsuarioResponse {
    
    private String nmUsuario;
    private String email;
    private String login;
    private AtivoInativoEnum stUsuario;
    private TipoUsuario tpUsuario;
    private LocalDateTime dtCadastro;

    public UsuarioResponse() { }

    public UsuarioResponse(String nmUsuario, String email, String login, AtivoInativoEnum stUsuario,
            TipoUsuario tpUsuario, LocalDateTime dtCadastro) {
        this.nmUsuario = nmUsuario;
        this.email = email;
        this.login = login;
        this.stUsuario = stUsuario;
        this.tpUsuario = tpUsuario;
        this.dtCadastro = dtCadastro;
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

    public TipoUsuario getTpUsuario() {
        return tpUsuario;
    }

    public void setTpUsuario(TipoUsuario tpUsuario) {
        this.tpUsuario = tpUsuario;
    }

    public LocalDateTime getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(LocalDateTime dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

}
