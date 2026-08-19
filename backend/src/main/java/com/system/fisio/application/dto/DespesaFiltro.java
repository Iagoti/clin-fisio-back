package com.system.fisio.application.dto;

import java.time.LocalDate;

public record DespesaFiltro(
        Integer cdCategoriaDespesa,
        Integer status,
        LocalDate dtInicio,
        LocalDate dtFim
) {
}
