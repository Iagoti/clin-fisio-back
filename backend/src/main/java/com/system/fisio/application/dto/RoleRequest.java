package com.system.fisio.application.dto;

import com.system.fisio.domain.enums.AtivoInativoEnum;

import java.util.Set;

public class RoleRequest {

    private Integer cdRole;
    private String nmRole;
    private String dsRole;
    private AtivoInativoEnum stRole;
    private Set<Integer> cdPermissoes;

    public RoleRequest() {
    }

    public RoleRequest(Integer cdRole, String nmRole, String dsRole, AtivoInativoEnum stRole, Set<Integer> cdPermissoes) {
        this.cdRole = cdRole;
        this.nmRole = nmRole;
        this.dsRole = dsRole;
        this.stRole = stRole;
        this.cdPermissoes = cdPermissoes;
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

    public Set<Integer> getCdPermissoes() {
        return cdPermissoes;
    }

    public void setCdPermissoes(Set<Integer> cdPermissoes) {
        this.cdPermissoes = cdPermissoes;
    }
}
