package com.system.fisio.infrastructure.persistence.mapper;

import com.system.fisio.domain.enums.AtivoInativoEnum;
import com.system.fisio.domain.model.CategoriaDespesa;
import com.system.fisio.infrastructure.persistence.entity.CategoriaDespesaEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoriaDespesaPersistenceMapper {

    public CategoriaDespesaEntity toEntity(CategoriaDespesa categoria) {
        CategoriaDespesaEntity entity = new CategoriaDespesaEntity();
        entity.setCdCategoriaDespesa(categoria.getCdCategoriaDespesa());
        entity.setNmCategoria(categoria.getNmCategoria());
        entity.setStCategoria(categoria.getStCategoria().getCodigo());
        entity.setDtCadastro(categoria.getDtCadastro());
        return entity;
    }

    public CategoriaDespesa toDomain(CategoriaDespesaEntity entity) {
        return new CategoriaDespesa(
                entity.getCdCategoriaDespesa(),
                entity.getNmCategoria(),
                AtivoInativoEnum.fromCodigo(entity.getStCategoria()),
                entity.getDtCadastro()
        );
    }
}
