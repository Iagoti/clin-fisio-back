package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.PagamentoRequest;
import com.system.fisio.application.dto.PagamentoResponse;
import com.system.fisio.application.mapper.PagamentoMapper;
import com.system.fisio.domain.exception.PacienteException;
import com.system.fisio.domain.model.Pagamento;
import com.system.fisio.domain.ports.IPacienteRepository;
import com.system.fisio.domain.ports.IPagamentoRepository;
import org.springframework.stereotype.Component;

/** Lançamento manual de um pagamento avulso (não vinculado a agendamento/pacote). */
@Component
public class CriarPagamentoUseCase {

    private final IPagamentoRepository pagamentoRepository;
    private final IPacienteRepository pacienteRepository;
    private final PagamentoMapper mapper;

    public CriarPagamentoUseCase(IPagamentoRepository pagamentoRepository, IPacienteRepository pacienteRepository, PagamentoMapper mapper) {
        this.pagamentoRepository = pagamentoRepository;
        this.pacienteRepository = pacienteRepository;
        this.mapper = mapper;
    }

    public PagamentoResponse execute(PagamentoRequest request) {
        var paciente = pacienteRepository.findById(request.getCdPaciente())
                .orElseThrow(() -> new PacienteException("Paciente não encontrado"));
        Pagamento pagamento = pagamentoRepository.save(mapper.toDomain(request));
        return mapper.toResponse(pagamento, paciente.getNmPaciente());
    }
}
