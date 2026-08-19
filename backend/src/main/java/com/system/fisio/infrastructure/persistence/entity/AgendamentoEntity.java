package com.system.fisio.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "agendamento")
@Getter
@Setter
@NoArgsConstructor
public class AgendamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_agendamento")
    private Integer cdAgendamento;

    @Column(name = "cd_paciente", nullable = false)
    private Integer cdPaciente;

    @Column(name = "tipo_atendimento", nullable = false)
    private Integer tipoAtendimento;

    @Column(name = "dt_agendamento", nullable = false)
    private LocalDate dataAgendamento;

    @Column(name = "hr_agendamento", nullable = false)
    private LocalTime horaAgendamento;

    @Column(name = "status")
    private Integer status;

    @Column(name = "vl_agendamento")
    private BigDecimal valor;

    @Column(name = "ds_observacoes")
    private String observacoes;

    @Column(name = "dt_cadastro")
    private LocalDateTime dtCadastro;

    @Column(name = "cd_pacote")
    private Integer cdPacote;

    @Column(name = "cd_agendamento_origem_falta")
    private Integer cdAgendamentoOrigemFalta;

    @Column(name = "fl_com_atestado")
    private boolean comAtestado;

    @Column(name = "arquivo_atestado_dados")
    private byte[] arquivoAtestadoDados;

    @Column(name = "nm_arquivo_atestado")
    private String nomeArquivoAtestado;

    @Column(name = "tp_arquivo_atestado")
    private String tipoArquivoAtestado;

    @PrePersist
    @PreUpdate
    private void aplicarPadroes() {
        if (status == null) {
            status = 1;
        }
        if (dtCadastro == null) {
            dtCadastro = LocalDateTime.now();
        }
    }
}
