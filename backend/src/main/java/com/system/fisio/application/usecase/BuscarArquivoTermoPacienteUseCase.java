package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.ArquivoTermoResponse;
import com.system.fisio.domain.exception.PacienteException;
import com.system.fisio.domain.model.Paciente;
import com.system.fisio.domain.ports.IPacienteRepository;
import org.springframework.stereotype.Component;

@Component
public class BuscarArquivoTermoPacienteUseCase {

    private final IPacienteRepository pacienteRepository;

    public BuscarArquivoTermoPacienteUseCase(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public ArquivoTermoResponse execute(Integer cdPaciente) {
        Paciente paciente = pacienteRepository.findById(cdPaciente)
                .orElseThrow(() -> new PacienteException("Paciente não encontrado"));
        if (paciente.getArquivoTermoDados() == null || paciente.getArquivoTermoDados().length == 0) {
            throw new PacienteException("Paciente não possui arquivo de termo anexado");
        }
        return new ArquivoTermoResponse(
                paciente.getArquivoTermoDados(),
                paciente.getArquivoTermoNome(),
                paciente.getArquivoTermoTipo()
        );
    }
}
