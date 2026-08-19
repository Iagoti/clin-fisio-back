package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.CategoriaDespesaRequest;
import com.system.fisio.application.dto.CategoriaDespesaResponse;
import com.system.fisio.application.mapper.CategoriaDespesaMapper;
import com.system.fisio.domain.model.CategoriaDespesa;
import com.system.fisio.domain.ports.ICategoriaDespesaRepository;
import org.springframework.stereotype.Component;

@Component
public class CriarCategoriaDespesaUseCase {

    private final ICategoriaDespesaRepository categoriaDespesaRepository;
    private final CategoriaDespesaMapper mapper;

    public CriarCategoriaDespesaUseCase(ICategoriaDespesaRepository categoriaDespesaRepository, CategoriaDespesaMapper mapper) {
        this.categoriaDespesaRepository = categoriaDespesaRepository;
        this.mapper = mapper;
    }

    public CategoriaDespesaResponse execute(CategoriaDespesaRequest request) {
        CategoriaDespesa categoria = categoriaDespesaRepository.save(mapper.toDomain(request));
        return mapper.toResponse(categoria);
    }
}
