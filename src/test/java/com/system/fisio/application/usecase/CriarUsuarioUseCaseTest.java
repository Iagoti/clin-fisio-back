package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.UsuarioRequest;
import com.system.fisio.application.dto.UsuarioResponse;
import com.system.fisio.application.mapper.UsuarioMapper;
import com.system.fisio.domain.enums.AtivoInativoEnum;
import com.system.fisio.domain.enums.TipoUsuario;
import com.system.fisio.domain.exception.UsuarioException;
import com.system.fisio.domain.model.Usuario;
import com.system.fisio.domain.ports.IUsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarUsuarioUseCaseTest {

    @Mock
    private IUsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private CriarUsuarioUseCase useCase;

    private UsuarioRequest request;
    private Usuario usuarioDomain;
    private UsuarioResponse response;

    @BeforeEach
    void setup() {
        request = new UsuarioRequest(
                "João Silva",
                "joao@email.com",
                "joao",
                "senha123",
                AtivoInativoEnum.ATIVO,
                TipoUsuario.ADM
        );

        usuarioDomain = mock(Usuario.class);
        response = mock(UsuarioResponse.class);
    }

    @Test
    @DisplayName("Deve criar usuário com sucesso (verifica encode da senha e fluxo repository/mapper)")
    void deveCriarUsuarioComSucesso() {

        when(usuarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getSenha())).thenReturn("HASH_SENHA");

        ArgumentCaptor<UsuarioRequest> requestCaptor = ArgumentCaptor.forClass(UsuarioRequest.class);

        when(usuarioMapper.toDomain(any(UsuarioRequest.class))).thenReturn(usuarioDomain);
        when(usuarioRepository.save(usuarioDomain)).thenReturn(usuarioDomain);
        when(usuarioMapper.toResponse(usuarioDomain)).thenReturn(response);

        UsuarioResponse result = useCase.execute(request);

        assertSame(response, result);

        verify(usuarioRepository).findByEmail(request.getEmail());
        verify(passwordEncoder).encode("senha123");

        verify(usuarioMapper).toDomain(requestCaptor.capture());
        UsuarioRequest encodedRequest = requestCaptor.getValue();

        assertEquals("João Silva", encodedRequest.getNome());
        assertEquals("joao@email.com", encodedRequest.getEmail());
        assertEquals("joao", encodedRequest.getLogin());
        assertEquals("HASH_SENHA", encodedRequest.getSenha());
        assertEquals(AtivoInativoEnum.ATIVO, encodedRequest.getStUsuario());
        assertEquals(TipoUsuario.ADM, encodedRequest.getTipo());

        verify(usuarioRepository).save(usuarioDomain);
        verify(usuarioMapper).toResponse(usuarioDomain);

        verifyNoMoreInteractions(usuarioRepository, passwordEncoder, usuarioMapper);
    }

    @Test
    @DisplayName("Deve lançar UsuarioException quando email já estiver cadastrado")
    void deveFalharQuandoEmailJaCadastrado() {
        when(usuarioRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(mock(Usuario.class)));

        UsuarioException ex = assertThrows(UsuarioException.class, () -> useCase.execute(request));

        assertTrue(ex.getMessage().contains("Erro ao criar usuário:"));
        assertTrue(ex.getMessage().contains("Usuário já cadastrado"));

        verify(usuarioRepository).findByEmail(request.getEmail());
        verifyNoMoreInteractions(usuarioRepository, passwordEncoder, usuarioMapper);
    }

    @Test
    @DisplayName("Deve embrulhar exceção inesperada em UsuarioException com mensagem padrão")
    void deveEmbrulharExcecaoInesperada() {

        when(usuarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getSenha())).thenReturn("HASH_SENHA");
        when(usuarioMapper.toDomain(any(UsuarioRequest.class)))
                .thenThrow(new RuntimeException("Falha no mapper"));

        UsuarioException ex = assertThrows(UsuarioException.class, () -> useCase.execute(request));

        assertEquals("Erro ao criar usuário: Falha no mapper", ex.getMessage());

        verify(usuarioRepository).findByEmail(request.getEmail());
        verify(passwordEncoder).encode(request.getSenha());
        verify(usuarioMapper).toDomain(any(UsuarioRequest.class));
    }
}

