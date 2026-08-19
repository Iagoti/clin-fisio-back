package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.AgendamentoResponse;
import com.system.fisio.application.mapper.AgendamentoMapper;
import com.system.fisio.domain.exception.AgendamentoException;
import com.system.fisio.domain.ports.IAgendamentoRepository;
import com.system.fisio.domain.ports.IPacienteRepository;
import org.springframework.stereotype.Component;

@Component
public class BuscarAgendamentoByIdUseCase {

    private final IAgendamentoRepository agendamentoRepository;
    private final IPacienteRepository pacienteRepository;
    private final AgendamentoMapper agendamentoMapper;

    public BuscarAgendamentoByIdUseCase(
            IAgendamentoRepository agendamentoRepository,
            IPacienteRepository pacienteRepository,
            AgendamentoMapper agendamentoMapper
    ) {
        this.agendamentoRepository = agendamentoRepository;
        this.pacienteRepository = pacienteRepository;
        this.agendamentoMapper = agendamentoMapper;
    }

    public AgendamentoResponse execute(Integer cdAgendamento) {
        var agendamento = agendamentoRepository.findById(cdAgendamento)
                .orElseThrow(() -> new AgendamentoException("Agendamento não encontrado"));
        String nmPaciente = pacienteRepository.findById(agendamento.getCdPaciente())
                .map(paciente -> paciente.getNmPaciente())
                .orElse(null);
        return agendamentoMapper.toResponse(agendamento, nmPaciente);
    }
}
