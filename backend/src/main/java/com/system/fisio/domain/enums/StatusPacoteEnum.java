package com.system.fisio.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import tools.jackson.databind.JsonNode;
import com.system.fisio.domain.exception.CodigoInvalidoException;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusPacoteEnum {

    ATIVO(1, "Ativo"),
    CONCLUIDO(2, "Concluído"),
    CANCELADO(3, "Cancelado");

    private final int codigo;
    private final String descricao;

    StatusPacoteEnum(int codigo, String descricao) {
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
    public static StatusPacoteEnum fromCodigo(JsonNode node) {
        if (node == null || node.isNull()) {
            return null;
        }
        int codigo = node.isNumber() ? node.asInt() : Integer.parseInt(node.asText());
        return fromCodigo(Integer.valueOf(codigo));
    }

    public static StatusPacoteEnum fromCodigo(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        for (StatusPacoteEnum status : values()) {
            if (status.codigo == codigo) {
                return status;
            }
        }
        throw new CodigoInvalidoException("Código inválido para StatusPacote: " + codigo);
    }

    @Override
    public String toString() {
        return descricao;
    }
}
