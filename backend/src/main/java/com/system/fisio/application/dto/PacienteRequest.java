package com.system.fisio.application.dto;

import com.system.fisio.domain.enums.AtivoInativoEnum;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PacienteRequest {
    private Integer cdPaciente;
    private String nome;
    private String cpf;
    private String celular;
    private String email;
    private String endereco;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private LocalDate dataAdmissao;
    private LocalDate dataPagamento;
    private BigDecimal valorMensalidade;
    private AtivoInativoEnum stPaciente = AtivoInativoEnum.ATIVO;
    private LocalDateTime dtCadastro;

    private LocalDate dataAvaliacaoAnamnese;
    private String alinhamentoCabeca;
    private String alinhamentoOmbros;
    private String alinhamentoLinhaMamilar;
    private String alinhamentoQuadril;
    private String alinhamentoJoelhos;
    private String alinhamentoPes;
    private String alinhamentoPelve;

    private LocalDate dataAvaliacaoFisica;
    private String mobilidadeForcaNotas;
    private String mobilidadeForcaObservacoes;
    private String comentariosFisica;
    private String assinaturaFisica;

    private LocalDate dataAvaliacaoPostural;
    private String posturaOmbros;
    private String posturaCinturaEscapular;
    private String posturaCurvaturasColuna;
    private String posturaTrianguloTales;
    private String posturaQuadril;
    private String posturaLinhaPoplitea;
    private String posturaTornozelo;

    private LocalDate dataAvaliacaoPilates;
    private String pilatesCinturaEscapular;
    private String pilatesControleRespiratorio;
    private String pilatesConscienciaCorporal;
    private String pilatesEstabilidadeGlobal;
    private String pilatesForcaGlobal;
    private String pilatesEquilibrio;
    private String pilatesFlexibilidadeMobilidade;
    private String pilatesAlinhamentoPostural;
    private String observacoesPilates;
    private String assinaturaPilates;

    /** Conteúdo do arquivo (PDF ou imagem) do termo, codificado em Base64. Informar apenas quando um novo arquivo for selecionado. */
    private String arquivoTermoBase64;
    private String arquivoTermoNome;
    private String arquivoTermoTipo;
}
