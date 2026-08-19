package com.system.fisio.domain.model;

import com.system.fisio.domain.enums.StatusAgendamentoEnum;
import com.system.fisio.domain.enums.TipoAtendimentoEnum;
import com.system.fisio.domain.exception.AgendamentoException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Agendamento {

    private final Integer cdAgendamento;
    private final Integer cdPaciente;
    private final TipoAtendimentoEnum tipoAtendimento;
    private final LocalDate dataAgendamento;
    private final LocalTime horaAgendamento;
    private StatusAgendamentoEnum status;
    private final BigDecimal valor;
    private final String observacoes;
    private final LocalDateTime dtCadastro;

    /** Pacote de fisioterapia consumido por este agendamento (só faz sentido para FISIOTERAPIA). */
    private Integer cdPacote;
    /** Se preenchido, este agendamento é uma reposição da falta referenciada aqui. */
    private Integer cdAgendamentoOrigemFalta;
    private boolean comAtestado;
    private byte[] arquivoAtestadoDados;
    private String nomeArquivoAtestado;
    private String tipoArquivoAtestado;

    public Agendamento(
            Integer cdAgendamento,
            Integer cdPaciente,
            TipoAtendimentoEnum tipoAtendimento,
            LocalDate dataAgendamento,
            LocalTime horaAgendamento,
            StatusAgendamentoEnum status,
            BigDecimal valor,
            String observacoes,
            LocalDateTime dtCadastro
    ) {
        this(cdAgendamento, cdPaciente, tipoAtendimento, dataAgendamento, horaAgendamento, status,
                valor, observacoes, dtCadastro, null, null, false, null, null, null);
    }

    public Agendamento(
            Integer cdAgendamento,
            Integer cdPaciente,
            TipoAtendimentoEnum tipoAtendimento,
            LocalDate dataAgendamento,
            LocalTime horaAgendamento,
            StatusAgendamentoEnum status,
            BigDecimal valor,
            String observacoes,
            LocalDateTime dtCadastro,
            Integer cdPacote,
            Integer cdAgendamentoOrigemFalta,
            boolean comAtestado,
            byte[] arquivoAtestadoDados,
            String nomeArquivoAtestado,
            String tipoArquivoAtestado
    ) {
        this.cdAgendamento = cdAgendamento;
        this.cdPaciente = cdPaciente;
        this.tipoAtendimento = tipoAtendimento;
        this.dataAgendamento = dataAgendamento;
        this.horaAgendamento = horaAgendamento;
        this.status = status == null ? StatusAgendamentoEnum.AGENDADO : status;
        this.valor = valor;
        this.observacoes = observacoes;
        this.dtCadastro = dtCadastro == null ? LocalDateTime.now() : dtCadastro;
        this.cdPacote = cdPacote;
        this.cdAgendamentoOrigemFalta = cdAgendamentoOrigemFalta;
        this.comAtestado = comAtestado;
        this.arquivoAtestadoDados = arquivoAtestadoDados;
        this.nomeArquivoAtestado = nomeArquivoAtestado;
        this.tipoArquivoAtestado = tipoArquivoAtestado;
        validar();
    }

    private void validar() {
        if (cdPaciente == null) {
            throw new AgendamentoException("Paciente é obrigatório");
        }
        if (tipoAtendimento == null) {
            throw new AgendamentoException("Tipo de atendimento é obrigatório");
        }
        if (dataAgendamento == null) {
            throw new AgendamentoException("Data do agendamento é obrigatória");
        }
        if (horaAgendamento == null) {
            throw new AgendamentoException("Horário do agendamento é obrigatório");
        }
    }

    public void cancelar() {
        this.status = StatusAgendamentoEnum.CANCELADO;
    }

    /** Vincula este agendamento ao pacote de fisioterapia do qual ele consome 1 sessão. */
    public void vincularPacote(Integer cdPacote) {
        this.cdPacote = cdPacote;
    }

    /** Marca este agendamento como reposição da falta referenciada. */
    public void vincularOrigemFalta(Integer cdAgendamentoOrigemFalta) {
        this.cdAgendamentoOrigemFalta = cdAgendamentoOrigemFalta;
    }

    public void marcarFalta(boolean comAtestado, byte[] arquivoDados, String nomeArquivo, String tipoArquivo) {
        if (this.status == StatusAgendamentoEnum.CANCELADO) {
            throw new AgendamentoException("Agendamento cancelado não pode ser marcado como falta");
        }
        this.status = StatusAgendamentoEnum.FALTOU;
        this.comAtestado = comAtestado;
        this.arquivoAtestadoDados = arquivoDados;
        this.nomeArquivoAtestado = nomeArquivo;
        this.tipoArquivoAtestado = tipoArquivo;
    }

    public boolean isReposicao() {
        return cdAgendamentoOrigemFalta != null;
    }

    public Integer getCdAgendamento() { return cdAgendamento; }
    public Integer getCdPaciente() { return cdPaciente; }
    public TipoAtendimentoEnum getTipoAtendimento() { return tipoAtendimento; }
    public LocalDate getDataAgendamento() { return dataAgendamento; }
    public LocalTime getHoraAgendamento() { return horaAgendamento; }
    public StatusAgendamentoEnum getStatus() { return status; }
    public BigDecimal getValor() { return valor; }
    public String getObservacoes() { return observacoes; }
    public LocalDateTime getDtCadastro() { return dtCadastro; }
    public Integer getCdPacote() { return cdPacote; }
    public Integer getCdAgendamentoOrigemFalta() { return cdAgendamentoOrigemFalta; }
    public boolean isComAtestado() { return comAtestado; }
    public byte[] getArquivoAtestadoDados() { return arquivoAtestadoDados; }
    public String getNomeArquivoAtestado() { return nomeArquivoAtestado; }
    public String getTipoArquivoAtestado() { return tipoArquivoAtestado; }
}
