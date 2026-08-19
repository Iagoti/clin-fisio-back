package com.system.fisio.domain.ports;

import com.system.fisio.application.dto.PagamentoFiltro;
import com.system.fisio.application.dto.PagamentoResponse;
import com.system.fisio.domain.model.Pagamento;

import java.util.List;
import java.util.Optional;

public interface IPagamentoRepository {
    Pagamento save(Pagamento pagamento);
    Optional<Pagamento> findById(Integer cdPagamento);
    List<PagamentoResponse> findAllByFiltro(PagamentoFiltro filtro);
}
