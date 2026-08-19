package com.system.fisio.domain.ports;

import com.system.fisio.domain.model.Permissao;

import java.util.List;
import java.util.Optional;

public interface IPermissaoRepository {
    List<Permissao> findAll();
    Optional<Permissao> findById(Integer cdPermissao);
}
