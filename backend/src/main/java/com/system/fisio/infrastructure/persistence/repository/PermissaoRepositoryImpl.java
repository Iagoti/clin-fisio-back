package com.system.fisio.infrastructure.persistence.repository;

import com.system.fisio.domain.model.Permissao;
import com.system.fisio.domain.ports.IPermissaoRepository;
import com.system.fisio.infrastructure.persistence.mapper.PermissaoPersistenceMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PermissaoRepositoryImpl implements IPermissaoRepository {

    private final PermissaoJpaRepository jpaRepository;
    private final PermissaoPersistenceMapper mapper;

    public PermissaoRepositoryImpl(PermissaoJpaRepository jpaRepository, PermissaoPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Permissao> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Permissao> findById(Integer cdPermissao) {
        return jpaRepository.findById(cdPermissao).map(mapper::toDomain);
    }
}
