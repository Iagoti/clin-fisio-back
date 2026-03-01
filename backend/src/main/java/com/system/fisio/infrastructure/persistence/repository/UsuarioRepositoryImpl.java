package com.system.fisio.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.fisio.application.dto.UsuarioFiltro;
import com.system.fisio.domain.enums.AtivoInativoEnum;
import com.system.fisio.domain.enums.TipoUsuario;
import com.system.fisio.domain.model.Usuario;
import com.system.fisio.domain.ports.IUsuarioRepository;
import com.system.fisio.infrastructure.persistence.entity.UsuarioEntity;
import com.system.fisio.infrastructure.persistence.mapper.UsuarioPersistenceMapper;
import com.system.fisio.infrastructure.persistence.query.QueryResult;
import com.system.fisio.infrastructure.ports.IUsuarioQuery;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.system.fisio.application.dto.UsuarioResponse;

import java.sql.Timestamp;

@Repository
public class UsuarioRepositoryImpl implements IUsuarioRepository {
    
    private final UsuarioJpaRepository jpaRepository;
    private final UsuarioPersistenceMapper mapper;
    private final IUsuarioQuery usuarioQuery;
    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepositoryImpl(
        UsuarioJpaRepository jpaRepository,
        UsuarioPersistenceMapper mapper,
        IUsuarioQuery usuarioQuery,
        JdbcTemplate jdbcTemplate
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.usuarioQuery = usuarioQuery;
        this.jdbcTemplate = jdbcTemplate;
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

        return jdbcTemplate.query(
                result.getSql().toString(),
                params,
                (rs, rowNum) -> {
                    Timestamp dtCadastro = rs.getTimestamp("dt_cadastro");
                    return new UsuarioResponse(
                            rs.getInt("cd_usuario"),
                            rs.getString("nm_usuario"),
                            rs.getString("email"),
                            rs.getString("login"),
                            AtivoInativoEnum.fromCodigo(rs.getObject("st_usuario", Integer.class)),
                            TipoUsuario.fromCodigo(rs.getObject("tp_usuario", Integer.class)),
                            dtCadastro != null ? dtCadastro.toLocalDateTime() : null
                    );
                }
        );
    }
}
