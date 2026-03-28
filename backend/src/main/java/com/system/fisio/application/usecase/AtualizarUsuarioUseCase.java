package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.UsuarioRequest;
import com.system.fisio.application.dto.UsuarioResponse;
import com.system.fisio.application.mapper.UsuarioMapper;
import com.system.fisio.domain.ports.IUsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class AtualizarUsuarioUseCase {

    private final IUsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public AtualizarUsuarioUseCase(IUsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioResponse execute(UsuarioRequest usuarioRequest) {
        var usuario = usuarioMapper.toDomain(usuarioRequest);
        var usuarioSalvo = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(usuarioSalvo);
    }
}

