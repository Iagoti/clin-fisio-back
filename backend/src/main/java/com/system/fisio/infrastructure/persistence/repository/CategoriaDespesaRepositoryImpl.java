package com.system.fisio.infrastructure.persistence.repository;

import com.system.fisio.domain.model.CategoriaDespesa;
import com.system.fisio.domain.ports.ICategoriaDespesaRepository;
import com.system.fisio.infrastructure.persistence.mapper.CategoriaDespesaPersistenceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoriaDespesaRepositoryImpl implements ICategoriaDespesaRepository {

    private final CategoriaDespesaJpaRepository jpaRepository;
    private final CategoriaDespesaPersistenceMapper mapper;

    public CategoriaDespesaRepositoryImpl(CategoriaDespesaJpaRepository jpaRepository, CategoriaDespesaPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public CategoriaDespesa save(CategoriaDespesa categoria) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(categoria)));
    }

    @Override
    public Optional<CategoriaDespesa> findById(Integer cdCategoriaDespesa) {
        return jpaRepository.findById(cdCategoriaDespesa).map(mapper::toDomain);
    }

    @Override
    public List<CategoriaDespesa> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }
}
