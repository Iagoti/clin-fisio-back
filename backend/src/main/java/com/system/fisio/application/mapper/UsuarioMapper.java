package com.system.fisio.application.mapper;

import com.system.fisio.domain.exception.RoleException;
import com.system.fisio.domain.exception.UsuarioException;
import com.system.fisio.domain.model.Role;
import com.system.fisio.domain.model.Usuario;
import com.system.fisio.domain.ports.IRoleRepository;
import com.system.fisio.application.dto.RoleResumoResponse;
import com.system.fisio.application.dto.UsuarioRequest;
import com.system.fisio.application.dto.UsuarioResponse;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    private final IRoleRepository roleRepository;

    public UsuarioMapper(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Usuario toDomain(UsuarioRequest usuarioRequest) {
        return new Usuario(
                usuarioRequest.getCdUsuario(),
                usuarioRequest.getNome(),
                usuarioRequest.getEmail(),
                usuarioRequest.getLogin(),
                usuarioRequest.getSenha(),
                resolveRoles(usuarioRequest.getCdRoles()),
                usuarioRequest.getStUsuario()
        );
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        var roles = usuario.getRoles().stream()
                .map(role -> new RoleResumoResponse(role.getCdRole(), role.getNmRole()))
                .sorted(Comparator.comparing(RoleResumoResponse::nmRole))
                .toList();

        return new UsuarioResponse(
                usuario.getCdUsuario(),
                usuario.getNmUsuario(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getStUsuario(),
                roles,
                usuario.getDataCadastro()
        );
    }

    private Set<Role> resolveRoles(Set<Integer> cdRoles) {
        if (cdRoles == null || cdRoles.isEmpty()) {
            throw new UsuarioException("Usuário precisa ter ao menos um perfil de acesso");
        }
        return cdRoles.stream()
                .map(cd -> roleRepository.findById(cd)
                        .orElseThrow(() -> new RoleException("Perfil não encontrado: " + cd)))
                .collect(Collectors.toSet());
    }

}
