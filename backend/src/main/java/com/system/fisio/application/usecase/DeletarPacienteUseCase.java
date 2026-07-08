package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.DeletePacienteResponse;
import com.system.fisio.domain.exception.PacienteException;
import com.system.fisio.domain.ports.IPacienteRepository;
import org.springframework.stereotype.Component;

@Component
public class DeletarPacienteUseCase {

    private final IPacienteRepository pacienteRepository;

    public DeletarPacienteUseCase(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public DeletePacienteResponse execute(Integer cdPaciente) {
        pacienteRepository.findById(cdPaciente)
                .orElseThrow(() -> new PacienteException("Paciente não encontrado"));
        pacienteRepository.deleteById(cdPaciente);
        return new DeletePacienteResponse(cdPaciente, "Paciente excluído com sucesso");
    }
}
