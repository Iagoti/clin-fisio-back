package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.PagamentoResponse;
import com.system.fisio.application.mapper.PagamentoMapper;
import com.system.fisio.domain.exception.PacienteException;
import com.system.fisio.domain.exception.PagamentoException;
import com.system.fisio.domain.ports.IPacienteRepository;
import com.system.fisio.domain.ports.IPagamentoRepository;
import org.springframework.stereotype.Component;

@Component
public class BuscarPagamentoByIdUseCase {

    private final IPagamentoRepository pagamentoRepository;
    private final IPacienteRepository pacienteRepository;
    private final PagamentoMapper mapper;

    public BuscarPagamentoByIdUseCase(IPagamentoRepository pagamentoRepository, IPacienteRepository pacienteRepository, PagamentoMapper mapper) {
        this.pagamentoRepository = pagamentoRepository;
        this.pacienteRepository = pacienteRepository;
        this.mapper = mapper;
    }

    public PagamentoResponse execute(Integer cdPagamento) {
        var pagamento = pagamentoRepository.findById(cdPagamento)
                .orElseThrow(() -> new PagamentoException("Pagamento não encontrado"));
        var paciente = pacienteRepository.findById(pagamento.getCdPaciente())
                .orElseThrow(() -> new PacienteException("Paciente não encontrado"));
        return mapper.toResponse(pagamento, paciente.getNmPaciente());
    }
}
