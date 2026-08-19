package com.system.fisio.application.dto;

import com.system.fisio.domain.enums.FormaPagamentoEnum;
import com.system.fisio.domain.enums.StatusPagamentoEnum;
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
public class PagamentoResponse {
    private Integer cdPagamento;
    private Integer cdAgendamento;
    private Integer cdPacote;
    private Integer cdPaciente;
    private String nmPaciente;
    private BigDecimal valor;
    private FormaPagamentoEnum formaPagamento;
    private StatusPagamentoEnum status;
    private LocalDate dtVencimento;
    private LocalDateTime dtPagamento;
    private String observacoes;
    private LocalDateTime dtCadastro;
}
