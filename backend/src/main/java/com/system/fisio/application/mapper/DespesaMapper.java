package com.system.fisio.application.mapper;

import com.system.fisio.application.dto.DespesaRequest;
import com.system.fisio.application.dto.DespesaResponse;
import com.system.fisio.domain.model.Despesa;
import org.springframework.stereotype.Component;

@Component
public class DespesaMapper {

    public Despesa toDomain(DespesaRequest request) {
        return new Despesa(
                request.getCdDespesa(),
                request.getCdCategoriaDespesa(),
                request.getDescricao(),
                request.getValor(),
                null,
                request.getDtVencimento(),
                null,
                request.getObservacoes(),
                null
        );
    }

    public DespesaResponse toResponse(Despesa despesa, String nmCategoria) {
        return new DespesaResponse(
                despesa.getCdDespesa(),
                despesa.getCdCategoriaDespesa(),
                nmCategoria,
                despesa.getDescricao(),
                despesa.getValor(),
                despesa.getStatus(),
                despesa.getDtVencimento(),
                despesa.getDtPagamento(),
                despesa.getObservacoes(),
                despesa.getDtCadastro()
        );
    }
}
