package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.RoleRequest;
import com.system.fisio.application.dto.RoleResponse;
import com.system.fisio.application.mapper.RoleMapper;
import com.system.fisio.domain.exception.RoleException;
import com.system.fisio.domain.model.Permissao;
import com.system.fisio.domain.model.Role;
import com.system.fisio.domain.ports.IPermissaoRepository;
import com.system.fisio.domain.ports.IRoleRepository;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CriarRoleUseCase {

    private final IRoleRepository roleRepository;
    private final IPermissaoRepository permissaoRepository;
    private final RoleMapper roleMapper;

    public CriarRoleUseCase(IRoleRepository roleRepository, IPermissaoRepository permissaoRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.permissaoRepository = permissaoRepository;
        this.roleMapper = roleMapper;
    }

    public RoleResponse execute(RoleRequest request) {
        if (roleRepository.findByNome(request.getNmRole()).isPresent()) {
            throw new RoleException("Já existe um perfil com esse nome");
        }
        Set<Permissao> permissoes = resolvePermissoes(request.getCdPermissoes());
        Role role = new Role(null, request.getNmRole(), request.getDsRole(), request.getStRole(), false, permissoes, null);
        Role salvo = roleRepository.save(role);
        return roleMapper.toResponse(salvo);
    }

    private Set<Permissao> resolvePermissoes(Set<Integer> cdPermissoes) {
        if (cdPermissoes == null || cdPermissoes.isEmpty()) {
            return Set.of();
        }
        return cdPermissoes.stream()
                .map(cd -> permissaoRepository.findById(cd)
                        .orElseThrow(() -> new RoleException("Permissão não encontrada: " + cd)))
                .collect(Collectors.toSet());
    }
}
