package com.system.fisio.infrastructure.persistence.repository;

import com.system.fisio.application.dto.AgendamentoFiltro;
import com.system.fisio.application.dto.AgendamentoResponse;
import com.system.fisio.domain.enums.StatusAgendamentoEnum;
import com.system.fisio.domain.enums.TipoAtendimentoEnum;
import com.system.fisio.domain.model.Agendamento;
import com.system.fisio.domain.ports.IAgendamentoRepository;
import com.system.fisio.infrastructure.persistence.entity.AgendamentoEntity;
import com.system.fisio.infrastructure.persistence.mapper.AgendamentoPersistenceMapper;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AgendamentoRepositoryImpl implements IAgendamentoRepository {

    private final AgendamentoJpaRepository jpaRepository;
    private final AgendamentoPersistenceMapper persistenceMapper;
    private final JdbcTemplate jdbcTemplate;

    public AgendamentoRepositoryImpl(
            AgendamentoJpaRepository jpaRepository,
            AgendamentoPersistenceMapper persistenceMapper,
            JdbcTemplate jdbcTemplate
    ) {
        this.jpaRepository = jpaRepository;
        this.persistenceMapper = persistenceMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Agendamento save(Agendamento agendamento) {
        AgendamentoEntity saved = jpaRepository.save(persistenceMapper.toEntity(agendamento));
        return persistenceMapper.toDomain(saved);
    }

    @Override
    public List<AgendamentoResponse> findAllByFiltro(AgendamentoFiltro filtro) {
        StringBuilder sql = new StringBuilder("""
                SELECT a.cd_agendamento, a.cd_paciente, p.nm_paciente, a.tipo_atendimento,
                       a.dt_agendamento, a.hr_agendamento, a.status, a.vl_agendamento,
                       a.ds_observacoes, a.dt_cadastro
                  FROM agendamento a
                  JOIN paciente p ON p.cd_paciente = a.cd_paciente
                 WHERE 1 = 1
                """);
        List<Object> params = new ArrayList<>();

        if (filtro.dataAgendamento() != null) {
            sql.append(" AND a.dt_agendamento = ?");
            params.add(filtro.dataAgendamento());
        }
        if (filtro.nmPaciente() != null && !filtro.nmPaciente().isBlank()) {
            sql.append(" AND LOWER(p.nm_paciente) LIKE LOWER(?)");
            params.add("%" + filtro.nmPaciente().trim() + "%");
        }
        if (filtro.cdPaciente() != null) {
            sql.append(" AND a.cd_paciente = ?");
            params.add(filtro.cdPaciente());
        }
        if (filtro.tipoAtendimento() != null) {
            sql.append(" AND a.tipo_atendimento = ?");
            params.add(filtro.tipoAtendimento());
        }
        if (filtro.status() != null) {
            sql.append(" AND a.status = ?");
            params.add(filtro.status());
        }

        sql.append(" ORDER BY a.dt_agendamento ASC, a.hr_agendamento ASC");

        return jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> new AgendamentoResponse(
                        rs.getInt("cd_agendamento"),
                        rs.getInt("cd_paciente"),
                        rs.getString("nm_paciente"),
                        TipoAtendimentoEnum.fromCodigo(rs.getObject("tipo_atendimento", Integer.class)),
                        rs.getObject("dt_agendamento", LocalDate.class),
                        rs.getObject("hr_agendamento", LocalTime.class),
                        StatusAgendamentoEnum.fromCodigo(rs.getObject("status", Integer.class)),
                        rs.getBigDecimal("vl_agendamento"),
                        rs.getString("ds_observacoes"),
                        toLocalDateTime(rs.getTimestamp("dt_cadastro"))
                )
        );
    }

    @Override
    public Optional<Agendamento> findById(Integer cdAgendamento) {
        return jpaRepository.findById(cdAgendamento).map(persistenceMapper::toDomain);
    }

    @Override
    public void deleteById(Integer cdAgendamento) {
        jpaRepository.deleteById(cdAgendamento);
    }

    @Override
    public boolean existeConflito(
            LocalDate dataAgendamento,
            LocalTime horaAgendamento,
            TipoAtendimentoEnum tipoAtendimento,
            Integer cdPaciente,
            Integer cdAgendamentoIgnorar
    ) {
        StringBuilder sql = new StringBuilder("""
                SELECT COUNT(*)
                  FROM agendamento
                 WHERE dt_agendamento = ?
                   AND hr_agendamento = ?
                   AND status <> ?
                   AND (tipo_atendimento = ? OR cd_paciente = ?)
                """);
        List<Object> params = new ArrayList<>(List.of(
                dataAgendamento,
                horaAgendamento,
                StatusAgendamentoEnum.CANCELADO.getCodigo(),
                tipoAtendimento.getCodigo(),
                cdPaciente
        ));

        if (cdAgendamentoIgnorar != null) {
            sql.append(" AND cd_agendamento <> ?");
            params.add(cdAgendamentoIgnorar);
        }

        Integer total = jdbcTemplate.queryForObject(sql.toString(), params.toArray(), Integer.class);
        return total != null && total > 0;
    }

    @Override
    public long contarReposicoesSemAtestadoNoMes(Integer cdPaciente, LocalDate inicioMes, LocalDate fimMes) {
        String sql = """
                SELECT COUNT(*)
                  FROM agendamento r
                  JOIN agendamento o ON o.cd_agendamento = r.cd_agendamento_origem_falta
                 WHERE o.cd_paciente = ?
                   AND o.fl_com_atestado = false
                   AND o.dt_agendamento BETWEEN ? AND ?
                   AND r.status <> ?
                """;
        Long total = jdbcTemplate.queryForObject(
                sql, Long.class, cdPaciente, inicioMes, fimMes, StatusAgendamentoEnum.CANCELADO.getCodigo()
        );
        return total != null ? total : 0L;
    }

    private java.time.LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}
