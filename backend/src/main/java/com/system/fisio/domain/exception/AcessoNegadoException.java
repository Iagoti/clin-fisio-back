package com.system.fisio.domain.exception;

public class AcessoNegadoException extends BusinessException {
    public AcessoNegadoException(String message) {
        super(message);
    }

    @Override
    public int httpStatus() {
        return 403;
    }
}

