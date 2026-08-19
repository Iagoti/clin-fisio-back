package com.system.fisio.application.dto;

import com.system.fisio.domain.enums.FormaPagamentoEnum;
import com.system.fisio.domain.enums.StatusPagamentoEnum;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PagamentoRequest {
    private Integer cdPagamento;
    private Integer cdAgendamento;
    private Integer cdPacote;
    private Integer cdPaciente;
    private BigDecimal valor;
    private FormaPagamentoEnum formaPagamento;
    private StatusPagamentoEnum status;
    private LocalDate dtVencimento;
    private String observacoes;
}
