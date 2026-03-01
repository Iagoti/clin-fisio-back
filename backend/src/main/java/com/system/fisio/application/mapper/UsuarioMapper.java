package com.system.fisio.application.mapper;

import com.system.fisio.domain.model.Usuario;
import com.system.fisio.application.dto.UsuarioRequest;
import com.system.fisio.application.dto.UsuarioResponse;

import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toDomain(UsuarioRequest usuarioRequest) {
        return new Usuario(
                null,
                usuarioRequest.getNome(),
                usuarioRequest.getEmail(),
                usuarioRequest.getLogin(),
                usuarioRequest.getSenha(),
                usuarioRequest.getTipo(),
                usuarioRequest.getStUsuario()
        );
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getCdUsuario(),
                usuario.getNmUsuario(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getStUsuario(),
                usuario.getTpUsuario(),
                usuario.getDataCadastro()
        );
    }

}
