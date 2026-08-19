package com.system.fisio.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pagamento")
@Getter
@Setter
@NoArgsConstructor
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_pagamento")
    private Integer cdPagamento;

    @Column(name = "cd_agendamento")
    private Integer cdAgendamento;

    @Column(name = "cd_pacote")
    private Integer cdPacote;

    @Column(name = "cd_paciente", nullable = false)
    private Integer cdPaciente;

    @Column(name = "vl_pagamento", nullable = false)
    private BigDecimal valor;

    @Column(name = "tp_forma_pagamento")
    private Integer formaPagamento;

    @Column(name = "st_pagamento")
    private Integer status;

    @Column(name = "dt_vencimento")
    private LocalDate dtVencimento;

    @Column(name = "dt_pagamento")
    private LocalDateTime dtPagamento;

    @Column(name = "ds_observacoes")
    private String observacoes;

    @Column(name = "dt_cadastro")
    private LocalDateTime dtCadastro;

    @PrePersist
    private void aplicarPadroes() {
        if (status == null) {
            status = 1;
        }
        if (dtCadastro == null) {
            dtCadastro = LocalDateTime.now();
        }
    }
}
