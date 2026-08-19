package com.system.fisio.application.dto;

import java.time.LocalDate;

public record AgendamentoFiltro(
        LocalDate dataAgendamento,
        String nmPaciente,
        Integer cdPaciente,
        Integer tipoAtendimento,
        Integer status
) {
}
