package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.DespesaRequest;
import com.system.fisio.application.dto.DespesaResponse;
import com.system.fisio.application.mapper.DespesaMapper;
import com.system.fisio.domain.exception.CategoriaDespesaException;
import com.system.fisio.domain.exception.DespesaException;
import com.system.fisio.domain.model.Despesa;
import com.system.fisio.domain.ports.ICategoriaDespesaRepository;
import com.system.fisio.domain.ports.IDespesaRepository;
import org.springframework.stereotype.Component;

@Component
public class AtualizarDespesaUseCase {

    private final IDespesaRepository despesaRepository;
    private final ICategoriaDespesaRepository categoriaDespesaRepository;
    private final DespesaMapper mapper;

    public AtualizarDespesaUseCase(IDespesaRepository despesaRepository, ICategoriaDespesaRepository categoriaDespesaRepository, DespesaMapper mapper) {
        this.despesaRepository = despesaRepository;
        this.categoriaDespesaRepository = categoriaDespesaRepository;
        this.mapper = mapper;
    }

    public DespesaResponse execute(DespesaRequest request) {
        if (request.getCdDespesa() == null) {
            throw new DespesaException("Código da despesa é obrigatório para atualização");
        }
        Despesa atual = despesaRepository.findById(request.getCdDespesa())
                .orElseThrow(() -> new DespesaException("Despesa não encontrada"));
        atual.atualizarDados(request.getDescricao(), request.getValor(), request.getDtVencimento(), request.getObservacoes());

        var categoria = categoriaDespesaRepository.findById(request.getCdCategoriaDespesa())
                .orElseThrow(() -> new CategoriaDespesaException("Categoria não encontrada"));

        Despesa salva = despesaRepository.save(atual);
        return mapper.toResponse(salva, categoria.getNmCategoria());
    }
}
