package com.system.fisio.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.fisio.application.dto.UsuarioFiltro;
import com.system.fisio.application.dto.UsuarioResponse;
import com.system.fisio.application.mapper.UsuarioMapper;
import com.system.fisio.domain.model.Usuario;
import com.system.fisio.domain.ports.IUsuarioRepository;
import com.system.fisio.infrastructure.persistence.entity.UsuarioEntity;
import com.system.fisio.infrastructure.persistence.mapper.UsuarioPersistenceMapper;
import com.system.fisio.infrastructure.persistence.query.QueryResult;
import com.system.fisio.infrastructure.ports.IUsuarioQuery;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepositoryImpl implements IUsuarioRepository {

    private final UsuarioJpaRepository jpaRepository;
    private final UsuarioPersistenceMapper mapper;
    private final IUsuarioQuery usuarioQuery;
    private final JdbcTemplate jdbcTemplate;
    private final UsuarioMapper usuarioMapper;

    public UsuarioRepositoryImpl(
        UsuarioJpaRepository jpaRepository,
        UsuarioPersistenceMapper mapper,
        IUsuarioQuery usuarioQuery,
        JdbcTemplate jdbcTemplate,
        UsuarioMapper usuarioMapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.usuarioQuery = usuarioQuery;
        this.jdbcTemplate = jdbcTemplate;
        this.usuarioMapper = usuarioMapper;
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

    @Override
    public List<UsuarioResponse> findAllByFiltro(UsuarioFiltro filtro) {
        QueryResult result = usuarioQuery.findAllByFiltro(filtro);
        Object[] params = result.getParams() != null ? result.getParams().toArray(new Object[0]) : new Object[0];

        // A query só filtra IDs; o resto (incluindo roles, via @ManyToMany EAGER)
        // vem do JPA para reaproveitar o mesmo mapeamento usado em todo o resto do
        // módulo em vez de duplicar a lógica de montagem de UsuarioResponse aqui.
        List<Integer> ids = jdbcTemplate.query(
                result.getSql().toString(),
                params,
                (rs, rowNum) -> rs.getInt("cd_usuario")
        );

        return ids.stream()
                .map(jpaRepository::findById)
                .flatMap(Optional::stream)
                .map(mapper::toDomain)
                .map(usuarioMapper::toResponse)
                .toList();
    }

    @Override
    public Optional<Usuario> findById(Integer cdUsuario) {
        return jpaRepository.findById(cdUsuario)
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(Integer cdUsuario) {
        jpaRepository.deleteById(cdUsuario);
    }
}
