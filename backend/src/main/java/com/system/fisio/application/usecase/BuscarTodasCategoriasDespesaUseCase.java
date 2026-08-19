package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.CategoriaDespesaResponse;
import com.system.fisio.application.mapper.CategoriaDespesaMapper;
import com.system.fisio.domain.ports.ICategoriaDespesaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuscarTodasCategoriasDespesaUseCase {

    private final ICategoriaDespesaRepository categoriaDespesaRepository;
    private final CategoriaDespesaMapper mapper;

    public BuscarTodasCategoriasDespesaUseCase(ICategoriaDespesaRepository categoriaDespesaRepository, CategoriaDespesaMapper mapper) {
        this.categoriaDespesaRepository = categoriaDespesaRepository;
        this.mapper = mapper;
    }

    public List<CategoriaDespesaResponse> execute() {
        return categoriaDespesaRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }
}
