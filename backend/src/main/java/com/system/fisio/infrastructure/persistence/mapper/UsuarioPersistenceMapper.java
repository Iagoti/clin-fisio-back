package com.system.fisio.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import com.system.fisio.domain.enums.AtivoInativoEnum;
import com.system.fisio.domain.model.Role;
import com.system.fisio.domain.model.Usuario;
import com.system.fisio.infrastructure.persistence.entity.UsuarioEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsuarioPersistenceMapper {

    private final RolePersistenceMapper rolePersistenceMapper;

    public UsuarioPersistenceMapper(RolePersistenceMapper rolePersistenceMapper) {
        this.rolePersistenceMapper = rolePersistenceMapper;
    }

    public UsuarioEntity toEntity(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity(
            usuario.getCdUsuario(),
            usuario.getNmUsuario(),
            usuario.getEmail(),
            usuario.getLogin(),
            usuario.getSenha(),
            usuario.getStUsuario().getCodigo()
        );
        entity.setDtCadastro(usuario.getDataCadastro());
        entity.setRoles(usuario.getRoles().stream()
                .map(rolePersistenceMapper::toEntity)
                .collect(Collectors.toSet()));
        return entity;
    }

    public Usuario toDomain(UsuarioEntity usuarioEntity) {
        Set<Role> roles = usuarioEntity.getRoles().stream()
                .map(rolePersistenceMapper::toDomain)
                .collect(Collectors.toSet());
        return new Usuario(
            usuarioEntity.getCdUsuario(),
            usuarioEntity.getNmUsuario(),
            usuarioEntity.getEmail(),
            usuarioEntity.getLogin(),
            usuarioEntity.getSenha(),
            roles,
            AtivoInativoEnum.fromCodigo(usuarioEntity.getStUsuario())
        );
    }
}
