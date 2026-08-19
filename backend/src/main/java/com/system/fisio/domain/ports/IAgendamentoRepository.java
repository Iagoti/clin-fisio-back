package com.system.fisio.domain.ports;

import com.system.fisio.application.dto.AgendamentoFiltro;
import com.system.fisio.application.dto.AgendamentoResponse;
import com.system.fisio.domain.enums.TipoAtendimentoEnum;
import com.system.fisio.domain.model.Agendamento;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface IAgendamentoRepository {
    Agendamento save(Agendamento agendamento);
    List<AgendamentoResponse> findAllByFiltro(AgendamentoFiltro filtro);
    Optional<Agendamento> findById(Integer cdAgendamento);
    void deleteById(Integer cdAgendamento);
    boolean existeConflito(
            LocalDate dataAgendamento,
            LocalTime horaAgendamento,
            TipoAtendimentoEnum tipoAtendimento,
            Integer cdPaciente,
            Integer cdAgendamentoIgnorar
    );

    /**
     * Conta reposições (não canceladas) criadas a partir de faltas SEM atestado do
     * paciente, cuja falta de origem caiu no intervalo informado — usada para
     * aplicar o limite de 1 reposição gratuita por mês (ver CriarReposicaoUseCase).
     */
    long contarReposicoesSemAtestadoNoMes(Integer cdPaciente, LocalDate inicioMes, LocalDate fimMes);
}
