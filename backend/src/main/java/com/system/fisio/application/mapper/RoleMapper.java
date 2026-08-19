package com.system.fisio.application.mapper;

import com.system.fisio.application.dto.PermissaoResponse;
import com.system.fisio.application.dto.RoleResponse;
import com.system.fisio.domain.model.Role;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class RoleMapper {

    public RoleResponse toResponse(Role role) {
        List<PermissaoResponse> permissoes = role.getPermissoes().stream()
                .map(p -> new PermissaoResponse(p.getCdPermissao(), p.getCdChave(), p.getDsPermissao(), p.getNmModulo()))
                .sorted(Comparator.comparing(PermissaoResponse::cdChave))
                .toList();

        return new RoleResponse(
                role.getCdRole(),
                role.getNmRole(),
                role.getDsRole(),
                role.getStRole(),
                role.isFlSistema(),
                permissoes,
                role.getDataCadastro()
        );
    }
}
