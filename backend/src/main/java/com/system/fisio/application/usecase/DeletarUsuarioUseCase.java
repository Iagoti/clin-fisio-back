package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.DeleteUsuarioResponse;
import com.system.fisio.application.dto.UsuarioResponse;
import com.system.fisio.domain.exception.AcessoNegadoException;
import com.system.fisio.domain.exception.UsuarioException;
import com.system.fisio.domain.model.Usuario;
import com.system.fisio.domain.ports.IUsuarioRepository;
import com.system.fisio.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeletarUsuarioUseCase {

    private final IUsuarioRepository usuarioRepository;

    public DeletarUsuarioUseCase(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public DeleteUsuarioResponse execute(Integer cdUsuario) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getAuthorities().stream()
                .noneMatch(a -> "ROLE_ADM".equals(a.getAuthority()))) {
            throw new AcessoNegadoException("Acesso negado: você não tem permissão para deletar usuários");
        }
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(cdUsuario);
        if (usuarioOptional.isEmpty()) {
            return new DeleteUsuarioResponse(false, "Usuário não encontrado");
        }
        usuarioRepository.deleteById(cdUsuario);
        return new DeleteUsuarioResponse(true, "Usuário deletado com sucesso");
    }
}
