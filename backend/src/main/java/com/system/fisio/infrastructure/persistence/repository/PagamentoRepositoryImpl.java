package com.system.fisio.infrastructure.persistence.repository;

import com.system.fisio.application.dto.PagamentoFiltro;
import com.system.fisio.application.dto.PagamentoResponse;
import com.system.fisio.domain.enums.FormaPagamentoEnum;
import com.system.fisio.domain.enums.StatusPagamentoEnum;
import com.system.fisio.domain.model.Pagamento;
import com.system.fisio.domain.ports.IPagamentoRepository;
import com.system.fisio.infrastructure.persistence.mapper.PagamentoPersistenceMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PagamentoRepositoryImpl implements IPagamentoRepository {

    private final PagamentoJpaRepository jpaRepository;
    private final PagamentoPersistenceMapper mapper;
    private final JdbcTemplate jdbcTemplate;

    public PagamentoRepositoryImpl(PagamentoJpaRepository jpaRepository, PagamentoPersistenceMapper mapper, JdbcTemplate jdbcTemplate) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Pagamento save(Pagamento pagamento) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(pagamento)));
    }

    @Override
    public Optional<Pagamento> findById(Integer cdPagamento) {
        return jpaRepository.findById(cdPagamento).map(mapper::toDomain);
    }

    @Override
    public List<PagamentoResponse> findAllByFiltro(PagamentoFiltro filtro) {
        StringBuilder sql = new StringBuilder("""
                SELECT pg.cd_pagamento, pg.cd_agendamento, pg.cd_pacote, pg.cd_paciente, p.nm_paciente,
                       pg.vl_pagamento, pg.tp_forma_pagamento, pg.st_pagamento, pg.dt_vencimento,
                       pg.dt_pagamento, pg.ds_observacoes, pg.dt_cadastro
                  FROM pagamento pg
                  JOIN paciente p ON p.cd_paciente = pg.cd_paciente
                 WHERE 1 = 1
                """);
        List<Object> params = new ArrayList<>();
        if (filtro.cdPaciente() != null) {
            sql.append(" AND pg.cd_paciente = ?");
            params.add(filtro.cdPaciente());
        }
        if (filtro.status() != null) {
            sql.append(" AND pg.st_pagamento = ?");
            params.add(filtro.status());
        }
        if (filtro.dtInicio() != null) {
            sql.append(" AND pg.dt_vencimento >= ?");
            params.add(filtro.dtInicio());
        }
        if (filtro.dtFim() != null) {
            sql.append(" AND pg.dt_vencimento <= ?");
            params.add(filtro.dtFim());
        }
        sql.append(" ORDER BY pg.dt_vencimento ASC NULLS LAST, pg.cd_pagamento ASC");

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> new PagamentoResponse(
                rs.getInt("cd_pagamento"),
                (Integer) rs.getObject("cd_agendamento"),
                (Integer) rs.getObject("cd_pacote"),
                rs.getInt("cd_paciente"),
                rs.getString("nm_paciente"),
                rs.getBigDecimal("vl_pagamento"),
                FormaPagamentoEnum.fromCodigo(rs.getObject("tp_forma_pagamento", Integer.class)),
                StatusPagamentoEnum.fromCodigo(rs.getObject("st_pagamento", Integer.class)),
                rs.getObject("dt_vencimento", LocalDate.class),
                toLocalDateTime(rs.getTimestamp("dt_pagamento")),
                rs.getString("ds_observacoes"),
                toLocalDateTime(rs.getTimestamp("dt_cadastro"))
        ));
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}
