package com.system.fisio.domain.ports;

import com.system.fisio.domain.model.CategoriaDespesa;

import java.util.List;
import java.util.Optional;

public interface ICategoriaDespesaRepository {
    CategoriaDespesa save(CategoriaDespesa categoria);
    Optional<CategoriaDespesa> findById(Integer cdCategoriaDespesa);
    List<CategoriaDespesa> findAll();
}
