package com.system.fisio.domain.model;

import com.system.fisio.domain.enums.StatusPacoteEnum;
import com.system.fisio.domain.exception.PacoteException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Pacote de sessões de fisioterapia: 10 sessões por R$100, cobradas de uma vez ao
 * iniciar o pacote (ver IniciarPacoteFisioterapiaUseCase). Cada agendamento de
 * fisioterapia consome 1 sessão daqui (ver Agendamento.vincularPacote).
 */
public class PacoteFisioterapia {

    public static final int SESSOES_PADRAO = 10;
    public static final BigDecimal VALOR_PADRAO = new BigDecimal("100.00");

    private final Integer cdPacote;
    private final Integer cdPaciente;
    private final Integer qtSessoesTotal;
    private Integer qtSessoesConsumidas;
    private final BigDecimal valor;
    private final LocalDate dtInicio;
    private LocalDate dtConclusao;
    private StatusPacoteEnum status;
    private final LocalDateTime dtCadastro;

    public PacoteFisioterapia(
            Integer cdPacote,
            Integer cdPaciente,
            Integer qtSessoesTotal,
            Integer qtSessoesConsumidas,
            BigDecimal valor,
            LocalDate dtInicio,
            LocalDate dtConclusao,
            StatusPacoteEnum status,
            LocalDateTime dtCadastro
    ) {
        this.cdPacote = cdPacote;
        this.cdPaciente = cdPaciente;
        this.qtSessoesTotal = qtSessoesTotal != null ? qtSessoesTotal : SESSOES_PADRAO;
        this.qtSessoesConsumidas = qtSessoesConsumidas != null ? qtSessoesConsumidas : 0;
        this.valor = valor != null ? valor : VALOR_PADRAO;
        this.dtInicio = dtInicio != null ? dtInicio : LocalDate.now();
        this.dtConclusao = dtConclusao;
        this.status = status != null ? status : StatusPacoteEnum.ATIVO;
        this.dtCadastro = dtCadastro != null ? dtCadastro : LocalDateTime.now();
        validar();
    }

    private void validar() {
        if (cdPaciente == null) {
            throw new PacoteException("Paciente é obrigatório");
        }
        if (qtSessoesTotal == null || qtSessoesTotal <= 0) {
            throw new PacoteException("Quantidade de sessões do pacote inválida");
        }
        if (valor == null || valor.signum() <= 0) {
            throw new PacoteException("Valor do pacote inválido");
        }
    }

    public void validarAtivoComSessaoDisponivel() {
        if (status != StatusPacoteEnum.ATIVO) {
            throw new PacoteException("Pacote não está ativo");
        }
        if (qtSessoesConsumidas >= qtSessoesTotal) {
            throw new PacoteException("Pacote sem sessões disponíveis — inicie um novo pacote de fisioterapia");
        }
    }

    /** Consome 1 sessão; conclui automaticamente o pacote ao atingir o total. */
    public void consumirSessao() {
        validarAtivoComSessaoDisponivel();
        this.qtSessoesConsumidas++;
        if (this.qtSessoesConsumidas >= this.qtSessoesTotal) {
            this.status = StatusPacoteEnum.CONCLUIDO;
            this.dtConclusao = LocalDate.now();
        }
    }

    public boolean estaCompleto() {
        return qtSessoesConsumidas >= qtSessoesTotal;
    }

    public int sessoesRestantes() {
        return Math.max(0, qtSessoesTotal - qtSessoesConsumidas);
    }

    public void cancelar() {
        this.status = StatusPacoteEnum.CANCELADO;
    }

    public Integer getCdPacote() { return cdPacote; }
    public Integer getCdPaciente() { return cdPaciente; }
    public Integer getQtSessoesTotal() { return qtSessoesTotal; }
    public Integer getQtSessoesConsumidas() { return qtSessoesConsumidas; }
    public BigDecimal getValor() { return valor; }
    public LocalDate getDtInicio() { return dtInicio; }
    public LocalDate getDtConclusao() { return dtConclusao; }
    public StatusPacoteEnum getStatus() { return status; }
    public LocalDateTime getDtCadastro() { return dtCadastro; }
}
