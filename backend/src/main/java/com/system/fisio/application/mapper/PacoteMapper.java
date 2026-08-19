package com.system.fisio.application.mapper;

import com.system.fisio.application.dto.PacoteResponse;
import com.system.fisio.domain.model.PacoteFisioterapia;
import org.springframework.stereotype.Component;

@Component
public class PacoteMapper {

    public PacoteResponse toResponse(PacoteFisioterapia pacote, String nmPaciente) {
        return new PacoteResponse(
                pacote.getCdPacote(),
                pacote.getCdPaciente(),
                nmPaciente,
                pacote.getQtSessoesTotal(),
                pacote.getQtSessoesConsumidas(),
                pacote.sessoesRestantes(),
                pacote.getValor(),
                pacote.getDtInicio(),
                pacote.getDtConclusao(),
                pacote.getStatus(),
                pacote.getDtCadastro()
        );
    }
}
