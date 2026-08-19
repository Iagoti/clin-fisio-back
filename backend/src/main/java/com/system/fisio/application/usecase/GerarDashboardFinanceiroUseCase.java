package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.DashboardFinanceiroResponse;
import com.system.fisio.domain.ports.IFinanceiroRelatorioRepository;
import org.springframework.stereotype.Component;

@Component
public class GerarDashboardFinanceiroUseCase {

    private final IFinanceiroRelatorioRepository relatorioRepository;

    public GerarDashboardFinanceiroUseCase(IFinanceiroRelatorioRepository relatorioRepository) {
        this.relatorioRepository = relatorioRepository;
    }

    public DashboardFinanceiroResponse execute() {
        return relatorioRepository.gerarDashboard();
    }
}
