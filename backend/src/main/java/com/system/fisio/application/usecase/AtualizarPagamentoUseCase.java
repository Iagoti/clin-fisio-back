package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.PagamentoRequest;
import com.system.fisio.application.dto.PagamentoResponse;
import com.system.fisio.application.mapper.PagamentoMapper;
import com.system.fisio.domain.enums.StatusPagamentoEnum;
import com.system.fisio.domain.exception.PacienteException;
import com.system.fisio.domain.exception.PagamentoException;
import com.system.fisio.domain.model.Pagamento;
import com.system.fisio.domain.ports.IPacienteRepository;
import com.system.fisio.domain.ports.IPagamentoRepository;
import org.springframework.stereotype.Component;

@Component
public class AtualizarPagamentoUseCase {

    private final IPagamentoRepository pagamentoRepository;
    private final IPacienteRepository pacienteRepository;
    private final PagamentoMapper mapper;

    public AtualizarPagamentoUseCase(IPagamentoRepository pagamentoRepository, IPacienteRepository pacienteRepository, PagamentoMapper mapper) {
        this.pagamentoRepository = pagamentoRepository;
        this.pacienteRepository = pacienteRepository;
        this.mapper = mapper;
    }

    public PagamentoResponse execute(PagamentoRequest request) {
        if (request.getCdPagamento() == null) {
            throw new PagamentoException("Código do pagamento é obrigatório para atualização");
        }
        Pagamento atual = pagamentoRepository.findById(request.getCdPagamento())
                .orElseThrow(() -> new PagamentoException("Pagamento não encontrado"));
        if (atual.getStatus() != StatusPagamentoEnum.PENDENTE) {
            throw new PagamentoException("Só é possível editar pagamentos pendentes — use baixar/cancelar");
        }
        // Edição não reabre status: só campos livres (valor, vencimento, forma, observações).
        request.setStatus(StatusPagamentoEnum.PENDENTE);

        var paciente = pacienteRepository.findById(request.getCdPaciente())
                .orElseThrow(() -> new PacienteException("Paciente não encontrado"));

        Pagamento salvo = pagamentoRepository.save(mapper.toDomain(request));
        return mapper.toResponse(salvo, paciente.getNmPaciente());
    }
}
