package com.system.fisio.application.ports;

public interface ITokenService {
    String gerarToken(Integer cdUsuario, String login, String role);
    String extrairLogin(String token);
    String extrairRole(String token);
    boolean tokenValido(String token);
}
