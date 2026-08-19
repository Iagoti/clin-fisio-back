package com.system.fisio.infrastructure.persistence.mapper;

import com.system.fisio.domain.model.Permissao;
import com.system.fisio.infrastructure.persistence.entity.PermissaoEntity;
import org.springframework.stereotype.Component;

@Component
public class PermissaoPersistenceMapper {

    public PermissaoEntity toEntity(Permissao permissao) {
        PermissaoEntity entity = new PermissaoEntity();
        entity.setCdPermissao(permissao.getCdPermissao());
        entity.setCdChave(permissao.getCdChave());
        entity.setDsPermissao(permissao.getDsPermissao());
        entity.setNmModulo(permissao.getNmModulo());
        return entity;
    }

    public Permissao toDomain(PermissaoEntity entity) {
        return new Permissao(entity.getCdPermissao(), entity.getCdChave(), entity.getDsPermissao(), entity.getNmModulo());
    }
}
