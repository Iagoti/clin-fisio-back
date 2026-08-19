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
@Table(name = "despesa")
@Getter
@Setter
@NoArgsConstructor
public class DespesaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_despesa")
    private Integer cdDespesa;

    @Column(name = "cd_categoria_despesa", nullable = false)
    private Integer cdCategoriaDespesa;

    @Column(name = "ds_descricao", nullable = false)
    private String descricao;

    @Column(name = "vl_despesa", nullable = false)
    private BigDecimal valor;

    @Column(name = "st_despesa")
    private Integer status;

    @Column(name = "dt_vencimento", nullable = false)
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
