package com.system.fisio.domain.model;

import com.system.fisio.domain.enums.FormaPagamentoEnum;
import com.system.fisio.domain.enums.StatusPagamentoEnum;
import com.system.fisio.domain.exception.PagamentoException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Pagamento {

    private final Integer cdPagamento;
    private final Integer cdAgendamento;
    private final Integer cdPacote;
    private final Integer cdPaciente;
    private final BigDecimal valor;
    private FormaPagamentoEnum formaPagamento;
    private StatusPagamentoEnum status;
    private final LocalDate dtVencimento;
    private LocalDateTime dtPagamento;
    private String observacoes;
    private final LocalDateTime dtCadastro;

    public Pagamento(
            Integer cdPagamento,
            Integer cdAgendamento,
            Integer cdPacote,
            Integer cdPaciente,
            BigDecimal valor,
            FormaPagamentoEnum formaPagamento,
            StatusPagamentoEnum status,
            LocalDate dtVencimento,
            LocalDateTime dtPagamento,
            String observacoes,
            LocalDateTime dtCadastro
    ) {
        this.cdPagamento = cdPagamento;
        this.cdAgendamento = cdAgendamento;
        this.cdPacote = cdPacote;
        this.cdPaciente = cdPaciente;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
        this.status = status != null ? status : StatusPagamentoEnum.PENDENTE;
        this.dtVencimento = dtVencimento;
        this.dtPagamento = dtPagamento;
        this.observacoes = observacoes;
        this.dtCadastro = dtCadastro != null ? dtCadastro : LocalDateTime.now();
        validar();
    }

    private void validar() {
        if (cdPaciente == null) {
            throw new PagamentoException("Paciente é obrigatório");
        }
        if (valor == null || valor.signum() <= 0) {
            throw new PagamentoException("Valor do pagamento inválido");
        }
    }

    public void baixar(FormaPagamentoEnum formaPagamento, LocalDateTime dtPagamento) {
        if (status == StatusPagamentoEnum.CANCELADO) {
            throw new PagamentoException("Pagamento cancelado não pode ser baixado");
        }
        this.status = StatusPagamentoEnum.PAGO;
        if (formaPagamento != null) {
            this.formaPagamento = formaPagamento;
        }
        this.dtPagamento = dtPagamento != null ? dtPagamento : LocalDateTime.now();
    }

    public void cancelar() {
        if (status == StatusPagamentoEnum.PAGO) {
            throw new PagamentoException("Pagamento já baixado não pode ser cancelado");
        }
        this.status = StatusPagamentoEnum.CANCELADO;
    }

    public Integer getCdPagamento() { return cdPagamento; }
    public Integer getCdAgendamento() { return cdAgendamento; }
    public Integer getCdPacote() { return cdPacote; }
    public Integer getCdPaciente() { return cdPaciente; }
    public BigDecimal getValor() { return valor; }
    public FormaPagamentoEnum getFormaPagamento() { return formaPagamento; }
    public StatusPagamentoEnum getStatus() { return status; }
    public LocalDate getDtVencimento() { return dtVencimento; }
    public LocalDateTime getDtPagamento() { return dtPagamento; }
    public String getObservacoes() { return observacoes; }
    public LocalDateTime getDtCadastro() { return dtCadastro; }
}
