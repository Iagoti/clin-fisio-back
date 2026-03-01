package com.system.fisio.domain.ports;

import java.util.List;
import java.util.Optional;

import com.system.fisio.application.dto.UsuarioFiltro;
import com.system.fisio.application.dto.UsuarioResponse;
import com.system.fisio.domain.model.Usuario;

public interface IUsuarioRepository {
    Optional<Usuario> findByEmail(String email);
    Usuario save(Usuario usuario);
    List<Usuario> findAll();
    List<UsuarioResponse> findAllByFiltro(UsuarioFiltro filtro);
}
