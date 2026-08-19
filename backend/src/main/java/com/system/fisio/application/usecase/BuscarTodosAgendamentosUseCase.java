package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.AgendamentoFiltro;
import com.system.fisio.application.dto.AgendamentoResponse;
import com.system.fisio.domain.ports.IAgendamentoRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BuscarTodosAgendamentosUseCase {

    private final IAgendamentoRepository agendamentoRepository;

    public BuscarTodosAgendamentosUseCase(IAgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    public List<AgendamentoResponse> execute(AgendamentoFiltro filtro) {
        return agendamentoRepository.findAllByFiltro(filtro);
    }
}
