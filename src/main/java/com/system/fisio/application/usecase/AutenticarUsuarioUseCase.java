package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.LoginRequest;
import com.system.fisio.application.dto.LoginResponse;
import com.system.fisio.application.ports.ITokenService;
import com.system.fisio.infrastructure.security.JwtService;
import com.system.fisio.domain.exception.UsuarioException;
import com.system.fisio.domain.model.Usuario;
import com.system.fisio.domain.ports.IUsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AutenticarUsuarioUseCase {

    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ITokenService jwtService;

    public AutenticarUsuarioUseCase(IUsuarioRepository usuarioRepository,
                                    PasswordEncoder passwordEncoder,
                                    JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse execute(LoginRequest req) {
        Usuario usuario = usuarioRepository.findByEmail(req.email())
                .orElseThrow(() -> new UsuarioException("Login ou senha inválidos"));

        usuario.validarUsuarioAtivo();
        if (!passwordEncoder.matches(req.senha(), usuario.getSenha())) {
            throw new UsuarioException("Login ou senha inválidos");
        }
        Integer tpUsuario = usuario.getTpUsuario().getCodigo();
        String token = jwtService.gerarToken(
                usuario.getCdUsuario(),
                usuario.getLogin(),
                tpUsuario
        );
        return new LoginResponse(token, usuario.getTpUsuario().getCodigo(), usuario.getCdUsuario(), usuario.getNmUsuario());
    }

}
