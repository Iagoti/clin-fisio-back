package com.system.fisio.application.ports;

import java.util.List;
import java.util.Set;

public interface ITokenService {
    String gerarToken(Integer cdUsuario, String login, Set<String> permissoes, Set<String> roles);
    String extrairLogin(String token);
    List<String> extrairPermissoes(String token);
    boolean tokenValido(String token);
}
