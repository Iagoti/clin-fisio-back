package com.system.fisio.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_usuario")
    private Integer cdUsuario;

    @Column(name = "nm_usuario")
    private String nmUsuario;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "login")
    private String login;

    @Column(name = "ds_senha_hash")
    private String senha;

    @Column(name = "st_usuario")
    private Integer stUsuario;

    @Column(name = "dt_cadastro")
    private LocalDateTime dtCadastro;

    // A coluna tp_usuario permanece no banco como dado legado (ver
    // db/migration/V1__baseline.sql) mas deixa de ser mapeada aqui: papéis e
    // permissões passam a ser geridos via usuario_role/role/permissao.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_role",
            joinColumns = @JoinColumn(name = "cd_usuario"),
            inverseJoinColumns = @JoinColumn(name = "cd_role")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    public UsuarioEntity() {
    }

    public UsuarioEntity(Integer cdUsuario, String nmUsuario, String email, String login,
                         String senha, Integer stUsuario) {
        this.cdUsuario = cdUsuario;
        this.nmUsuario = nmUsuario;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.stUsuario = stUsuario;
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

    public Integer getStUsuario() {
        return stUsuario;
    }

    public LocalDateTime getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(LocalDateTime dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}
