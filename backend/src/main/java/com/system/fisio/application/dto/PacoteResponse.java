package com.system.fisio.application.dto;

import com.system.fisio.domain.enums.StatusPacoteEnum;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacoteResponse {
    private Integer cdPacote;
    private Integer cdPaciente;
    private String nmPaciente;
    private Integer qtSessoesTotal;
    private Integer qtSessoesConsumidas;
    private Integer sessoesRestantes;
    private BigDecimal valor;
    private LocalDate dtInicio;
    private LocalDate dtConclusao;
    private StatusPacoteEnum status;
    private LocalDateTime dtCadastro;
}
