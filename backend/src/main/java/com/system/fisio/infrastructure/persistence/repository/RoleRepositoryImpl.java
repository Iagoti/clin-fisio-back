package com.system.fisio.infrastructure.persistence.repository;

import com.system.fisio.domain.model.Role;
import com.system.fisio.domain.ports.IRoleRepository;
import com.system.fisio.infrastructure.persistence.entity.RoleEntity;
import com.system.fisio.infrastructure.persistence.mapper.RolePersistenceMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements IRoleRepository {

    private final RoleJpaRepository jpaRepository;
    private final RolePersistenceMapper mapper;
    private final JdbcTemplate jdbcTemplate;

    public RoleRepositoryImpl(RoleJpaRepository jpaRepository, RolePersistenceMapper mapper, JdbcTemplate jdbcTemplate) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Role save(Role role) {
        RoleEntity entity = mapper.toEntity(role);
        RoleEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Role> findById(Integer cdRole) {
        return jpaRepository.findById(cdRole).map(mapper::toDomain);
    }

    @Override
    public Optional<Role> findByNome(String nmRole) {
        return jpaRepository.findByNmRole(nmRole).map(mapper::toDomain);
    }

    @Override
    public List<Role> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(Integer cdRole) {
        jpaRepository.deleteById(cdRole);
    }

    @Override
    public boolean existeUsuarioVinculado(Integer cdRole) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM usuario_role WHERE cd_role = ?", Integer.class, cdRole
        );
        return count != null && count > 0;
    }
}
