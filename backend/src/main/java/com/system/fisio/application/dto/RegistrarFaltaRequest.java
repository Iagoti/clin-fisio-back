package com.system.fisio.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarFaltaRequest {
    private Integer cdAgendamento;
    private boolean comAtestado;
    /** Base64 do arquivo do atestado (opcional, aceita prefixo data:...;base64,). */
    private String arquivoAtestadoBase64;
    private String nomeArquivoAtestado;
    private String tipoArquivoAtestado;
}
