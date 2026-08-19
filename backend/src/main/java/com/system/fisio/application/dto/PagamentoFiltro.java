package com.system.fisio.application.dto;

import java.time.LocalDate;

public record PagamentoFiltro(
        Integer cdPaciente,
        Integer status,
        LocalDate dtInicio,
        LocalDate dtFim
) {
}
