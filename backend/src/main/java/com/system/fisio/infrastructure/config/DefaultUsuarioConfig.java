package com.system.fisio.infrastructure.config;

import com.system.fisio.domain.enums.AtivoInativoEnum;
import com.system.fisio.domain.enums.TipoUsuario;
import com.system.fisio.infrastructure.persistence.entity.UsuarioEntity;
import com.system.fisio.infrastructure.persistence.repository.UsuarioJpaRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DefaultUsuarioConfig {

    @Bean
    public CommandLineRunner criarUsuarioPadrao(
            UsuarioJpaRepository usuarioJpaRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.default-user.enabled:true}") boolean enabled,
            @Value("${app.default-user.name:Administrador}") String nome,
            @Value("${app.default-user.email:admin@clin-fisio.com}") String email,
            @Value("${app.default-user.login:admin}") String login,
            @Value("${app.default-user.password:admin123}") String senha
    ) {
        return args -> {
            if (!enabled || usuarioJpaRepository.findByEmail(email).isPresent()) {
                return;
            }

            UsuarioEntity usuarioPadrao = new UsuarioEntity(
                    null,
                    nome,
                    email,
                    login,
                    passwordEncoder.encode(senha),
                    TipoUsuario.ADM.getCodigo(),
                    AtivoInativoEnum.ATIVO.getCodigo()
            );
            usuarioPadrao.setDtCadastro(LocalDateTime.now());

            usuarioJpaRepository.save(usuarioPadrao);
        };
    }
}
