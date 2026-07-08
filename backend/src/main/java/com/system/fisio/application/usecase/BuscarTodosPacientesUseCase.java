package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.PacienteFiltro;
import com.system.fisio.application.dto.PacienteResponse;
import com.system.fisio.domain.ports.IPacienteRepository;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BuscarTodosPacientesUseCase {

    private final IPacienteRepository pacienteRepository;

    public BuscarTodosPacientesUseCase(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<PacienteResponse> execute(PacienteFiltro filtro) {
        return pacienteRepository.findAllByFiltro(filtro);
    }
}
