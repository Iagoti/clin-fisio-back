package com.system.fisio.infrastructure.persistence.mapper;

import com.system.fisio.domain.enums.AtivoInativoEnum;
import com.system.fisio.domain.enums.TipoAtendimentoEnum;
import com.system.fisio.domain.model.Paciente;
import com.system.fisio.infrastructure.persistence.entity.PacienteEntity;
import org.springframework.stereotype.Component;

@Component
public class PacientePersistenceMapper {

    public PacienteEntity toEntity(Paciente paciente) {
        PacienteEntity entity = new PacienteEntity();
        entity.setCdPaciente(paciente.getCdPaciente());
        entity.setNmPaciente(paciente.getNmPaciente());
        entity.setCpf(paciente.getCpf());
        entity.setCelular(paciente.getCelular());
        entity.setEmail(paciente.getEmail());
        entity.setEndereco(paciente.getEndereco());
        entity.setBairro(paciente.getBairro());
        entity.setCidade(paciente.getCidade());
        entity.setEstado(paciente.getEstado());
        entity.setCep(paciente.getCep());
        entity.setDataAdmissao(paciente.getDataAdmissao());
        entity.setDataPagamento(paciente.getDataPagamento());
        entity.setValorMensalidade(paciente.getValorMensalidade());
        entity.setStPaciente(paciente.getStPaciente().getCodigo());
        entity.setTipoAtendimento(paciente.getTipoAtendimento() != null ? paciente.getTipoAtendimento().getCodigo() : null);
        entity.setDtCadastro(paciente.getDtCadastro());
        entity.setDataAvaliacaoAnamnese(paciente.getDataAvaliacaoAnamnese());
        entity.setAlinhamentoCabeca(paciente.getAlinhamentoCabeca());
        entity.setAlinhamentoOmbros(paciente.getAlinhamentoOmbros());
        entity.setAlinhamentoLinhaMamilar(paciente.getAlinhamentoLinhaMamilar());
        entity.setAlinhamentoQuadril(paciente.getAlinhamentoQuadril());
        entity.setAlinhamentoJoelhos(paciente.getAlinhamentoJoelhos());
        entity.setAlinhamentoPes(paciente.getAlinhamentoPes());
        entity.setAlinhamentoPelve(paciente.getAlinhamentoPelve());
        entity.setDataAvaliacaoFisica(paciente.getDataAvaliacaoFisica());
        entity.setMobilidadeForcaNotas(paciente.getMobilidadeForcaNotas());
        entity.setMobilidadeForcaObservacoes(paciente.getMobilidadeForcaObservacoes());
        entity.setComentariosFisica(paciente.getComentariosFisica());
        entity.setAssinaturaFisica(paciente.getAssinaturaFisica());
        entity.setDataAvaliacaoPostural(paciente.getDataAvaliacaoPostural());
        entity.setPosturaOmbros(paciente.getPosturaOmbros());
        entity.setPosturaCinturaEscapular(paciente.getPosturaCinturaEscapular());
        entity.setPosturaCurvaturasColuna(paciente.getPosturaCurvaturasColuna());
        entity.setPosturaTrianguloTales(paciente.getPosturaTrianguloTales());
        entity.setPosturaQuadril(paciente.getPosturaQuadril());
        entity.setPosturaLinhaPoplitea(paciente.getPosturaLinhaPoplitea());
        entity.setPosturaTornozelo(paciente.getPosturaTornozelo());
        entity.setDataAvaliacaoPilates(paciente.getDataAvaliacaoPilates());
        entity.setPilatesCinturaEscapular(paciente.getPilatesCinturaEscapular());
        entity.setPilatesControleRespiratorio(paciente.getPilatesControleRespiratorio());
        entity.setPilatesConscienciaCorporal(paciente.getPilatesConscienciaCorporal());
        entity.setPilatesEstabilidadeGlobal(paciente.getPilatesEstabilidadeGlobal());
        entity.setPilatesForcaGlobal(paciente.getPilatesForcaGlobal());
        entity.setPilatesEquilibrio(paciente.getPilatesEquilibrio());
        entity.setPilatesFlexibilidadeMobilidade(paciente.getPilatesFlexibilidadeMobilidade());
        entity.setPilatesAlinhamentoPostural(paciente.getPilatesAlinhamentoPostural());
        entity.setObservacoesPilates(paciente.getObservacoesPilates());
        entity.setAssinaturaPilates(paciente.getAssinaturaPilates());
        entity.setArquivoTermoDados(paciente.getArquivoTermoDados());
        entity.setArquivoTermoNome(paciente.getArquivoTermoNome());
        entity.setArquivoTermoTipo(paciente.getArquivoTermoTipo());
        return entity;
    }

    public Paciente toDomain(PacienteEntity entity) {
        return new Paciente(
                entity.getCdPaciente(),
                entity.getNmPaciente(),
                entity.getCpf(),
                entity.getCelular(),
                entity.getEmail(),
                entity.getEndereco(),
                entity.getBairro(),
                entity.getCidade(),
                entity.getEstado(),
                entity.getCep(),
                entity.getDataAdmissao(),
                entity.getDataPagamento(),
                entity.getValorMensalidade(),
                AtivoInativoEnum.fromCodigo(entity.getStPaciente()),
                TipoAtendimentoEnum.fromCodigo(entity.getTipoAtendimento()),
                entity.getDtCadastro(),
                entity.getDataAvaliacaoAnamnese(),
                entity.getAlinhamentoCabeca(),
                entity.getAlinhamentoOmbros(),
                entity.getAlinhamentoLinhaMamilar(),
                entity.getAlinhamentoQuadril(),
                entity.getAlinhamentoJoelhos(),
                entity.getAlinhamentoPes(),
                entity.getAlinhamentoPelve(),
                entity.getDataAvaliacaoFisica(),
                entity.getMobilidadeForcaNotas(),
                entity.getMobilidadeForcaObservacoes(),
                entity.getComentariosFisica(),
                entity.getAssinaturaFisica(),
                entity.getDataAvaliacaoPostural(),
                entity.getPosturaOmbros(),
                entity.getPosturaCinturaEscapular(),
                entity.getPosturaCurvaturasColuna(),
                entity.getPosturaTrianguloTales(),
                entity.getPosturaQuadril(),
                entity.getPosturaLinhaPoplitea(),
                entity.getPosturaTornozelo(),
                entity.getDataAvaliacaoPilates(),
                entity.getPilatesCinturaEscapular(),
                entity.getPilatesControleRespiratorio(),
                entity.getPilatesConscienciaCorporal(),
                entity.getPilatesEstabilidadeGlobal(),
                entity.getPilatesForcaGlobal(),
                entity.getPilatesEquilibrio(),
                entity.getPilatesFlexibilidadeMobilidade(),
                entity.getPilatesAlinhamentoPostural(),
                entity.getObservacoesPilates(),
                entity.getAssinaturaPilates(),
                entity.getArquivoTermoDados(),
                entity.getArquivoTermoNome(),
                entity.getArquivoTermoTipo()
        );
    }
}
