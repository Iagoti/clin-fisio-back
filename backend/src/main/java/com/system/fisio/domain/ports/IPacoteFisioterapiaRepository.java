package com.system.fisio.domain.ports;

import com.system.fisio.application.dto.PacoteFiltro;
import com.system.fisio.application.dto.PacoteResponse;
import com.system.fisio.domain.model.PacoteFisioterapia;

import java.util.List;
import java.util.Optional;

public interface IPacoteFisioterapiaRepository {
    PacoteFisioterapia save(PacoteFisioterapia pacote);
    Optional<PacoteFisioterapia> findById(Integer cdPacote);
    /** Pacote ATIVO mais antigo do paciente que ainda tem sessão disponível. */
    Optional<PacoteFisioterapia> findAtivoComSessaoDisponivel(Integer cdPaciente);
    List<PacoteResponse> findAllByFiltro(PacoteFiltro filtro);
}
