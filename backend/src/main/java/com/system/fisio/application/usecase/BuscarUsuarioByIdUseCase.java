package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.UsuarioResponse;
import com.system.fisio.application.mapper.UsuarioMapper;
import com.system.fisio.domain.exception.UsuarioException;
import com.system.fisio.domain.ports.IUsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class BuscarUsuarioByIdUseCase {

    private final IUsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public BuscarUsuarioByIdUseCase(IUsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioResponse execute(Integer cdUsuario) {
        return usuarioRepository.findById(cdUsuario)
                .map(usuarioMapper::toResponse)
                .orElseThrow(() -> new UsuarioException("Usuário não encontrado"));
    }
}
