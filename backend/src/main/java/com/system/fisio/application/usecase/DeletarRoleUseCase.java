package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.DeleteRoleResponse;
import com.system.fisio.domain.exception.RoleException;
import com.system.fisio.domain.model.Role;
import com.system.fisio.domain.ports.IRoleRepository;
import org.springframework.stereotype.Component;

@Component
public class DeletarRoleUseCase {

    private final IRoleRepository roleRepository;

    public DeletarRoleUseCase(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public DeleteRoleResponse execute(Integer cdRole) {
        Role role = roleRepository.findById(cdRole)
                .orElseThrow(() -> new RoleException("Perfil não encontrado"));

        role.validarNaoSistema();

        if (roleRepository.existeUsuarioVinculado(cdRole)) {
            throw new RoleException("Não é possível excluir um perfil vinculado a usuários");
        }

        roleRepository.deleteById(cdRole);
        return new DeleteRoleResponse(true, "Perfil excluído com sucesso");
    }
}
