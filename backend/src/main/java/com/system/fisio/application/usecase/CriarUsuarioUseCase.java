package com.system.fisio.application.usecase;

import com.system.fisio.application.mapper.UsuarioMapper;
import com.system.fisio.domain.exception.UsuarioException;
import com.system.fisio.domain.model.Usuario;
import com.system.fisio.domain.ports.IUsuarioRepository;
import com.system.fisio.application.dto.UsuarioRequest;
import com.system.fisio.application.dto.UsuarioResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CriarUsuarioUseCase {

    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    public CriarUsuarioUseCase(
            IUsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            UsuarioMapper usuarioMapper
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
    }

    // Autorização (quem pode criar usuário) é responsabilidade da camada HTTP
    // (@PreAuthorize em UsuarioController), não deste caso de uso.
    public UsuarioResponse execute(UsuarioRequest usuarioRequest) {
        if (usuarioRepository.findByEmail(usuarioRequest.getEmail()).isPresent()) {
            throw new UsuarioException("Usuário já cadastrado");
        }
        UsuarioRequest requestEncodedPassword = new UsuarioRequest(
                usuarioRequest.getCdUsuario(),
                usuarioRequest.getNome(),
                usuarioRequest.getEmail(),
                usuarioRequest.getLogin(),
                passwordEncoder.encode(usuarioRequest.getSenha()),
                usuarioRequest.getStUsuario(),
                usuarioRequest.getCdRoles()
        );
        Usuario usuario = usuarioRepository.save(usuarioMapper.toDomain(requestEncodedPassword));
        return usuarioMapper.toResponse(usuario);
    }
}
