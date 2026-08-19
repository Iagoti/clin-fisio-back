package com.system.fisio.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "permissao")
public class PermissaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_permissao")
    private Integer cdPermissao;

    @Column(name = "cd_chave", nullable = false, unique = true)
    private String cdChave;

    @Column(name = "ds_permissao")
    private String dsPermissao;

    @Column(name = "nm_modulo")
    private String nmModulo;

    @Column(name = "dt_cadastro")
    private LocalDateTime dtCadastro;

    public PermissaoEntity() {
    }

    public Integer getCdPermissao() {
        return cdPermissao;
    }

    public void setCdPermissao(Integer cdPermissao) {
        this.cdPermissao = cdPermissao;
    }

    public String getCdChave() {
        return cdChave;
    }

    public void setCdChave(String cdChave) {
        this.cdChave = cdChave;
    }

    public String getDsPermissao() {
        return dsPermissao;
    }

    public void setDsPermissao(String dsPermissao) {
        this.dsPermissao = dsPermissao;
    }

    public String getNmModulo() {
        return nmModulo;
    }

    public void setNmModulo(String nmModulo) {
        this.nmModulo = nmModulo;
    }

    public LocalDateTime getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(LocalDateTime dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PermissaoEntity that)) return false;
        return cdPermissao != null && cdPermissao.equals(that.cdPermissao);
    }

    @Override
    public int hashCode() {
        return cdPermissao != null ? cdPermissao.hashCode() : 0;
    }
}
