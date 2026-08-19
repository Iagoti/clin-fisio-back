package com.system.fisio.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DespesaRequest {
    private Integer cdDespesa;
    private Integer cdCategoriaDespesa;
    private String descricao;
    private BigDecimal valor;
    private LocalDate dtVencimento;
    private String observacoes;
}
