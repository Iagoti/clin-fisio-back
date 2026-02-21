package com.system.fisio.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import com.system.fisio.domain.enums.AtivoInativoEnum;
import com.system.fisio.domain.enums.TipoUsuario;
import com.system.fisio.domain.model.Usuario;
import com.system.fisio.infrastructure.persistence.entity.UsuarioEntity;

@Component
public class UsuarioPersistenceMapper {

    public UsuarioEntity toEntity(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity(
            usuario.getCdUsuario(),
            usuario.getNmUsuario(),
            usuario.getEmail(),
            usuario.getLogin(),
            usuario.getSenha(),
            usuario.getTpUsuario().getCodigo(),
            usuario.getStUsuario().getCodigo()
        );
        entity.setDtCadastro(usuario.getDataCadastro());
        return entity;
    }

    public Usuario toDomain(UsuarioEntity usuarioEntity) {
        return new Usuario(
            usuarioEntity.getCdUsuario(),
            usuarioEntity.getNmUsuario(),
            usuarioEntity.getEmail(),
            usuarioEntity.getLogin(),
            usuarioEntity.getSenha(),
            TipoUsuario.fromCodigo(usuarioEntity.getTpUsuario()),
            AtivoInativoEnum.fromCodigo(usuarioEntity.getStUsuario())
        );
    }
}
