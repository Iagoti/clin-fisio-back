package com.system.fisio.infrastructure.persistence.mapper;

import com.system.fisio.domain.enums.StatusAgendamentoEnum;
import com.system.fisio.domain.enums.TipoAtendimentoEnum;
import com.system.fisio.domain.model.Agendamento;
import com.system.fisio.infrastructure.persistence.entity.AgendamentoEntity;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoPersistenceMapper {

    public AgendamentoEntity toEntity(Agendamento agendamento) {
        AgendamentoEntity entity = new AgendamentoEntity();
        entity.setCdAgendamento(agendamento.getCdAgendamento());
        entity.setCdPaciente(agendamento.getCdPaciente());
        entity.setTipoAtendimento(agendamento.getTipoAtendimento().getCodigo());
        entity.setDataAgendamento(agendamento.getDataAgendamento());
        entity.setHoraAgendamento(agendamento.getHoraAgendamento());
        entity.setStatus(agendamento.getStatus().getCodigo());
        entity.setValor(agendamento.getValor());
        entity.setObservacoes(agendamento.getObservacoes());
        entity.setDtCadastro(agendamento.getDtCadastro());
        entity.setCdPacote(agendamento.getCdPacote());
        entity.setCdAgendamentoOrigemFalta(agendamento.getCdAgendamentoOrigemFalta());
        entity.setComAtestado(agendamento.isComAtestado());
        entity.setArquivoAtestadoDados(agendamento.getArquivoAtestadoDados());
        entity.setNomeArquivoAtestado(agendamento.getNomeArquivoAtestado());
        entity.setTipoArquivoAtestado(agendamento.getTipoArquivoAtestado());
        return entity;
    }

    public Agendamento toDomain(AgendamentoEntity entity) {
        return new Agendamento(
                entity.getCdAgendamento(),
                entity.getCdPaciente(),
                TipoAtendimentoEnum.fromCodigo(entity.getTipoAtendimento()),
                entity.getDataAgendamento(),
                entity.getHoraAgendamento(),
                StatusAgendamentoEnum.fromCodigo(entity.getStatus()),
                entity.getValor(),
                entity.getObservacoes(),
                entity.getDtCadastro(),
                entity.getCdPacote(),
                entity.getCdAgendamentoOrigemFalta(),
                entity.isComAtestado(),
                entity.getArquivoAtestadoDados(),
                entity.getNomeArquivoAtestado(),
                entity.getTipoArquivoAtestado()
        );
    }
}
