package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.RoleResponse;
import com.system.fisio.application.mapper.RoleMapper;
import com.system.fisio.domain.ports.IRoleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuscarTodasRolesUseCase {

    private final IRoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public BuscarTodasRolesUseCase(IRoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public List<RoleResponse> execute() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toResponse)
                .toList();
    }
}
