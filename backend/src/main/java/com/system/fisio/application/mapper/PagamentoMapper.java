package com.system.fisio.application.mapper;

import com.system.fisio.application.dto.PagamentoRequest;
import com.system.fisio.application.dto.PagamentoResponse;
import com.system.fisio.domain.enums.StatusPagamentoEnum;
import com.system.fisio.domain.model.Pagamento;
import org.springframework.stereotype.Component;

@Component
public class PagamentoMapper {

    public Pagamento toDomain(PagamentoRequest request) {
        return new Pagamento(
                request.getCdPagamento(),
                request.getCdAgendamento(),
                request.getCdPacote(),
                request.getCdPaciente(),
                request.getValor(),
                request.getFormaPagamento(),
                request.getStatus() != null ? request.getStatus() : StatusPagamentoEnum.PENDENTE,
                request.getDtVencimento(),
                null,
                request.getObservacoes(),
                null
        );
    }

    public PagamentoResponse toResponse(Pagamento pagamento, String nmPaciente) {
        return new PagamentoResponse(
                pagamento.getCdPagamento(),
                pagamento.getCdAgendamento(),
                pagamento.getCdPacote(),
                pagamento.getCdPaciente(),
                nmPaciente,
                pagamento.getValor(),
                pagamento.getFormaPagamento(),
                pagamento.getStatus(),
                pagamento.getDtVencimento(),
                pagamento.getDtPagamento(),
                pagamento.getObservacoes(),
                pagamento.getDtCadastro()
        );
    }
}
