package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.IniciarPacoteRequest;
import com.system.fisio.application.dto.PacoteResponse;
import com.system.fisio.application.mapper.PacoteMapper;
import com.system.fisio.domain.enums.FormaPagamentoEnum;
import com.system.fisio.domain.enums.StatusPagamentoEnum;
import com.system.fisio.domain.exception.PacoteException;
import com.system.fisio.domain.model.PacoteFisioterapia;
import com.system.fisio.domain.model.Pagamento;
import com.system.fisio.domain.ports.IPacienteRepository;
import com.system.fisio.domain.ports.IPacoteFisioterapiaRepository;
import com.system.fisio.domain.ports.IPagamentoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Inicia um novo pacote de fisioterapia (10 sessões) e gera, na mesma operação, o
 * Pagamento de R$100 correspondente — a cobrança nasce junto com o pacote, não
 * por sessão (ver decisão de negócio registrada no plano da Fase 3).
 */
@Component
public class IniciarPacoteFisioterapiaUseCase {

    private final IPacoteFisioterapiaRepository pacoteRepository;
    private final IPagamentoRepository pagamentoRepository;
    private final IPacienteRepository pacienteRepository;
    private final PacoteMapper pacoteMapper;

    public IniciarPacoteFisioterapiaUseCase(
            IPacoteFisioterapiaRepository pacoteRepository,
            IPagamentoRepository pagamentoRepository,
            IPacienteRepository pacienteRepository,
            PacoteMapper pacoteMapper
    ) {
        this.pacoteRepository = pacoteRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.pacienteRepository = pacienteRepository;
        this.pacoteMapper = pacoteMapper;
    }

    public PacoteResponse execute(IniciarPacoteRequest request) {
        var paciente = pacienteRepository.findById(request.getCdPaciente())
                .orElseThrow(() -> new PacoteException("Paciente não encontrado"));

        pacoteRepository.findAtivoComSessaoDisponivel(request.getCdPaciente()).ifPresent(p -> {
            throw new PacoteException("Paciente já tem um pacote de fisioterapia ativo com sessões disponíveis");
        });

        PacoteFisioterapia novoPacote = new PacoteFisioterapia(
                null,
                request.getCdPaciente(),
                PacoteFisioterapia.SESSOES_PADRAO,
                0,
                PacoteFisioterapia.VALOR_PADRAO,
                LocalDate.now(),
                null,
                null,
                null
        );
        PacoteFisioterapia pacoteSalvo = pacoteRepository.save(novoPacote);

        Pagamento pagamento = new Pagamento(
                null,
                null,
                pacoteSalvo.getCdPacote(),
                pacoteSalvo.getCdPaciente(),
                pacoteSalvo.getValor(),
                FormaPagamentoEnum.DINHEIRO,
                StatusPagamentoEnum.PENDENTE,
                LocalDate.now(),
                null,
                "Pacote de fisioterapia — " + PacoteFisioterapia.SESSOES_PADRAO + " sessões",
                null
        );
        pagamentoRepository.save(pagamento);

        return pacoteMapper.toResponse(pacoteSalvo, paciente.getNmPaciente());
    }
}
