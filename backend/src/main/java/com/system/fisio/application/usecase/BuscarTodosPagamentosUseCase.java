package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.PagamentoFiltro;
import com.system.fisio.application.dto.PagamentoResponse;
import com.system.fisio.domain.ports.IPagamentoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuscarTodosPagamentosUseCase {

    private final IPagamentoRepository pagamentoRepository;

    public BuscarTodosPagamentosUseCase(IPagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public List<PagamentoResponse> execute(PagamentoFiltro filtro) {
        return pagamentoRepository.findAllByFiltro(filtro);
    }
}
