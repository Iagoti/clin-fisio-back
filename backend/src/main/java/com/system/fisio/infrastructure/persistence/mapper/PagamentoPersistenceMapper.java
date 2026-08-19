package com.system.fisio.infrastructure.persistence.mapper;

import com.system.fisio.domain.enums.FormaPagamentoEnum;
import com.system.fisio.domain.enums.StatusPagamentoEnum;
import com.system.fisio.domain.model.Pagamento;
import com.system.fisio.infrastructure.persistence.entity.PagamentoEntity;
import org.springframework.stereotype.Component;

@Component
public class PagamentoPersistenceMapper {

    public PagamentoEntity toEntity(Pagamento pagamento) {
        PagamentoEntity entity = new PagamentoEntity();
        entity.setCdPagamento(pagamento.getCdPagamento());
        entity.setCdAgendamento(pagamento.getCdAgendamento());
        entity.setCdPacote(pagamento.getCdPacote());
        entity.setCdPaciente(pagamento.getCdPaciente());
        entity.setValor(pagamento.getValor());
        entity.setFormaPagamento(pagamento.getFormaPagamento() != null ? pagamento.getFormaPagamento().getCodigo() : null);
        entity.setStatus(pagamento.getStatus().getCodigo());
        entity.setDtVencimento(pagamento.getDtVencimento());
        entity.setDtPagamento(pagamento.getDtPagamento());
        entity.setObservacoes(pagamento.getObservacoes());
        entity.setDtCadastro(pagamento.getDtCadastro());
        return entity;
    }

    public Pagamento toDomain(PagamentoEntity entity) {
        return new Pagamento(
                entity.getCdPagamento(),
                entity.getCdAgendamento(),
                entity.getCdPacote(),
                entity.getCdPaciente(),
                entity.getValor(),
                FormaPagamentoEnum.fromCodigo(entity.getFormaPagamento()),
                StatusPagamentoEnum.fromCodigo(entity.getStatus()),
                entity.getDtVencimento(),
                entity.getDtPagamento(),
                entity.getObservacoes(),
                entity.getDtCadastro()
        );
    }
}
