package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.AgendamentoRequest;
import com.system.fisio.application.dto.AgendamentoResponse;
import com.system.fisio.application.mapper.AgendamentoMapper;
import com.system.fisio.domain.enums.FormaPagamentoEnum;
import com.system.fisio.domain.enums.StatusPagamentoEnum;
import com.system.fisio.domain.enums.TipoAtendimentoEnum;
import com.system.fisio.domain.exception.AgendamentoException;
import com.system.fisio.domain.exception.PacienteException;
import com.system.fisio.domain.model.Agendamento;
import com.system.fisio.domain.model.PacoteFisioterapia;
import com.system.fisio.domain.model.Pagamento;
import com.system.fisio.domain.ports.IAgendamentoRepository;
import com.system.fisio.domain.ports.IPacienteRepository;
import com.system.fisio.domain.ports.IPacoteFisioterapiaRepository;
import com.system.fisio.domain.ports.IPagamentoRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CriarAgendamentoUseCase {

    /** Valor fixo da sessão avulsa de pilates (regra de negócio, não editável pelo usuário). */
    private static final BigDecimal VALOR_SESSAO_PILATES = new BigDecimal("100.00");

    private final IAgendamentoRepository agendamentoRepository;
    private final IPacienteRepository pacienteRepository;
    private final IPacoteFisioterapiaRepository pacoteRepository;
    private final IPagamentoRepository pagamentoRepository;
    private final AgendamentoMapper agendamentoMapper;

    public CriarAgendamentoUseCase(
            IAgendamentoRepository agendamentoRepository,
            IPacienteRepository pacienteRepository,
            IPacoteFisioterapiaRepository pacoteRepository,
            IPagamentoRepository pagamentoRepository,
            AgendamentoMapper agendamentoMapper
    ) {
        this.agendamentoRepository = agendamentoRepository;
        this.pacienteRepository = pacienteRepository;
        this.pacoteRepository = pacoteRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.agendamentoMapper = agendamentoMapper;
    }

    public AgendamentoResponse execute(AgendamentoRequest request) {
        var paciente = pacienteRepository.findById(request.getCdPaciente())
                .orElseThrow(() -> new PacienteException("Paciente não encontrado"));

        Agendamento novoAgendamento = agendamentoMapper.toDomain(request);
        if (agendamentoRepository.existeConflito(
                novoAgendamento.getDataAgendamento(),
                novoAgendamento.getHoraAgendamento(),
                novoAgendamento.getTipoAtendimento(),
                novoAgendamento.getCdPaciente(),
                null
        )) {
            throw new AgendamentoException("Já existe um agendamento nesse dia e horário.");
        }

        // Fisioterapia é vendida em pacotes de 10 sessões (cobrados de uma vez ao
        // iniciar o pacote — ver IniciarPacoteFisioterapiaUseCase); cada agendamento
        // aqui só consome 1 sessão do pacote ativo do paciente.
        PacoteFisioterapia pacoteConsumido = null;
        if (novoAgendamento.getTipoAtendimento() == TipoAtendimentoEnum.FISIOTERAPIA) {
            pacoteConsumido = pacoteRepository.findAtivoComSessaoDisponivel(novoAgendamento.getCdPaciente())
                    .orElseThrow(() -> new AgendamentoException(
                            "Paciente não tem pacote de fisioterapia ativo com sessões disponíveis — inicie um novo pacote antes de agendar."));
            pacoteConsumido.consumirSessao();
            novoAgendamento.vincularPacote(pacoteConsumido.getCdPacote());
        }

        Agendamento agendamento = agendamentoRepository.save(novoAgendamento);

        if (pacoteConsumido != null) {
            pacoteRepository.save(pacoteConsumido);
        }

        // Pilates é cobrado por sessão — cada agendamento gera 1 cobrança individual.
        if (novoAgendamento.getTipoAtendimento() == TipoAtendimentoEnum.PILATES) {
            Pagamento pagamento = new Pagamento(
                    null,
                    agendamento.getCdAgendamento(),
                    null,
                    agendamento.getCdPaciente(),
                    VALOR_SESSAO_PILATES,
                    FormaPagamentoEnum.DINHEIRO,
                    StatusPagamentoEnum.PENDENTE,
                    agendamento.getDataAgendamento(),
                    null,
                    "Sessão de pilates",
                    null
            );
            pagamentoRepository.save(pagamento);
        }

        return agendamentoMapper.toResponse(agendamento, paciente.getNmPaciente());
    }
}
