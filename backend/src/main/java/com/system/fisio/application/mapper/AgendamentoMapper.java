package com.system.fisio.application.mapper;

import com.system.fisio.application.dto.AgendamentoRequest;
import com.system.fisio.application.dto.AgendamentoResponse;
import com.system.fisio.domain.model.Agendamento;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoMapper {

    public Agendamento toDomain(AgendamentoRequest request) {
        return new Agendamento(
                request.getCdAgendamento(),
                request.getCdPaciente(),
                request.getTipoAtendimento(),
                request.getDataAgendamento(),
                request.getHoraAgendamento(),
                request.getStatus(),
                request.getValor(),
                request.getObservacoes(),
                request.getDtCadastro()
        );
    }

    public AgendamentoResponse toResponse(Agendamento agendamento, String nmPaciente) {
        return new AgendamentoResponse(
                agendamento.getCdAgendamento(),
                agendamento.getCdPaciente(),
                nmPaciente,
                agendamento.getTipoAtendimento(),
                agendamento.getDataAgendamento(),
                agendamento.getHoraAgendamento(),
                agendamento.getStatus(),
                agendamento.getValor(),
                agendamento.getObservacoes(),
                agendamento.getDtCadastro()
        );
    }
}
