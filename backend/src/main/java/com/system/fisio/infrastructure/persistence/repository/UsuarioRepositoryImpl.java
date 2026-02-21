package com.system.fisio.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.system.fisio.domain.model.Usuario;
import com.system.fisio.domain.ports.IUsuarioRepository;
import com.system.fisio.infrastructure.persistence.entity.UsuarioEntity;
import com.system.fisio.infrastructure.persistence.mapper.UsuarioPersistenceMapper;

@Repository
public class UsuarioRepositoryImpl implements IUsuarioRepository {
    
    private final UsuarioJpaRepository jpaRepository;
    private final UsuarioPersistenceMapper mapper;

    public UsuarioRepositoryImpl(
        UsuarioJpaRepository jpaRepository,
        UsuarioPersistenceMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity usuarioEntity = mapper.toEntity(usuario);
        UsuarioEntity savedEntity = jpaRepository.save(usuarioEntity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public List<Usuario> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
