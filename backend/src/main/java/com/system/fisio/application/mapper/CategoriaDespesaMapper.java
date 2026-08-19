package com.system.fisio.application.mapper;

import com.system.fisio.application.dto.CategoriaDespesaRequest;
import com.system.fisio.application.dto.CategoriaDespesaResponse;
import com.system.fisio.domain.model.CategoriaDespesa;
import org.springframework.stereotype.Component;

@Component
public class CategoriaDespesaMapper {

    public CategoriaDespesa toDomain(CategoriaDespesaRequest request) {
        return new CategoriaDespesa(
                request.getCdCategoriaDespesa(),
                request.getNmCategoria(),
                request.getStCategoria(),
                null
        );
    }

    public CategoriaDespesaResponse toResponse(CategoriaDespesa categoria) {
        return new CategoriaDespesaResponse(
                categoria.getCdCategoriaDespesa(),
                categoria.getNmCategoria(),
                categoria.getStCategoria(),
                categoria.getDtCadastro()
        );
    }
}
