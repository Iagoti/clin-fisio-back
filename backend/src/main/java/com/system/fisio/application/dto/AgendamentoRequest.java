package com.system.fisio.application.dto;

import com.system.fisio.domain.enums.StatusAgendamentoEnum;
import com.system.fisio.domain.enums.TipoAtendimentoEnum;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AgendamentoRequest {
    private Integer cdAgendamento;
    private Integer cdPaciente;
    private TipoAtendimentoEnum tipoAtendimento;
    private LocalDate dataAgendamento;
    private LocalTime horaAgendamento;
    private StatusAgendamentoEnum status = StatusAgendamentoEnum.AGENDADO;
    private BigDecimal valor;
    private String observacoes;
    private LocalDateTime dtCadastro;
}
