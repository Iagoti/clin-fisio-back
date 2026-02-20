package com.system.fisio.application.ports;

public interface ITokenService {
    String gerarToken(Integer cdUsuario, String login, Integer tpUsuario);
    String extrairLogin(String token);
    String extrairRole(String token);
    boolean tokenValido(String token);
}
