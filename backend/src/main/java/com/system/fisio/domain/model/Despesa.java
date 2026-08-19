package com.system.fisio.domain.model;

import com.system.fisio.domain.enums.StatusDespesaEnum;
import com.system.fisio.domain.exception.DespesaException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Despesa {

    private final Integer cdDespesa;
    private final Integer cdCategoriaDespesa;
    private String descricao;
    private BigDecimal valor;
    private StatusDespesaEnum status;
    private LocalDate dtVencimento;
    private LocalDateTime dtPagamento;
    private String observacoes;
    private final LocalDateTime dtCadastro;

    public Despesa(
            Integer cdDespesa,
            Integer cdCategoriaDespesa,
            String descricao,
            BigDecimal valor,
            StatusDespesaEnum status,
            LocalDate dtVencimento,
            LocalDateTime dtPagamento,
            String observacoes,
            LocalDateTime dtCadastro
    ) {
        this.cdDespesa = cdDespesa;
        this.cdCategoriaDespesa = cdCategoriaDespesa;
        this.descricao = descricao;
        this.valor = valor;
        this.status = status != null ? status : StatusDespesaEnum.PENDENTE;
        this.dtVencimento = dtVencimento;
        this.dtPagamento = dtPagamento;
        this.observacoes = observacoes;
        this.dtCadastro = dtCadastro != null ? dtCadastro : LocalDateTime.now();
        validar();
    }

    private void validar() {
        if (cdCategoriaDespesa == null) {
            throw new DespesaException("Categoria é obrigatória");
        }
        if (descricao == null || descricao.isBlank()) {
            throw new DespesaException("Descrição é obrigatória");
        }
        if (valor == null || valor.signum() <= 0) {
            throw new DespesaException("Valor da despesa inválido");
        }
        if (dtVencimento == null) {
            throw new DespesaException("Data de vencimento é obrigatória");
        }
    }

    public void atualizarDados(String descricao, BigDecimal valor, LocalDate dtVencimento, String observacoes) {
        if (status == StatusDespesaEnum.PAGO) {
            throw new DespesaException("Despesa já paga não pode ser editada");
        }
        this.descricao = descricao;
        this.valor = valor;
        this.dtVencimento = dtVencimento;
        this.observacoes = observacoes;
        validar();
    }

    public void baixar(LocalDateTime dtPagamento) {
        if (status == StatusDespesaEnum.CANCELADO) {
            throw new DespesaException("Despesa cancelada não pode ser baixada");
        }
        this.status = StatusDespesaEnum.PAGO;
        this.dtPagamento = dtPagamento != null ? dtPagamento : LocalDateTime.now();
    }

    public void cancelar() {
        if (status == StatusDespesaEnum.PAGO) {
            throw new DespesaException("Despesa já paga não pode ser cancelada");
        }
        this.status = StatusDespesaEnum.CANCELADO;
    }

    public Integer getCdDespesa() { return cdDespesa; }
    public Integer getCdCategoriaDespesa() { return cdCategoriaDespesa; }
    public String getDescricao() { return descricao; }
    public BigDecimal getValor() { return valor; }
    public StatusDespesaEnum getStatus() { return status; }
    public LocalDate getDtVencimento() { return dtVencimento; }
    public LocalDateTime getDtPagamento() { return dtPagamento; }
    public String getObservacoes() { return observacoes; }
    public LocalDateTime getDtCadastro() { return dtCadastro; }
}
