package com.system.fisio.domain;

import com.system.fisio.domain.enums.AtivoInativoEnum;
import com.system.fisio.domain.exception.UsuarioException;
import com.system.fisio.domain.model.Permissao;
import com.system.fisio.domain.model.Role;
import com.system.fisio.domain.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private Role roleAdministrador() {
        Permissao permissao = new Permissao(1, "USUARIO_LISTAR", "Listar usuários", "USUARIO");
        return new Role(1, "ADMINISTRADOR", "Acesso total", AtivoInativoEnum.ATIVO, true, Set.of(permissao), null);
    }

    private Usuario criarUsuarioValido() {
        return new Usuario(
                1,
                "João Silva",
                "joao@email.com",
                "joao",
                "123456",
                Set.of(roleAdministrador()),
                AtivoInativoEnum.ATIVO
        );
    }

    @Test
    @DisplayName("Deve criar usuário válido com sucesso")
    void deveCriarUsuarioValido() {
        Usuario usuario = criarUsuarioValido();

        assertEquals("João Silva", usuario.getNmUsuario());
        assertEquals("joao", usuario.getLogin());
        assertEquals(Set.of("ADMINISTRADOR"), usuario.getNomesRoles());
        assertEquals(AtivoInativoEnum.ATIVO, usuario.getStUsuario());
        assertNotNull(usuario.getDataCadastro());
    }

    @Test
    @DisplayName("Deve definir dataCadastro automaticamente")
    void deveDefinirDataCadastroAutomaticamente() {
        LocalDateTime antes = LocalDateTime.now();

        Usuario usuario = criarUsuarioValido();

        assertTrue(usuario.getDataCadastro().isAfter(antes.minusSeconds(1)));
    }

    @Test
    @DisplayName("Deve inativar usuário")
    void deveInativarUsuario() {
        Usuario usuario = criarUsuarioValido();

        usuario.inativar();

        assertEquals(AtivoInativoEnum.INATIVO, usuario.getStUsuario());
    }

    @Test
    @DisplayName("Deve ativar usuário")
    void deveAtivarUsuario() {
        Usuario usuario = criarUsuarioValido();

        usuario.inativar();
        usuario.ativar();

        assertEquals(AtivoInativoEnum.ATIVO, usuario.getStUsuario());
    }

    @Test
    @DisplayName("Deve validar usuário ativo sem lançar exceção")
    void deveValidarUsuarioAtivoSemErro() {
        Usuario usuario = criarUsuarioValido();

        assertDoesNotThrow(usuario::validarUsuarioAtivo);
    }

    @Test
    @DisplayName("Deve lançar exceção ao validar usuário inativo")
    void deveLancarExcecaoQuandoUsuarioInativo() {
        Usuario usuario = criarUsuarioValido();
        usuario.inativar();

        assertThrows(UsuarioException.class, usuario::validarUsuarioAtivo);
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome for nulo")
    void deveFalharQuandoNomeNulo() {
        assertThrows(UsuarioException.class, () ->
                new Usuario(1, null, "email@email.com", "login", "123", Set.of(roleAdministrador()), AtivoInativoEnum.ATIVO)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando login for vazio")
    void deveFalharQuandoLoginVazio() {
        assertThrows(UsuarioException.class, () ->
                new Usuario(1, "João", "email@email.com", "", "123", Set.of(roleAdministrador()), AtivoInativoEnum.ATIVO)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando senha for nula")
    void deveFalharQuandoSenhaNula() {
        assertThrows(UsuarioException.class, () ->
                new Usuario(null, "João", "email@email.com", "login", null, Set.of(roleAdministrador()), AtivoInativoEnum.ATIVO)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não tem nenhum perfil")
    void deveFalharQuandoSemRoles() {
        assertThrows(UsuarioException.class, () ->
                new Usuario(1, "João", "email@email.com", "login", "123", Set.of(), AtivoInativoEnum.ATIVO)
        );
    }

    @Test
    @DisplayName("Deve verificar permissão efetiva a partir das roles do usuário")
    void devePossuirPermissaoDasRoles() {
        Usuario usuario = criarUsuarioValido();

        assertTrue(usuario.possuiPermissao("USUARIO_LISTAR"));
        assertFalse(usuario.possuiPermissao("FINANCEIRO_QUALQUER_COISA"));
    }
}
