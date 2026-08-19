package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.RoleResponse;
import com.system.fisio.application.mapper.RoleMapper;
import com.system.fisio.domain.exception.RoleException;
import com.system.fisio.domain.ports.IRoleRepository;
import org.springframework.stereotype.Component;

@Component
public class BuscarRoleByIdUseCase {

    private final IRoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public BuscarRoleByIdUseCase(IRoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public RoleResponse execute(Integer cdRole) {
        return roleRepository.findById(cdRole)
                .map(roleMapper::toResponse)
                .orElseThrow(() -> new RoleException("Perfil não encontrado"));
    }
}
