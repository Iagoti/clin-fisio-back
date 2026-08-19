package com.system.fisio.infrastructure.persistence.mapper;

import com.system.fisio.domain.enums.StatusPacoteEnum;
import com.system.fisio.domain.model.PacoteFisioterapia;
import com.system.fisio.infrastructure.persistence.entity.PacoteFisioterapiaEntity;
import org.springframework.stereotype.Component;

@Component
public class PacoteFisioterapiaPersistenceMapper {

    public PacoteFisioterapiaEntity toEntity(PacoteFisioterapia pacote) {
        PacoteFisioterapiaEntity entity = new PacoteFisioterapiaEntity();
        entity.setCdPacote(pacote.getCdPacote());
        entity.setCdPaciente(pacote.getCdPaciente());
        entity.setQtSessoesTotal(pacote.getQtSessoesTotal());
        entity.setQtSessoesConsumidas(pacote.getQtSessoesConsumidas());
        entity.setValor(pacote.getValor());
        entity.setDtInicio(pacote.getDtInicio());
        entity.setDtConclusao(pacote.getDtConclusao());
        entity.setStatus(pacote.getStatus().getCodigo());
        entity.setDtCadastro(pacote.getDtCadastro());
        return entity;
    }

    public PacoteFisioterapia toDomain(PacoteFisioterapiaEntity entity) {
        return new PacoteFisioterapia(
                entity.getCdPacote(),
                entity.getCdPaciente(),
                entity.getQtSessoesTotal(),
                entity.getQtSessoesConsumidas(),
                entity.getValor(),
                entity.getDtInicio(),
                entity.getDtConclusao(),
                StatusPacoteEnum.fromCodigo(entity.getStatus()),
                entity.getDtCadastro()
        );
    }
}
