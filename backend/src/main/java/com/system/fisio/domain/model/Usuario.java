package com.system.fisio.domain.model;

import com.system.fisio.domain.enums.AtivoInativoEnum;
import com.system.fisio.domain.exception.UsuarioException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Usuario {

    private Integer cdUsuario;
    private String nmUsuario;
    private String email;
    private String login;
    private String senha;
    private Set<Role> roles;
    private AtivoInativoEnum stUsuario;
    private final LocalDateTime dataCadastro;

    public Usuario(
            Integer cdUsuario,
            String nmUsuario,
            String email,
            String login,
            String senha,
            Set<Role> roles,
            AtivoInativoEnum stUsuario
    ) {
        this.cdUsuario = cdUsuario;
        this.nmUsuario = nmUsuario;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.roles = roles != null ? new HashSet<>(roles) : new HashSet<>();
        this.stUsuario = stUsuario;
        this.dataCadastro = LocalDateTime.now();
        validar();
    }

    public void validarUsuarioAtivo() {
        if (!this.stUsuario.equals(AtivoInativoEnum.ATIVO)) {
            throw new UsuarioException("Usuário inativo.");
        }
    }

    private void validar() {
        if (nmUsuario == null || nmUsuario.isBlank()) throw new UsuarioException("Nome obrigatório");
        if (login == null || login.isBlank()) throw new UsuarioException("Login obrigatório");
        if (cdUsuario == null && (senha == null || senha.isBlank())) throw new UsuarioException("Senha obrigatória");
        if (roles == null || roles.isEmpty()) throw new UsuarioException("Usuário precisa ter ao menos um perfil de acesso");
    }

    /** Delega a checagem de permissão para os perfis atribuídos ao usuário. */
    public boolean possuiPermissao(String chave) {
        return roles.stream().anyMatch(role -> role.possuiPermissao(chave));
    }

    public Set<String> getPermissoesEfetivas() {
        return roles.stream()
                .flatMap(role -> role.getPermissoes().stream())
                .map(Permissao::getCdChave)
                .collect(Collectors.toUnmodifiableSet());
    }

    public Set<String> getNomesRoles() {
        return roles.stream()
                .map(Role::getNmRole)
                .collect(Collectors.toUnmodifiableSet());
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

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}
