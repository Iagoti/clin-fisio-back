package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.UsuarioRequest;
import com.system.fisio.application.dto.UsuarioResponse;
import com.system.fisio.application.mapper.UsuarioMapper;
import com.system.fisio.domain.enums.AtivoInativoEnum;
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
import java.util.Set;
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
                null,
                "João Silva",
                "joao@email.com",
                "joao",
                "senha123",
                AtivoInativoEnum.ATIVO,
                Set.of(1)
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
        assertEquals(Set.of(1), encodedRequest.getCdRoles());

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

        // A implementação atual não embrulha a mensagem com um prefixo — propaga a
        // mensagem de negócio diretamente.
        assertEquals("Usuário já cadastrado", ex.getMessage());

        verify(usuarioRepository).findByEmail(request.getEmail());
        verifyNoMoreInteractions(usuarioRepository, passwordEncoder, usuarioMapper);
    }

    @Test
    @DisplayName("Deve propagar exceção inesperada do mapper sem embrulhar")
    void devePropagarExcecaoInesperadaSemEmbrulhar() {

        when(usuarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.getSenha())).thenReturn("HASH_SENHA");
        when(usuarioMapper.toDomain(any(UsuarioRequest.class)))
                .thenThrow(new RuntimeException("Falha no mapper"));

        // A implementação atual não tem try/catch ao redor do mapper — a exceção
        // original propaga sem ser convertida em UsuarioException.
        RuntimeException ex = assertThrows(RuntimeException.class, () -> useCase.execute(request));

        assertEquals("Falha no mapper", ex.getMessage());

        verify(usuarioRepository).findByEmail(request.getEmail());
        verify(passwordEncoder).encode(request.getSenha());
        verify(usuarioMapper).toDomain(any(UsuarioRequest.class));
    }
}
