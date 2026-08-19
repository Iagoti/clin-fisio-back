package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.DeleteUsuarioResponse;
import com.system.fisio.domain.model.Usuario;
import com.system.fisio.domain.ports.IUsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeletarUsuarioUseCase {

    private final IUsuarioRepository usuarioRepository;

    public DeletarUsuarioUseCase(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Autorização (quem pode deletar usuário) é responsabilidade da camada HTTP
    // (@PreAuthorize em UsuarioController), não deste caso de uso.
    public DeleteUsuarioResponse execute(Integer cdUsuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(cdUsuario);
        if (usuarioOptional.isEmpty()) {
            return new DeleteUsuarioResponse(false, "Usuário não encontrado");
        }
        usuarioRepository.deleteById(cdUsuario);
        return new DeleteUsuarioResponse(true, "Usuário deletado com sucesso");
    }
}
