package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.DespesaFiltro;
import com.system.fisio.application.dto.DespesaResponse;
import com.system.fisio.domain.ports.IDespesaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuscarTodasDespesasUseCase {

    private final IDespesaRepository despesaRepository;

    public BuscarTodasDespesasUseCase(IDespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    public List<DespesaResponse> execute(DespesaFiltro filtro) {
        return despesaRepository.findAllByFiltro(filtro);
    }
}
