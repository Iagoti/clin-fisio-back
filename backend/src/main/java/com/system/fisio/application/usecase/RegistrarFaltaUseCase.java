package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.AgendamentoResponse;
import com.system.fisio.application.dto.RegistrarFaltaRequest;
import com.system.fisio.application.mapper.AgendamentoMapper;
import com.system.fisio.domain.exception.AgendamentoException;
import com.system.fisio.domain.exception.PacienteException;
import com.system.fisio.domain.model.Agendamento;
import com.system.fisio.domain.ports.IAgendamentoRepository;
import com.system.fisio.domain.ports.IPacienteRepository;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class RegistrarFaltaUseCase {

    private final IAgendamentoRepository agendamentoRepository;
    private final IPacienteRepository pacienteRepository;
    private final AgendamentoMapper agendamentoMapper;

    public RegistrarFaltaUseCase(
            IAgendamentoRepository agendamentoRepository,
            IPacienteRepository pacienteRepository,
            AgendamentoMapper agendamentoMapper
    ) {
        this.agendamentoRepository = agendamentoRepository;
        this.pacienteRepository = pacienteRepository;
        this.agendamentoMapper = agendamentoMapper;
    }

    public AgendamentoResponse execute(RegistrarFaltaRequest request) {
        Agendamento agendamento = agendamentoRepository.findById(request.getCdAgendamento())
                .orElseThrow(() -> new AgendamentoException("Agendamento não encontrado"));

        byte[] arquivo = decodificarBase64(request.getArquivoAtestadoBase64());
        agendamento.marcarFalta(
                request.isComAtestado(),
                arquivo,
                arquivo != null ? request.getNomeArquivoAtestado() : null,
                arquivo != null ? request.getTipoArquivoAtestado() : null
        );

        Agendamento salvo = agendamentoRepository.save(agendamento);
        var paciente = pacienteRepository.findById(salvo.getCdPaciente())
                .orElseThrow(() -> new PacienteException("Paciente não encontrado"));
        return agendamentoMapper.toResponse(salvo, paciente.getNmPaciente());
    }

    private byte[] decodificarBase64(String base64) {
        if (base64 == null || base64.isBlank()) {
            return null;
        }
        String conteudo = base64;
        int idx = conteudo.indexOf(",");
        if (conteudo.startsWith("data:") && idx >= 0) {
            conteudo = conteudo.substring(idx + 1);
        }
        try {
            return Base64.getDecoder().decode(conteudo);
        } catch (IllegalArgumentException ex) {
            throw new AgendamentoException("Arquivo do atestado inválido");
        }
    }
}
