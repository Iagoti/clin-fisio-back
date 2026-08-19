package com.system.fisio.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import tools.jackson.databind.JsonNode;
import com.system.fisio.domain.exception.CodigoInvalidoException;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusAgendamentoEnum {

    AGENDADO(1, "Agendado"),
    CONFIRMADO(2, "Confirmado"),
    CONCLUIDO(3, "Concluído"),
    CANCELADO(4, "Cancelado"),
    FALTOU(5, "Faltou");

    private final int codigo;
    private final String descricao;

    StatusAgendamentoEnum(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static StatusAgendamentoEnum fromCodigo(JsonNode node) {
        if (node == null || node.isNull()) {
            return null;
        }
        int codigo = node.isNumber() ? node.asInt() : Integer.parseInt(node.asText());
        return fromCodigo(Integer.valueOf(codigo));
    }

    public static StatusAgendamentoEnum fromCodigo(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        for (StatusAgendamentoEnum status : values()) {
            if (status.codigo == codigo) {
                return status;
            }
        }
        throw new CodigoInvalidoException("Código inválido para StatusAgendamento: " + codigo);
    }

    @Override
    public String toString() {
        return descricao;
    }
}
