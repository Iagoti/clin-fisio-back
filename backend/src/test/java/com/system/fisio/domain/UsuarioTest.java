package com.system.fisio.domain;

import com.system.fisio.domain.enums.AtivoInativoEnum;
import com.system.fisio.domain.enums.TipoUsuario;
import com.system.fisio.domain.exception.UsuarioException;
import com.system.fisio.domain.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private Usuario criarUsuarioValido() {
        return new Usuario(
                1,
                "João Silva",
                "joao@email.com",
                "joao",
                "123456",
                TipoUsuario.ADM,
                AtivoInativoEnum.ATIVO
        );
    }

    @Test
    @DisplayName("Deve criar usuário válido com sucesso")
    void deveCriarUsuarioValido() {
        Usuario usuario = criarUsuarioValido();

        assertEquals("João Silva", usuario.getNmUsuario());
        assertEquals("joao", usuario.getLogin());
        assertEquals(TipoUsuario.ADM, usuario.getTpUsuario());
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
                new Usuario(1, null, "email@email.com", "login", "123", TipoUsuario.ADM, AtivoInativoEnum.ATIVO)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando login for vazio")
    void deveFalharQuandoLoginVazio() {
        assertThrows(UsuarioException.class, () ->
                new Usuario(1, "João", "email@email.com", "", "123", TipoUsuario.ADM, AtivoInativoEnum.ATIVO)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando senha for nula")
    void deveFalharQuandoSenhaNula() {
        assertThrows(UsuarioException.class, () ->
                new Usuario(1, "João", "email@email.com", "login", null, TipoUsuario.ADM, AtivoInativoEnum.ATIVO)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando tipo for nulo")
    void deveFalharQuandoTipoNulo() {
        assertThrows(UsuarioException.class, () ->
                new Usuario(1, "João", "email@email.com", "login", "123", null, AtivoInativoEnum.ATIVO)
        );
    }
}

