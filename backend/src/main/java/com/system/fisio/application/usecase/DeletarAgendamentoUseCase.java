package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.DeleteAgendamentoResponse;
import com.system.fisio.domain.exception.AgendamentoException;
import com.system.fisio.domain.ports.IAgendamentoRepository;
import org.springframework.stereotype.Component;

@Component
public class DeletarAgendamentoUseCase {

    private final IAgendamentoRepository agendamentoRepository;

    public DeletarAgendamentoUseCase(IAgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    public DeleteAgendamentoResponse execute(Integer cdAgendamento) {
        agendamentoRepository.findById(cdAgendamento)
                .orElseThrow(() -> new AgendamentoException("Agendamento não encontrado"));
        agendamentoRepository.deleteById(cdAgendamento);
        return new DeleteAgendamentoResponse(cdAgendamento, "Agendamento excluído com sucesso");
    }
}
