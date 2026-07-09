package com.system.fisio.application.mapper;

import com.system.fisio.application.dto.PacienteRequest;
import com.system.fisio.application.dto.PacienteResponse;
import com.system.fisio.domain.exception.PacienteException;
import com.system.fisio.domain.model.Paciente;
import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class PacienteMapper {

    public Paciente toDomain(PacienteRequest request) {
        byte[] arquivoTermoDados = decodificarBase64(request.getArquivoTermoBase64());
        return new Paciente(
                request.getCdPaciente(),
                request.getNome(),
                request.getCpf(),
                request.getCelular(),
                request.getEmail(),
                request.getEndereco(),
                request.getBairro(),
                request.getCidade(),
                request.getEstado(),
                request.getCep(),
                request.getDataAdmissao(),
                request.getDataPagamento(),
                request.getValorMensalidade(),
                request.getStPaciente(),
                request.getTipoAtendimento(),
                request.getDtCadastro(),
                request.getDataAvaliacaoAnamnese(),
                request.getAlinhamentoCabeca(),
                request.getAlinhamentoOmbros(),
                request.getAlinhamentoLinhaMamilar(),
                request.getAlinhamentoQuadril(),
                request.getAlinhamentoJoelhos(),
                request.getAlinhamentoPes(),
                request.getAlinhamentoPelve(),
                request.getDataAvaliacaoFisica(),
                request.getMobilidadeForcaNotas(),
                request.getMobilidadeForcaObservacoes(),
                request.getComentariosFisica(),
                request.getAssinaturaFisica(),
                request.getDataAvaliacaoPostural(),
                request.getPosturaOmbros(),
                request.getPosturaCinturaEscapular(),
                request.getPosturaCurvaturasColuna(),
                request.getPosturaTrianguloTales(),
                request.getPosturaQuadril(),
                request.getPosturaLinhaPoplitea(),
                request.getPosturaTornozelo(),
                request.getDataAvaliacaoPilates(),
                request.getPilatesCinturaEscapular(),
                request.getPilatesControleRespiratorio(),
                request.getPilatesConscienciaCorporal(),
                request.getPilatesEstabilidadeGlobal(),
                request.getPilatesForcaGlobal(),
                request.getPilatesEquilibrio(),
                request.getPilatesFlexibilidadeMobilidade(),
                request.getPilatesAlinhamentoPostural(),
                request.getObservacoesPilates(),
                request.getAssinaturaPilates(),
                request.getQuantidadeSessoes(),
                request.getSessoesAgendadas(),
                arquivoTermoDados,
                arquivoTermoDados != null ? request.getArquivoTermoNome() : null,
                arquivoTermoDados != null ? request.getArquivoTermoTipo() : null
        );
    }

    public PacienteResponse toResponse(Paciente paciente) {
        PacienteResponse response = new PacienteResponse(
                paciente.getCdPaciente(),
                paciente.getNmPaciente(),
                paciente.getCpf(),
                paciente.getCelular(),
                paciente.getEmail(),
                paciente.getEndereco(),
                paciente.getBairro(),
                paciente.getCidade(),
                paciente.getEstado(),
                paciente.getCep(),
                paciente.getDataAdmissao(),
                paciente.getDataPagamento(),
                paciente.getValorMensalidade(),
                paciente.getStPaciente(),
                paciente.getTipoAtendimento(),
                paciente.getDtCadastro(),
                paciente.getDataAvaliacaoAnamnese(),
                paciente.getAlinhamentoCabeca(),
                paciente.getAlinhamentoOmbros(),
                paciente.getAlinhamentoLinhaMamilar(),
                paciente.getAlinhamentoQuadril(),
                paciente.getAlinhamentoJoelhos(),
                paciente.getAlinhamentoPes(),
                paciente.getAlinhamentoPelve(),
                paciente.getDataAvaliacaoFisica(),
                paciente.getMobilidadeForcaNotas(),
                paciente.getMobilidadeForcaObservacoes(),
                paciente.getComentariosFisica(),
                paciente.getAssinaturaFisica(),
                paciente.getDataAvaliacaoPostural(),
                paciente.getPosturaOmbros(),
                paciente.getPosturaCinturaEscapular(),
                paciente.getPosturaCurvaturasColuna(),
                paciente.getPosturaTrianguloTales(),
                paciente.getPosturaQuadril(),
                paciente.getPosturaLinhaPoplitea(),
                paciente.getPosturaTornozelo(),
                paciente.getDataAvaliacaoPilates(),
                paciente.getPilatesCinturaEscapular(),
                paciente.getPilatesControleRespiratorio(),
                paciente.getPilatesConscienciaCorporal(),
                paciente.getPilatesEstabilidadeGlobal(),
                paciente.getPilatesForcaGlobal(),
                paciente.getPilatesEquilibrio(),
                paciente.getPilatesFlexibilidadeMobilidade(),
                paciente.getPilatesAlinhamentoPostural(),
                paciente.getObservacoesPilates(),
                paciente.getAssinaturaPilates(),
                paciente.getQuantidadeSessoes(),
                paciente.getSessoesAgendadas(),
                paciente.getArquivoTermoDados() != null && paciente.getArquivoTermoDados().length > 0,
                paciente.getArquivoTermoNome(),
                paciente.getArquivoTermoTipo()
        );
        return response;
    }

    private byte[] decodificarBase64(String base64) {
        if (base64 == null || base64.isBlank()) {
            return null;
        }
        String conteudo = base64;
        int idx = conteudo.indexOf(",");
        if (conteudo.startsWith("data:") && idx >= 0) {
            conteudo = conteudo.substring(idx + 1);
        }
        try {
            return Base64.getDecoder().decode(conteudo);
        } catch (IllegalArgumentException ex) {
            throw new PacienteException("Arquivo do termo inválido");
        }
    }
}
