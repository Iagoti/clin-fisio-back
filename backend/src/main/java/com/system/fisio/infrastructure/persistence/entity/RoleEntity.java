package com.system.fisio.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_role")
    private Integer cdRole;

    @Column(name = "nm_role", nullable = false, unique = true)
    private String nmRole;

    @Column(name = "ds_role")
    private String dsRole;

    @Column(name = "st_role")
    private Integer stRole;

    @Column(name = "fl_sistema", nullable = false)
    private boolean flSistema;

    @Column(name = "dt_cadastro")
    private LocalDateTime dtCadastro;

    // EAGER: perfis/permissões são pequenos em volume e quase sempre precisam estar
    // disponíveis junto com o Role (ex.: montar o token de login), então o custo de
    // LAZY + gestão de sessão aberta não compensa aqui.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissao",
            joinColumns = @JoinColumn(name = "cd_role"),
            inverseJoinColumns = @JoinColumn(name = "cd_permissao")
    )
    private Set<PermissaoEntity> permissoes = new HashSet<>();

    public RoleEntity() {
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

    public Integer getStRole() {
        return stRole;
    }

    public void setStRole(Integer stRole) {
        this.stRole = stRole;
    }

    public boolean isFlSistema() {
        return flSistema;
    }

    public void setFlSistema(boolean flSistema) {
        this.flSistema = flSistema;
    }

    public LocalDateTime getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(LocalDateTime dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public Set<PermissaoEntity> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(Set<PermissaoEntity> permissoes) {
        this.permissoes = permissoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleEntity that)) return false;
        return cdRole != null && cdRole.equals(that.cdRole);
    }

    @Override
    public int hashCode() {
        return cdRole != null ? cdRole.hashCode() : 0;
    }
}
