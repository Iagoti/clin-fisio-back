package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.DespesaRequest;
import com.system.fisio.application.dto.DespesaResponse;
import com.system.fisio.application.mapper.DespesaMapper;
import com.system.fisio.domain.exception.CategoriaDespesaException;
import com.system.fisio.domain.model.Despesa;
import com.system.fisio.domain.ports.ICategoriaDespesaRepository;
import com.system.fisio.domain.ports.IDespesaRepository;
import org.springframework.stereotype.Component;

@Component
public class CriarDespesaUseCase {

    private final IDespesaRepository despesaRepository;
    private final ICategoriaDespesaRepository categoriaDespesaRepository;
    private final DespesaMapper mapper;

    public CriarDespesaUseCase(IDespesaRepository despesaRepository, ICategoriaDespesaRepository categoriaDespesaRepository, DespesaMapper mapper) {
        this.despesaRepository = despesaRepository;
        this.categoriaDespesaRepository = categoriaDespesaRepository;
        this.mapper = mapper;
    }

    public DespesaResponse execute(DespesaRequest request) {
        var categoria = categoriaDespesaRepository.findById(request.getCdCategoriaDespesa())
                .orElseThrow(() -> new CategoriaDespesaException("Categoria não encontrada"));
        Despesa despesa = despesaRepository.save(mapper.toDomain(request));
        return mapper.toResponse(despesa, categoria.getNmCategoria());
    }
}
