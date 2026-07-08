package com.system.fisio.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "paciente")
@Getter
@Setter
@NoArgsConstructor
public class PacienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_paciente")
    private Integer cdPaciente;

    @Column(name = "nm_paciente", nullable = false)
    private String nmPaciente;

    @Column(name = "nr_cpf")
    private String cpf;

    @Column(name = "nr_celular")
    private String celular;

    @Column(name = "ds_email")
    private String email;

    @Column(name = "ds_endereco")
    private String endereco;

    @Column(name = "ds_bairro")
    private String bairro;

    @Column(name = "ds_cidade")
    private String cidade;

    @Column(name = "sg_estado")
    private String estado;

    @Column(name = "nr_cep")
    private String cep;

    @Column(name = "dt_admissao")
    private LocalDate dataAdmissao;

    @Column(name = "dt_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "vl_mensalidade")
    private BigDecimal valorMensalidade;

    @Column(name = "st_paciente")
    private Integer stPaciente;

    @Column(name = "dt_cadastro")
    private LocalDateTime dtCadastro;

    @Column(name = "dt_avaliacao_anamnese")
    private LocalDate dataAvaliacaoAnamnese;

    private String alinhamentoCabeca;
    private String alinhamentoOmbros;
    private String alinhamentoLinhaMamilar;
    private String alinhamentoQuadril;
    private String alinhamentoJoelhos;
    private String alinhamentoPes;
    private String alinhamentoPelve;

    @Column(name = "dt_avaliacao_fisica")
    private LocalDate dataAvaliacaoFisica;

    @Lob
    private String mobilidadeForcaNotas;

    @Lob
    private String mobilidadeForcaObservacoes;

    @Lob
    private String comentariosFisica;

    private String assinaturaFisica;

    @Column(name = "dt_avaliacao_postural")
    private LocalDate dataAvaliacaoPostural;

    private String posturaOmbros;
    private String posturaCinturaEscapular;
    private String posturaCurvaturasColuna;
    private String posturaTrianguloTales;
    private String posturaQuadril;
    private String posturaLinhaPoplitea;
    private String posturaTornozelo;

    @Column(name = "dt_avaliacao_pilates")
    private LocalDate dataAvaliacaoPilates;

    private String pilatesCinturaEscapular;
    private String pilatesControleRespiratorio;
    private String pilatesConscienciaCorporal;
    private String pilatesEstabilidadeGlobal;
    private String pilatesForcaGlobal;
    private String pilatesEquilibrio;
    private String pilatesFlexibilidadeMobilidade;
    private String pilatesAlinhamentoPostural;

    @Lob
    private String observacoesPilates;

    private String assinaturaPilates;

    @Column(name = "arquivo_termo_dados", columnDefinition = "bytea")
    private byte[] arquivoTermoDados;

    @Column(name = "arquivo_termo_nome")
    private String arquivoTermoNome;

    @Column(name = "arquivo_termo_tipo")
    private String arquivoTermoTipo;

    @PrePersist
    @PreUpdate
    private void aplicarPadroes() {
        if (stPaciente == null) {
            stPaciente = 1;
        }
        if (dtCadastro == null) {
            dtCadastro = LocalDateTime.now();
        }
    }
}
