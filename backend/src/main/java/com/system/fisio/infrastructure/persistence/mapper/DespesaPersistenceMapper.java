package com.system.fisio.infrastructure.persistence.mapper;

import com.system.fisio.domain.enums.StatusDespesaEnum;
import com.system.fisio.domain.model.Despesa;
import com.system.fisio.infrastructure.persistence.entity.DespesaEntity;
import org.springframework.stereotype.Component;

@Component
public class DespesaPersistenceMapper {

    public DespesaEntity toEntity(Despesa despesa) {
        DespesaEntity entity = new DespesaEntity();
        entity.setCdDespesa(despesa.getCdDespesa());
        entity.setCdCategoriaDespesa(despesa.getCdCategoriaDespesa());
        entity.setDescricao(despesa.getDescricao());
        entity.setValor(despesa.getValor());
        entity.setStatus(despesa.getStatus().getCodigo());
        entity.setDtVencimento(despesa.getDtVencimento());
        entity.setDtPagamento(despesa.getDtPagamento());
        entity.setObservacoes(despesa.getObservacoes());
        entity.setDtCadastro(despesa.getDtCadastro());
        return entity;
    }

    public Despesa toDomain(DespesaEntity entity) {
        return new Despesa(
                entity.getCdDespesa(),
                entity.getCdCategoriaDespesa(),
                entity.getDescricao(),
                entity.getValor(),
                StatusDespesaEnum.fromCodigo(entity.getStatus()),
                entity.getDtVencimento(),
                entity.getDtPagamento(),
                entity.getObservacoes(),
                entity.getDtCadastro()
        );
    }
}
