package com.system.fisio.application.dto;

import com.system.fisio.domain.enums.StatusDespesaEnum;
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
public class DespesaResponse {
    private Integer cdDespesa;
    private Integer cdCategoriaDespesa;
    private String nmCategoria;
    private String descricao;
    private BigDecimal valor;
    private StatusDespesaEnum status;
    private LocalDate dtVencimento;
    private LocalDateTime dtPagamento;
    private String observacoes;
    private LocalDateTime dtCadastro;
}
