package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.UsuarioResponse;
import com.system.fisio.domain.ports.IUsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuscarTodosUsuariosUseCase {

    private final IUsuarioRepository usuarioRepository;

    public  BuscarTodosUsuariosUseCase(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioResponse> execute(){
        return usuarioRepository.findAll().stream()
                .map(usuario -> new UsuarioResponse(
                        usuario.getNmUsuario(),
                        usuario.getEmail(),
                        usuario.getLogin(),
                        usuario.getStUsuario(),
                        usuario.getTpUsuario(),
                        usuario.getDataCadastro()
                ))
                .toList();
    }
}
