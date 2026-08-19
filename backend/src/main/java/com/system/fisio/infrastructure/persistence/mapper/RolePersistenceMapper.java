package com.system.fisio.infrastructure.persistence.mapper;

import com.system.fisio.domain.enums.AtivoInativoEnum;
import com.system.fisio.domain.model.Permissao;
import com.system.fisio.domain.model.Role;
import com.system.fisio.infrastructure.persistence.entity.RoleEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RolePersistenceMapper {

    private final PermissaoPersistenceMapper permissaoMapper;

    public RolePersistenceMapper(PermissaoPersistenceMapper permissaoMapper) {
        this.permissaoMapper = permissaoMapper;
    }

    public RoleEntity toEntity(Role role) {
        RoleEntity entity = new RoleEntity();
        entity.setCdRole(role.getCdRole());
        entity.setNmRole(role.getNmRole());
        entity.setDsRole(role.getDsRole());
        entity.setStRole(role.getStRole().getCodigo());
        entity.setFlSistema(role.isFlSistema());
        entity.setDtCadastro(role.getDataCadastro());
        entity.setPermissoes(role.getPermissoes().stream()
                .map(permissaoMapper::toEntity)
                .collect(Collectors.toSet()));
        return entity;
    }

    public Role toDomain(RoleEntity entity) {
        Set<Permissao> permissoes = entity.getPermissoes().stream()
                .map(permissaoMapper::toDomain)
                .collect(Collectors.toSet());
        return new Role(
                entity.getCdRole(),
                entity.getNmRole(),
                entity.getDsRole(),
                AtivoInativoEnum.fromCodigo(entity.getStRole()),
                entity.isFlSistema(),
                permissoes,
                entity.getDtCadastro()
        );
    }
}
