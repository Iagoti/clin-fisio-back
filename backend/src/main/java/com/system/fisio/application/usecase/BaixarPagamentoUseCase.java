package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.BaixarPagamentoRequest;
import com.system.fisio.application.dto.PagamentoResponse;
import com.system.fisio.application.mapper.PagamentoMapper;
import com.system.fisio.domain.exception.PacienteException;
import com.system.fisio.domain.exception.PagamentoException;
import com.system.fisio.domain.model.Pagamento;
import com.system.fisio.domain.ports.IPacienteRepository;
import com.system.fisio.domain.ports.IPagamentoRepository;
import org.springframework.stereotype.Component;

@Component
public class BaixarPagamentoUseCase {

    private final IPagamentoRepository pagamentoRepository;
    private final IPacienteRepository pacienteRepository;
    private final PagamentoMapper mapper;

    public BaixarPagamentoUseCase(IPagamentoRepository pagamentoRepository, IPacienteRepository pacienteRepository, PagamentoMapper mapper) {
        this.pagamentoRepository = pagamentoRepository;
        this.pacienteRepository = pacienteRepository;
        this.mapper = mapper;
    }

    public PagamentoResponse execute(Integer cdPagamento, BaixarPagamentoRequest request) {
        Pagamento pagamento = pagamentoRepository.findById(cdPagamento)
                .orElseThrow(() -> new PagamentoException("Pagamento não encontrado"));
        pagamento.baixar(request.getFormaPagamento(), request.getDtPagamento());
        Pagamento salvo = pagamentoRepository.save(pagamento);
        var paciente = pacienteRepository.findById(salvo.getCdPaciente())
                .orElseThrow(() -> new PacienteException("Paciente não encontrado"));
        return mapper.toResponse(salvo, paciente.getNmPaciente());
    }
}
