package com.system.fisio.domain.ports;

import com.system.fisio.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleRepository {
    Role save(Role role);
    Optional<Role> findById(Integer cdRole);
    Optional<Role> findByNome(String nmRole);
    List<Role> findAll();
    void deleteById(Integer cdRole);

    /** Usada para bloquear exclusão de perfis ainda vinculados a usuários. */
    boolean existeUsuarioVinculado(Integer cdRole);
}
