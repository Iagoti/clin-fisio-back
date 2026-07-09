package com.system.fisio.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import tools.jackson.databind.JsonNode;
import com.system.fisio.domain.exception.CodigoInvalidoException;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoAtendimentoEnum {

    FISIOTERAPIA(1, "Fisioterapia"),
    PILATES(2, "Pilates"),
    AMBOS(3, "Fisioterapia e Pilates");

    private final int codigo;
    private final String descricao;

    TipoAtendimentoEnum(int codigo, String descricao) {
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
    public static TipoAtendimentoEnum fromCodigo(JsonNode node) {
        if (node == null || node.isNull()) {
            return null;
        }
        int codigo = node.isNumber() ? node.asInt() : Integer.parseInt(node.asText());
        return fromCodigo(Integer.valueOf(codigo));
    }

    public static TipoAtendimentoEnum fromCodigo(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        for (TipoAtendimentoEnum tipo : values()) {
            if (tipo.codigo == codigo) {
                return tipo;
            }
        }
        throw new CodigoInvalidoException("Código inválido para TipoAtendimento: " + codigo);
    }

    @Override
    public String toString() {
        return descricao;
    }
}
