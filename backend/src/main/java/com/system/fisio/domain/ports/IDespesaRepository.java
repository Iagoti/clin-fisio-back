package com.system.fisio.domain.ports;

import com.system.fisio.application.dto.DespesaFiltro;
import com.system.fisio.application.dto.DespesaResponse;
import com.system.fisio.domain.model.Despesa;

import java.util.List;
import java.util.Optional;

public interface IDespesaRepository {
    Despesa save(Despesa despesa);
    Optional<Despesa> findById(Integer cdDespesa);
    List<DespesaResponse> findAllByFiltro(DespesaFiltro filtro);
    void deleteById(Integer cdDespesa);
}
