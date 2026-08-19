package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.AgendamentoResponse;
import com.system.fisio.application.dto.CriarReposicaoRequest;
import com.system.fisio.application.mapper.AgendamentoMapper;
import com.system.fisio.domain.enums.StatusAgendamentoEnum;
import com.system.fisio.domain.exception.AgendamentoException;
import com.system.fisio.domain.exception.PacienteException;
import com.system.fisio.domain.model.Agendamento;
import com.system.fisio.domain.ports.IAgendamentoRepository;
import com.system.fisio.domain.ports.IPacienteRepository;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

/**
 * Cria uma reposição a partir de um agendamento marcado como falta. Regra de
 * negócio: 1 reposição gratuita por mês por paciente quando a falta não tem
 * atestado médico; ilimitado quando há atestado (ver Agendamento.marcarFalta).
 * Reposição não gera novo Pagamento nem consome nova sessão do pacote — o crédito
 * já foi pago/alocado na sessão original perdida.
 */
@Component
public class CriarReposicaoUseCase {

    private final IAgendamentoRepository agendamentoRepository;
    private final IPacienteRepository pacienteRepository;
    private final AgendamentoMapper agendamentoMapper;

    public CriarReposicaoUseCase(
            IAgendamentoRepository agendamentoRepository,
            IPacienteRepository pacienteRepository,
            AgendamentoMapper agendamentoMapper
    ) {
        this.agendamentoRepository = agendamentoRepository;
        this.pacienteRepository = pacienteRepository;
        this.agendamentoMapper = agendamentoMapper;
    }

    public AgendamentoResponse execute(CriarReposicaoRequest request) {
        Agendamento origem = agendamentoRepository.findById(request.getCdAgendamentoOrigemFalta())
                .orElseThrow(() -> new AgendamentoException("Agendamento de origem não encontrado"));

        if (origem.getStatus() != StatusAgendamentoEnum.FALTOU) {
            throw new AgendamentoException("Só é possível criar reposição a partir de um agendamento marcado como falta");
        }

        if (!origem.isComAtestado()) {
            validarLimiteMensalSemAtestado(origem);
        }

        if (agendamentoRepository.existeConflito(
                request.getDataAgendamento(),
                request.getHoraAgendamento(),
                origem.getTipoAtendimento(),
                origem.getCdPaciente(),
                null
        )) {
            throw new AgendamentoException("Já existe um agendamento nesse dia e horário.");
        }

        Agendamento reposicao = new Agendamento(
                null,
                origem.getCdPaciente(),
                origem.getTipoAtendimento(),
                request.getDataAgendamento(),
                request.getHoraAgendamento(),
                StatusAgendamentoEnum.AGENDADO,
                null,
                request.getObservacoes(),
                null
        );
        reposicao.vincularOrigemFalta(origem.getCdAgendamento());
        if (origem.getCdPacote() != null) {
            reposicao.vincularPacote(origem.getCdPacote());
        }

        Agendamento salvo = agendamentoRepository.save(reposicao);
        var paciente = pacienteRepository.findById(salvo.getCdPaciente())
                .orElseThrow(() -> new PacienteException("Paciente não encontrado"));
        return agendamentoMapper.toResponse(salvo, paciente.getNmPaciente());
    }

    private void validarLimiteMensalSemAtestado(Agendamento origem) {
        YearMonth mesReferencia = YearMonth.from(origem.getDataAgendamento());
        long reposicoesNoMes = agendamentoRepository.contarReposicoesSemAtestadoNoMes(
                origem.getCdPaciente(), mesReferencia.atDay(1), mesReferencia.atEndOfMonth()
        );
        if (reposicoesNoMes >= 1) {
            throw new AgendamentoException(
                    "Paciente já utilizou a reposição gratuita deste mês. Reposições adicionais exigem atestado médico na falta de origem.");
        }
    }
}
