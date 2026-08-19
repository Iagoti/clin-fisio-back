package com.system.fisio.infrastructure.persistence.repository;

import com.system.fisio.application.dto.PacoteFiltro;
import com.system.fisio.application.dto.PacoteResponse;
import com.system.fisio.domain.enums.StatusPacoteEnum;
import com.system.fisio.domain.model.PacoteFisioterapia;
import com.system.fisio.domain.ports.IPacoteFisioterapiaRepository;
import com.system.fisio.infrastructure.persistence.mapper.PacoteFisioterapiaPersistenceMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PacoteFisioterapiaRepositoryImpl implements IPacoteFisioterapiaRepository {

    private final PacoteFisioterapiaJpaRepository jpaRepository;
    private final PacoteFisioterapiaPersistenceMapper mapper;
    private final JdbcTemplate jdbcTemplate;

    public PacoteFisioterapiaRepositoryImpl(
            PacoteFisioterapiaJpaRepository jpaRepository,
            PacoteFisioterapiaPersistenceMapper mapper,
            JdbcTemplate jdbcTemplate
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PacoteFisioterapia save(PacoteFisioterapia pacote) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(pacote)));
    }

    @Override
    public Optional<PacoteFisioterapia> findById(Integer cdPacote) {
        return jpaRepository.findById(cdPacote).map(mapper::toDomain);
    }

    @Override
    public Optional<PacoteFisioterapia> findAtivoComSessaoDisponivel(Integer cdPaciente) {
        String sql = """
                SELECT cd_pacote FROM pacote_fisioterapia
                 WHERE cd_paciente = ? AND st_pacote = 1 AND qt_sessoes_consumidas < qt_sessoes_total
                 ORDER BY dt_inicio ASC
                 LIMIT 1
                """;
        List<Integer> ids = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("cd_pacote"), cdPaciente);
        return ids.isEmpty() ? Optional.empty() : findById(ids.get(0));
    }

    @Override
    public List<PacoteResponse> findAllByFiltro(PacoteFiltro filtro) {
        StringBuilder sql = new StringBuilder("""
                SELECT pf.cd_pacote, pf.cd_paciente, p.nm_paciente, pf.qt_sessoes_total,
                       pf.qt_sessoes_consumidas, pf.vl_pacote, pf.dt_inicio, pf.dt_conclusao,
                       pf.st_pacote, pf.dt_cadastro
                  FROM pacote_fisioterapia pf
                  JOIN paciente p ON p.cd_paciente = pf.cd_paciente
                 WHERE 1 = 1
                """);
        List<Object> params = new ArrayList<>();
        if (filtro.cdPaciente() != null) {
            sql.append(" AND pf.cd_paciente = ?");
            params.add(filtro.cdPaciente());
        }
        if (filtro.status() != null) {
            sql.append(" AND pf.st_pacote = ?");
            params.add(filtro.status());
        }
        sql.append(" ORDER BY pf.dt_inicio DESC");

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> {
            int total = rs.getInt("qt_sessoes_total");
            int consumidas = rs.getInt("qt_sessoes_consumidas");
            return new PacoteResponse(
                    rs.getInt("cd_pacote"),
                    rs.getInt("cd_paciente"),
                    rs.getString("nm_paciente"),
                    total,
                    consumidas,
                    Math.max(0, total - consumidas),
                    rs.getBigDecimal("vl_pacote"),
                    rs.getObject("dt_inicio", LocalDate.class),
                    rs.getObject("dt_conclusao", LocalDate.class),
                    StatusPacoteEnum.fromCodigo(rs.getObject("st_pacote", Integer.class)),
                    toLocalDateTime(rs.getTimestamp("dt_cadastro"))
            );
        });
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}
