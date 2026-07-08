package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.PacienteRequest;
import com.system.fisio.application.dto.PacienteResponse;
import com.system.fisio.application.mapper.PacienteMapper;
import com.system.fisio.domain.exception.PacienteException;
import com.system.fisio.domain.model.Paciente;
import com.system.fisio.domain.ports.IPacienteRepository;
import org.springframework.stereotype.Component;

@Component
public class AtualizarPacienteUseCase {

    private final IPacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;

    public AtualizarPacienteUseCase(IPacienteRepository pacienteRepository, PacienteMapper pacienteMapper) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteMapper = pacienteMapper;
    }

    public PacienteResponse execute(PacienteRequest request) {
        if (request.getCdPaciente() == null) {
            throw new PacienteException("Código do paciente é obrigatório para atualização");
        }
        Paciente atual = pacienteRepository.findById(request.getCdPaciente())
                .orElseThrow(() -> new PacienteException("Paciente não encontrado"));
        request.setDtCadastro(atual.getDtCadastro());
        Paciente pacienteAtualizado = pacienteMapper.toDomain(request);
        pacienteAtualizado.manterArquivoTermoExistente(atual);
        Paciente paciente = pacienteRepository.save(pacienteAtualizado);
        return pacienteMapper.toResponse(paciente);
    }
}
