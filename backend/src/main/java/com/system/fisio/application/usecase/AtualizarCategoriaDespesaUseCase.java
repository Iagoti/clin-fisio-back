package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.CategoriaDespesaRequest;
import com.system.fisio.application.dto.CategoriaDespesaResponse;
import com.system.fisio.application.mapper.CategoriaDespesaMapper;
import com.system.fisio.domain.exception.CategoriaDespesaException;
import com.system.fisio.domain.model.CategoriaDespesa;
import com.system.fisio.domain.ports.ICategoriaDespesaRepository;
import org.springframework.stereotype.Component;

@Component
public class AtualizarCategoriaDespesaUseCase {

    private final ICategoriaDespesaRepository categoriaDespesaRepository;
    private final CategoriaDespesaMapper mapper;

    public AtualizarCategoriaDespesaUseCase(ICategoriaDespesaRepository categoriaDespesaRepository, CategoriaDespesaMapper mapper) {
        this.categoriaDespesaRepository = categoriaDespesaRepository;
        this.mapper = mapper;
    }

    public CategoriaDespesaResponse execute(CategoriaDespesaRequest request) {
        if (request.getCdCategoriaDespesa() == null) {
            throw new CategoriaDespesaException("Código da categoria é obrigatório para atualização");
        }
        CategoriaDespesa atual = categoriaDespesaRepository.findById(request.getCdCategoriaDespesa())
                .orElseThrow(() -> new CategoriaDespesaException("Categoria não encontrada"));
        atual.atualizarDados(request.getNmCategoria(), request.getStCategoria());
        CategoriaDespesa salva = categoriaDespesaRepository.save(atual);
        return mapper.toResponse(salva);
    }
}
