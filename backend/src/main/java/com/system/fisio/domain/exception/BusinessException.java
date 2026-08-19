package com.system.fisio.domain.exception;

public abstract class BusinessException extends RuntimeException {

    protected BusinessException(String message) {
        super(message);
    }

    /**
     * Código de status HTTP a ser usado pelo GlobalExceptionHandler ao traduzir
     * esta exceção em resposta. Mantido como int puro (sem depender de tipos do
     * Spring) para não vazar framework para a camada de domínio.
     */
    public int httpStatus() {
        return 400;
    }

}

