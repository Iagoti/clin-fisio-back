package com.system.fisio.domain.ports;

import com.system.fisio.application.dto.PacienteFiltro;
import com.system.fisio.application.dto.PacienteResponse;
import com.system.fisio.domain.model.Paciente;
import java.util.List;
import java.util.Optional;

public interface IPacienteRepository {
    Paciente save(Paciente paciente);
    List<PacienteResponse> findAllByFiltro(PacienteFiltro filtro);
    Optional<Paciente> findById(Integer cdPaciente);
    void deleteById(Integer cdPaciente);
}
