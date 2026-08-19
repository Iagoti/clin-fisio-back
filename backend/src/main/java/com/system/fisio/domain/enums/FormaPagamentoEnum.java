package com.system.fisio.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import tools.jackson.databind.JsonNode;
import com.system.fisio.domain.exception.CodigoInvalidoException;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FormaPagamentoEnum {

    DINHEIRO(1, "Dinheiro"),
    PIX(2, "Pix"),
    CARTAO_CREDITO(3, "Cartão de crédito"),
    CARTAO_DEBITO(4, "Cartão de débito"),
    TRANSFERENCIA(5, "Transferência");

    private final int codigo;
    private final String descricao;

    FormaPagamentoEnum(int codigo, String descricao) {
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
    public static FormaPagamentoEnum fromCodigo(JsonNode node) {
        if (node == null || node.isNull()) {
            return null;
        }
        int codigo = node.isNumber() ? node.asInt() : Integer.parseInt(node.asText());
        return fromCodigo(Integer.valueOf(codigo));
    }

    public static FormaPagamentoEnum fromCodigo(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        for (FormaPagamentoEnum forma : values()) {
            if (forma.codigo == codigo) {
                return forma;
            }
        }
        throw new CodigoInvalidoException("Código inválido para FormaPagamento: " + codigo);
    }

    @Override
    public String toString() {
        return descricao;
    }
}
