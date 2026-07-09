package com.system.fisio.domain.model;

import com.system.fisio.domain.enums.AtivoInativoEnum;
import com.system.fisio.domain.enums.TipoAtendimentoEnum;
import com.system.fisio.domain.exception.PacienteException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Paciente {

    private final Integer cdPaciente;
    private final String nmPaciente;
    private final String cpf;
    private final String celular;
    private final String email;
    private final String endereco;
    private final String bairro;
    private final String cidade;
    private final String estado;
    private final String cep;
    private final LocalDate dataAdmissao;
    private final LocalDate dataPagamento;
    private final BigDecimal valorMensalidade;
    private AtivoInativoEnum stPaciente;
    private final TipoAtendimentoEnum tipoAtendimento;
    private final LocalDateTime dtCadastro;

    private final LocalDate dataAvaliacaoAnamnese;
    private final String alinhamentoCabeca;
    private final String alinhamentoOmbros;
    private final String alinhamentoLinhaMamilar;
    private final String alinhamentoQuadril;
    private final String alinhamentoJoelhos;
    private final String alinhamentoPes;
    private final String alinhamentoPelve;

    private final LocalDate dataAvaliacaoFisica;
    private final String mobilidadeForcaNotas;
    private final String mobilidadeForcaObservacoes;
    private final String comentariosFisica;
    private final String assinaturaFisica;

    private final LocalDate dataAvaliacaoPostural;
    private final String posturaOmbros;
    private final String posturaCinturaEscapular;
    private final String posturaCurvaturasColuna;
    private final String posturaTrianguloTales;
    private final String posturaQuadril;
    private final String posturaLinhaPoplitea;
    private final String posturaTornozelo;

    private final LocalDate dataAvaliacaoPilates;
    private final String pilatesCinturaEscapular;
    private final String pilatesControleRespiratorio;
    private final String pilatesConscienciaCorporal;
    private final String pilatesEstabilidadeGlobal;
    private final String pilatesForcaGlobal;
    private final String pilatesEquilibrio;
    private final String pilatesFlexibilidadeMobilidade;
    private final String pilatesAlinhamentoPostural;
    private final String observacoesPilates;
    private final String assinaturaPilates;

    private byte[] arquivoTermoDados;
    private String arquivoTermoNome;
    private String arquivoTermoTipo;

    public Paciente(
            Integer cdPaciente,
            String nmPaciente,
            String cpf,
            String celular,
            String email,
            String endereco,
            String bairro,
            String cidade,
            String estado,
            String cep,
            LocalDate dataAdmissao,
            LocalDate dataPagamento,
            BigDecimal valorMensalidade,
            AtivoInativoEnum stPaciente,
            TipoAtendimentoEnum tipoAtendimento,
            LocalDateTime dtCadastro,
            LocalDate dataAvaliacaoAnamnese,
            String alinhamentoCabeca,
            String alinhamentoOmbros,
            String alinhamentoLinhaMamilar,
            String alinhamentoQuadril,
            String alinhamentoJoelhos,
            String alinhamentoPes,
            String alinhamentoPelve,
            LocalDate dataAvaliacaoFisica,
            String mobilidadeForcaNotas,
            String mobilidadeForcaObservacoes,
            String comentariosFisica,
            String assinaturaFisica,
            LocalDate dataAvaliacaoPostural,
            String posturaOmbros,
            String posturaCinturaEscapular,
            String posturaCurvaturasColuna,
            String posturaTrianguloTales,
            String posturaQuadril,
            String posturaLinhaPoplitea,
            String posturaTornozelo,
            LocalDate dataAvaliacaoPilates,
            String pilatesCinturaEscapular,
            String pilatesControleRespiratorio,
            String pilatesConscienciaCorporal,
            String pilatesEstabilidadeGlobal,
            String pilatesForcaGlobal,
            String pilatesEquilibrio,
            String pilatesFlexibilidadeMobilidade,
            String pilatesAlinhamentoPostural,
            String observacoesPilates,
            String assinaturaPilates,
            byte[] arquivoTermoDados,
            String arquivoTermoNome,
            String arquivoTermoTipo
    ) {
        this.cdPaciente = cdPaciente;
        this.nmPaciente = nmPaciente;
        this.cpf = cpf;
        this.celular = celular;
        this.email = email;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.dataAdmissao = dataAdmissao;
        this.dataPagamento = dataPagamento;
        this.valorMensalidade = valorMensalidade;
        this.stPaciente = stPaciente == null ? AtivoInativoEnum.ATIVO : stPaciente;
        this.tipoAtendimento = tipoAtendimento;
        this.dtCadastro = dtCadastro == null ? LocalDateTime.now() : dtCadastro;
        this.dataAvaliacaoAnamnese = dataAvaliacaoAnamnese;
        this.alinhamentoCabeca = alinhamentoCabeca;
        this.alinhamentoOmbros = alinhamentoOmbros;
        this.alinhamentoLinhaMamilar = alinhamentoLinhaMamilar;
        this.alinhamentoQuadril = alinhamentoQuadril;
        this.alinhamentoJoelhos = alinhamentoJoelhos;
        this.alinhamentoPes = alinhamentoPes;
        this.alinhamentoPelve = alinhamentoPelve;
        this.dataAvaliacaoFisica = dataAvaliacaoFisica;
        this.mobilidadeForcaNotas = mobilidadeForcaNotas;
        this.mobilidadeForcaObservacoes = mobilidadeForcaObservacoes;
        this.comentariosFisica = comentariosFisica;
        this.assinaturaFisica = assinaturaFisica;
        this.dataAvaliacaoPostural = dataAvaliacaoPostural;
        this.posturaOmbros = posturaOmbros;
        this.posturaCinturaEscapular = posturaCinturaEscapular;
        this.posturaCurvaturasColuna = posturaCurvaturasColuna;
        this.posturaTrianguloTales = posturaTrianguloTales;
        this.posturaQuadril = posturaQuadril;
        this.posturaLinhaPoplitea = posturaLinhaPoplitea;
        this.posturaTornozelo = posturaTornozelo;
        this.dataAvaliacaoPilates = dataAvaliacaoPilates;
        this.pilatesCinturaEscapular = pilatesCinturaEscapular;
        this.pilatesControleRespiratorio = pilatesControleRespiratorio;
        this.pilatesConscienciaCorporal = pilatesConscienciaCorporal;
        this.pilatesEstabilidadeGlobal = pilatesEstabilidadeGlobal;
        this.pilatesForcaGlobal = pilatesForcaGlobal;
        this.pilatesEquilibrio = pilatesEquilibrio;
        this.pilatesFlexibilidadeMobilidade = pilatesFlexibilidadeMobilidade;
        this.pilatesAlinhamentoPostural = pilatesAlinhamentoPostural;
        this.observacoesPilates = observacoesPilates;
        this.assinaturaPilates = assinaturaPilates;
        this.arquivoTermoDados = arquivoTermoDados;
        this.arquivoTermoNome = arquivoTermoNome;
        this.arquivoTermoTipo = arquivoTermoTipo;
        validar();
    }

    private void validar() {
        if (nmPaciente == null || nmPaciente.isBlank()) {
            throw new PacienteException("Nome do paciente é obrigatório");
        }
    }

    public void inativar() {
        this.stPaciente = AtivoInativoEnum.INATIVO;
    }

    public void manterArquivoTermoExistente(Paciente atual) {
        if (this.arquivoTermoDados == null && atual != null) {
            this.arquivoTermoDados = atual.getArquivoTermoDados();
            this.arquivoTermoNome = atual.getArquivoTermoNome();
            this.arquivoTermoTipo = atual.getArquivoTermoTipo();
        }
    }

    public Integer getCdPaciente() { return cdPaciente; }
    public String getNmPaciente() { return nmPaciente; }
    public String getCpf() { return cpf; }
    public String getCelular() { return celular; }
    public String getEmail() { return email; }
    public String getEndereco() { return endereco; }
    public String getBairro() { return bairro; }
    public String getCidade() { return cidade; }
    public String getEstado() { return estado; }
    public String getCep() { return cep; }
    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public LocalDate getDataPagamento() { return dataPagamento; }
    public BigDecimal getValorMensalidade() { return valorMensalidade; }
    public AtivoInativoEnum getStPaciente() { return stPaciente; }
    public TipoAtendimentoEnum getTipoAtendimento() { return tipoAtendimento; }
    public LocalDateTime getDtCadastro() { return dtCadastro; }
    public LocalDate getDataAvaliacaoAnamnese() { return dataAvaliacaoAnamnese; }
    public String getAlinhamentoCabeca() { return alinhamentoCabeca; }
    public String getAlinhamentoOmbros() { return alinhamentoOmbros; }
    public String getAlinhamentoLinhaMamilar() { return alinhamentoLinhaMamilar; }
    public String getAlinhamentoQuadril() { return alinhamentoQuadril; }
    public String getAlinhamentoJoelhos() { return alinhamentoJoelhos; }
    public String getAlinhamentoPes() { return alinhamentoPes; }
    public String getAlinhamentoPelve() { return alinhamentoPelve; }
    public LocalDate getDataAvaliacaoFisica() { return dataAvaliacaoFisica; }
    public String getMobilidadeForcaNotas() { return mobilidadeForcaNotas; }
    public String getMobilidadeForcaObservacoes() { return mobilidadeForcaObservacoes; }
    public String getComentariosFisica() { return comentariosFisica; }
    public String getAssinaturaFisica() { return assinaturaFisica; }
    public LocalDate getDataAvaliacaoPostural() { return dataAvaliacaoPostural; }
    public String getPosturaOmbros() { return posturaOmbros; }
    public String getPosturaCinturaEscapular() { return posturaCinturaEscapular; }
    public String getPosturaCurvaturasColuna() { return posturaCurvaturasColuna; }
    public String getPosturaTrianguloTales() { return posturaTrianguloTales; }
    public String getPosturaQuadril() { return posturaQuadril; }
    public String getPosturaLinhaPoplitea() { return posturaLinhaPoplitea; }
    public String getPosturaTornozelo() { return posturaTornozelo; }
    public LocalDate getDataAvaliacaoPilates() { return dataAvaliacaoPilates; }
    public String getPilatesCinturaEscapular() { return pilatesCinturaEscapular; }
    public String getPilatesControleRespiratorio() { return pilatesControleRespiratorio; }
    public String getPilatesConscienciaCorporal() { return pilatesConscienciaCorporal; }
    public String getPilatesEstabilidadeGlobal() { return pilatesEstabilidadeGlobal; }
    public String getPilatesForcaGlobal() { return pilatesForcaGlobal; }
    public String getPilatesEquilibrio() { return pilatesEquilibrio; }
    public String getPilatesFlexibilidadeMobilidade() { return pilatesFlexibilidadeMobilidade; }
    public String getPilatesAlinhamentoPostural() { return pilatesAlinhamentoPostural; }
    public String getObservacoesPilates() { return observacoesPilates; }
    public String getAssinaturaPilates() { return assinaturaPilates; }
    public byte[] getArquivoTermoDados() { return arquivoTermoDados; }
    public String getArquivoTermoNome() { return arquivoTermoNome; }
    public String getArquivoTermoTipo() { return arquivoTermoTipo; }
}
