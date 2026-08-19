package com.system.fisio.infrastructure.config;

import com.system.fisio.domain.enums.AtivoInativoEnum;
import com.system.fisio.infrastructure.persistence.entity.UsuarioEntity;
import com.system.fisio.infrastructure.persistence.repository.RoleJpaRepository;
import com.system.fisio.infrastructure.persistence.repository.UsuarioJpaRepository;
import java.time.LocalDateTime;
import java.util.Set;
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
            RoleJpaRepository roleJpaRepository,
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

            // A role ADMINISTRADOR é semeada pela migration V3 (db/migration) — se ela
            // não existir, algo na ordem de inicialização (Flyway antes do JPA) está
            // errado, então falhar alto aqui é melhor que criar um admin sem acesso.
            var roleAdministrador = roleJpaRepository.findByNmRole("ADMINISTRADOR")
                    .orElseThrow(() -> new IllegalStateException(
                            "Role ADMINISTRADOR não encontrada — a migration V3 (seed RBAC) precisa rodar antes deste seed de usuário."));

            UsuarioEntity usuarioPadrao = new UsuarioEntity(
                    null,
                    nome,
                    email,
                    login,
                    passwordEncoder.encode(senha),
                    AtivoInativoEnum.ATIVO.getCodigo()
            );
            usuarioPadrao.setDtCadastro(LocalDateTime.now());
            usuarioPadrao.setRoles(Set.of(roleAdministrador));

            usuarioJpaRepository.save(usuarioPadrao);
        };
    }
}
