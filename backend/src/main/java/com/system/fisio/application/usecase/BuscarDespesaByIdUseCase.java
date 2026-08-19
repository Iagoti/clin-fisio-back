package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.DespesaResponse;
import com.system.fisio.application.mapper.DespesaMapper;
import com.system.fisio.domain.exception.CategoriaDespesaException;
import com.system.fisio.domain.exception.DespesaException;
import com.system.fisio.domain.ports.ICategoriaDespesaRepository;
import com.system.fisio.domain.ports.IDespesaRepository;
import org.springframework.stereotype.Component;

@Component
public class BuscarDespesaByIdUseCase {

    private final IDespesaRepository despesaRepository;
    private final ICategoriaDespesaRepository categoriaDespesaRepository;
    private final DespesaMapper mapper;

    public BuscarDespesaByIdUseCase(IDespesaRepository despesaRepository, ICategoriaDespesaRepository categoriaDespesaRepository, DespesaMapper mapper) {
        this.despesaRepository = despesaRepository;
        this.categoriaDespesaRepository = categoriaDespesaRepository;
        this.mapper = mapper;
    }

    public DespesaResponse execute(Integer cdDespesa) {
        var despesa = despesaRepository.findById(cdDespesa)
                .orElseThrow(() -> new DespesaException("Despesa não encontrada"));
        var categoria = categoriaDespesaRepository.findById(despesa.getCdCategoriaDespesa())
                .orElseThrow(() -> new CategoriaDespesaException("Categoria não encontrada"));
        return mapper.toResponse(despesa, categoria.getNmCategoria());
    }
}
