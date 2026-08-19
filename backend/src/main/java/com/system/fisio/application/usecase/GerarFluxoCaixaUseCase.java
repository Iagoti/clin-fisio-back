package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.FluxoCaixaPontoResponse;
import com.system.fisio.domain.exception.PagamentoException;
import com.system.fisio.domain.ports.IFinanceiroRelatorioRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class GerarFluxoCaixaUseCase {

    private final IFinanceiroRelatorioRepository relatorioRepository;

    public GerarFluxoCaixaUseCase(IFinanceiroRelatorioRepository relatorioRepository) {
        this.relatorioRepository = relatorioRepository;
    }

    public List<FluxoCaixaPontoResponse> execute(LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio == null || dataFim == null) {
            throw new PagamentoException("Data de início e fim são obrigatórias");
        }
        if (dataFim.isBefore(dataInicio)) {
            throw new PagamentoException("Data fim não pode ser anterior à data início");
        }
        return relatorioRepository.gerarFluxoCaixa(dataInicio, dataFim);
    }
}
