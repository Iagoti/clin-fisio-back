package com.system.fisio.infrastructure.persistence.repository;

import com.system.fisio.application.dto.DespesaFiltro;
import com.system.fisio.application.dto.DespesaResponse;
import com.system.fisio.domain.enums.StatusDespesaEnum;
import com.system.fisio.domain.model.Despesa;
import com.system.fisio.domain.ports.IDespesaRepository;
import com.system.fisio.infrastructure.persistence.mapper.DespesaPersistenceMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DespesaRepositoryImpl implements IDespesaRepository {

    private final DespesaJpaRepository jpaRepository;
    private final DespesaPersistenceMapper mapper;
    private final JdbcTemplate jdbcTemplate;

    public DespesaRepositoryImpl(DespesaJpaRepository jpaRepository, DespesaPersistenceMapper mapper, JdbcTemplate jdbcTemplate) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Despesa save(Despesa despesa) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(despesa)));
    }

    @Override
    public Optional<Despesa> findById(Integer cdDespesa) {
        return jpaRepository.findById(cdDespesa).map(mapper::toDomain);
    }

    @Override
    public List<DespesaResponse> findAllByFiltro(DespesaFiltro filtro) {
        StringBuilder sql = new StringBuilder("""
                SELECT d.cd_despesa, d.cd_categoria_despesa, c.nm_categoria, d.ds_descricao, d.vl_despesa,
                       d.st_despesa, d.dt_vencimento, d.dt_pagamento, d.ds_observacoes, d.dt_cadastro
                  FROM despesa d
                  JOIN categoria_despesa c ON c.cd_categoria_despesa = d.cd_categoria_despesa
                 WHERE 1 = 1
                """);
        List<Object> params = new ArrayList<>();
        if (filtro.cdCategoriaDespesa() != null) {
            sql.append(" AND d.cd_categoria_despesa = ?");
            params.add(filtro.cdCategoriaDespesa());
        }
        if (filtro.status() != null) {
            sql.append(" AND d.st_despesa = ?");
            params.add(filtro.status());
        }
        if (filtro.dtInicio() != null) {
            sql.append(" AND d.dt_vencimento >= ?");
            params.add(filtro.dtInicio());
        }
        if (filtro.dtFim() != null) {
            sql.append(" AND d.dt_vencimento <= ?");
            params.add(filtro.dtFim());
        }
        sql.append(" ORDER BY d.dt_vencimento ASC, d.cd_despesa ASC");

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> new DespesaResponse(
                rs.getInt("cd_despesa"),
                rs.getInt("cd_categoria_despesa"),
                rs.getString("nm_categoria"),
                rs.getString("ds_descricao"),
                rs.getBigDecimal("vl_despesa"),
                StatusDespesaEnum.fromCodigo(rs.getObject("st_despesa", Integer.class)),
                rs.getObject("dt_vencimento", LocalDate.class),
                toLocalDateTime(rs.getTimestamp("dt_pagamento")),
                rs.getString("ds_observacoes"),
                toLocalDateTime(rs.getTimestamp("dt_cadastro"))
        ));
    }

    @Override
    public void deleteById(Integer cdDespesa) {
        jpaRepository.deleteById(cdDespesa);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}
