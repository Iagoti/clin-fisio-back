package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.UsuarioResponse;
import com.system.fisio.domain.ports.IUsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class BuscarUsuarioByIdUseCase {

    private final IUsuarioRepository usuarioRepository;

    public BuscarUsuarioByIdUseCase(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponse execute(Integer cdUsuario) {
        return usuarioRepository.findById(cdUsuario)
                .map(usuario -> new UsuarioResponse(
                        usuario.getCdUsuario(),
                        usuario.getNmUsuario(),
                        usuario.getEmail(),
                        usuario.getLogin(),
                        usuario.getStUsuario(),
                        usuario.getTpUsuario(),
                        usuario.getDataCadastro()
                ))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
