package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.AgendamentoRequest;
import com.system.fisio.application.dto.AgendamentoResponse;
import com.system.fisio.application.mapper.AgendamentoMapper;
import com.system.fisio.domain.exception.AgendamentoException;
import com.system.fisio.domain.exception.PacienteException;
import com.system.fisio.domain.model.Agendamento;
import com.system.fisio.domain.ports.IAgendamentoRepository;
import com.system.fisio.domain.ports.IPacienteRepository;
import org.springframework.stereotype.Component;

@Component
public class AtualizarAgendamentoUseCase {

    private final IAgendamentoRepository agendamentoRepository;
    private final IPacienteRepository pacienteRepository;
    private final AgendamentoMapper agendamentoMapper;

    public AtualizarAgendamentoUseCase(
            IAgendamentoRepository agendamentoRepository,
            IPacienteRepository pacienteRepository,
            AgendamentoMapper agendamentoMapper
    ) {
        this.agendamentoRepository = agendamentoRepository;
        this.pacienteRepository = pacienteRepository;
        this.agendamentoMapper = agendamentoMapper;
    }

    public AgendamentoResponse execute(AgendamentoRequest request) {
        if (request.getCdAgendamento() == null) {
            throw new AgendamentoException("Código do agendamento é obrigatório para atualização");
        }
        Agendamento atual = agendamentoRepository.findById(request.getCdAgendamento())
                .orElseThrow(() -> new AgendamentoException("Agendamento não encontrado"));
        request.setDtCadastro(atual.getDtCadastro());

        var paciente = pacienteRepository.findById(request.getCdPaciente())
                .orElseThrow(() -> new PacienteException("Paciente não encontrado"));

        Agendamento agendamentoAtualizado = agendamentoMapper.toDomain(request);
        if (agendamentoRepository.existeConflito(
                agendamentoAtualizado.getDataAgendamento(),
                agendamentoAtualizado.getHoraAgendamento(),
                agendamentoAtualizado.getTipoAtendimento(),
                agendamentoAtualizado.getCdPaciente(),
                agendamentoAtualizado.getCdAgendamento()
        )) {
            throw new AgendamentoException("Já existe um agendamento nesse dia e horário.");
        }

        Agendamento agendamento = agendamentoRepository.save(agendamentoAtualizado);
        return agendamentoMapper.toResponse(agendamento, paciente.getNmPaciente());
    }
}
