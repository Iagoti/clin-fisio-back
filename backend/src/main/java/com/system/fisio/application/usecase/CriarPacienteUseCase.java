package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.PacienteRequest;
import com.system.fisio.application.dto.PacienteResponse;
import com.system.fisio.application.mapper.PacienteMapper;
import com.system.fisio.domain.model.Paciente;
import com.system.fisio.domain.ports.IPacienteRepository;
import org.springframework.stereotype.Component;

@Component
public class CriarPacienteUseCase {

    private final IPacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;

    public CriarPacienteUseCase(IPacienteRepository pacienteRepository, PacienteMapper pacienteMapper) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteMapper = pacienteMapper;
    }

    public PacienteResponse execute(PacienteRequest request) {
        Paciente paciente = pacienteRepository.save(pacienteMapper.toDomain(request));
        return pacienteMapper.toResponse(paciente);
    }
}
