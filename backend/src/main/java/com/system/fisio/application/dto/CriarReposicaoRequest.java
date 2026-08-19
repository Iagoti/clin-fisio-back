package com.system.fisio.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriarReposicaoRequest {
    private Integer cdAgendamentoOrigemFalta;
    private LocalDate dataAgendamento;
    private LocalTime horaAgendamento;
    private String observacoes;
}
