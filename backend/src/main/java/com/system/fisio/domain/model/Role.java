package com.system.fisio.domain.model;

import com.system.fisio.domain.enums.AtivoInativoEnum;
import com.system.fisio.domain.exception.RoleException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Role {

    private Integer cdRole;
    private String nmRole;
    private String dsRole;
    private AtivoInativoEnum stRole;
    private final boolean flSistema;
    private Set<Permissao> permissoes;
    private final LocalDateTime dataCadastro;

    public Role(
            Integer cdRole,
            String nmRole,
            String dsRole,
            AtivoInativoEnum stRole,
            boolean flSistema,
            Set<Permissao> permissoes,
            LocalDateTime dataCadastro
    ) {
        this.cdRole = cdRole;
        this.nmRole = nmRole;
        this.dsRole = dsRole;
        this.stRole = stRole;
        this.flSistema = flSistema;
        this.permissoes = permissoes != null ? new HashSet<>(permissoes) : new HashSet<>();
        this.dataCadastro = dataCadastro != null ? dataCadastro : LocalDateTime.now();
        validar();
    }

    private void validar() {
        if (nmRole == null || nmRole.isBlank()) {
            throw new RoleException("Nome do perfil obrigatório");
        }
        if (stRole == null) {
            throw new RoleException("Status do perfil obrigatório");
        }
    }

    /**
     * Perfis de sistema (ADMINISTRADOR/RECEPCAO, semeados pela migration V3) não
     * podem ser renomeados, ter suas permissões alteradas nem ser excluídos — evita
     * que alguém acidentalmente esvazie o perfil administrador e perca acesso.
     */
    public void validarNaoSistema() {
        if (flSistema) {
            throw new RoleException("Perfis de sistema não podem ser alterados ou excluídos");
        }
    }

    public boolean possuiPermissao(String chave) {
        if (chave == null) return false;
        return permissoes.stream().anyMatch(p -> chave.equals(p.getCdChave()));
    }

    public void atualizarDados(String nmRole, String dsRole, Set<Permissao> permissoes) {
        validarNaoSistema();
        this.nmRole = nmRole;
        this.dsRole = dsRole;
        this.permissoes = permissoes != null ? new HashSet<>(permissoes) : new HashSet<>();
        validar();
    }

    public void inativar() {
        this.stRole = AtivoInativoEnum.INATIVO;
    }

    public void ativar() {
        this.stRole = AtivoInativoEnum.ATIVO;
    }

    public Integer getCdRole() {
        return cdRole;
    }

    public String getNmRole() {
        return nmRole;
    }

    public String getDsRole() {
        return dsRole;
    }

    public AtivoInativoEnum getStRole() {
        return stRole;
    }

    public boolean isFlSistema() {
        return flSistema;
    }

    public Set<Permissao> getPermissoes() {
        return Collections.unmodifiableSet(permissoes);
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}
