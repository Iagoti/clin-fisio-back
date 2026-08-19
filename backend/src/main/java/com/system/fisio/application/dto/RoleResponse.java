package com.system.fisio.application.dto;

import com.system.fisio.domain.enums.AtivoInativoEnum;

import java.time.LocalDateTime;
import java.util.List;

public class RoleResponse {

    private Integer cdRole;
    private String nmRole;
    private String dsRole;
    private AtivoInativoEnum stRole;
    private boolean flSistema;
    private List<PermissaoResponse> permissoes;
    private LocalDateTime dtCadastro;

    public RoleResponse() {
    }

    public RoleResponse(Integer cdRole, String nmRole, String dsRole, AtivoInativoEnum stRole,
                         boolean flSistema, List<PermissaoResponse> permissoes, LocalDateTime dtCadastro) {
        this.cdRole = cdRole;
        this.nmRole = nmRole;
        this.dsRole = dsRole;
        this.stRole = stRole;
        this.flSistema = flSistema;
        this.permissoes = permissoes;
        this.dtCadastro = dtCadastro;
    }

    public Integer getCdRole() {
        return cdRole;
    }

    public void setCdRole(Integer cdRole) {
        this.cdRole = cdRole;
    }

    public String getNmRole() {
        return nmRole;
    }

    public void setNmRole(String nmRole) {
        this.nmRole = nmRole;
    }

    public String getDsRole() {
        return dsRole;
    }

    public void setDsRole(String dsRole) {
        this.dsRole = dsRole;
    }

    public AtivoInativoEnum getStRole() {
        return stRole;
    }

    public void setStRole(AtivoInativoEnum stRole) {
        this.stRole = stRole;
    }

    public boolean isFlSistema() {
        return flSistema;
    }

    public void setFlSistema(boolean flSistema) {
        this.flSistema = flSistema;
    }

    public List<PermissaoResponse> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<PermissaoResponse> permissoes) {
        this.permissoes = permissoes;
    }

    public LocalDateTime getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(LocalDateTime dtCadastro) {
        this.dtCadastro = dtCadastro;
    }
}
