package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.PacienteResponse;
import com.system.fisio.application.mapper.PacienteMapper;
import com.system.fisio.domain.exception.PacienteException;
import com.system.fisio.domain.ports.IPacienteRepository;
import org.springframework.stereotype.Component;

@Component
public class BuscarPacienteByIdUseCase {

    private final IPacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;

    public BuscarPacienteByIdUseCase(IPacienteRepository pacienteRepository, PacienteMapper pacienteMapper) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteMapper = pacienteMapper;
    }

    public PacienteResponse execute(Integer cdPaciente) {
        return pacienteRepository.findById(cdPaciente)
                .map(pacienteMapper::toResponse)
                .orElseThrow(() -> new PacienteException("Paciente não encontrado"));
    }
}
