package com.system.fisio.infrastructure.persistence.repository;

import com.system.fisio.application.dto.PacienteFiltro;
import com.system.fisio.application.dto.PacienteResponse;
import com.system.fisio.application.mapper.PacienteMapper;
import com.system.fisio.domain.enums.AtivoInativoEnum;
import com.system.fisio.domain.model.Paciente;
import com.system.fisio.domain.ports.IPacienteRepository;
import com.system.fisio.infrastructure.persistence.entity.PacienteEntity;
import com.system.fisio.infrastructure.persistence.mapper.PacientePersistenceMapper;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PacienteRepositoryImpl implements IPacienteRepository {

    private final PacienteJpaRepository jpaRepository;
    private final PacientePersistenceMapper persistenceMapper;
    private final PacienteMapper pacienteMapper;
    private final JdbcTemplate jdbcTemplate;

    public PacienteRepositoryImpl(
            PacienteJpaRepository jpaRepository,
            PacientePersistenceMapper persistenceMapper,
            PacienteMapper pacienteMapper,
            JdbcTemplate jdbcTemplate
    ) {
        this.jpaRepository = jpaRepository;
        this.persistenceMapper = persistenceMapper;
        this.pacienteMapper = pacienteMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Paciente save(Paciente paciente) {
        PacienteEntity saved = jpaRepository.save(persistenceMapper.toEntity(paciente));
        return persistenceMapper.toDomain(saved);
    }

    @Override
    public List<PacienteResponse> findAllByFiltro(PacienteFiltro filtro) {
        String nome = filtro.nmPaciente();
        String cpf = apenasDigitos(filtro.cpf());
        Integer ativo = filtro.pacienteAtivo();
        StringBuilder sql = new StringBuilder("""
                SELECT cd_paciente, nm_paciente, nr_cpf, nr_celular, ds_cidade,
                       COALESCE(st_paciente, 1) AS st_paciente, dt_cadastro
                  FROM paciente
                 WHERE 1 = 1
                """);
        List<Object> params = new ArrayList<>();

        if (nome != null && !nome.isBlank()) {
            sql.append(" AND LOWER(nm_paciente) LIKE LOWER(?)");
            params.add("%" + nome.trim() + "%");
        }
        if (cpf != null) {
            sql.append(" AND regexp_replace(COALESCE(nr_cpf, ''), '\\D', '', 'g') LIKE ?");
            params.add("%" + cpf + "%");
        }
        if (ativo != null) {
            sql.append(" AND COALESCE(st_paciente, 1) = ?");
            params.add(ativo);
        }

        sql.append(" ORDER BY nm_paciente ASC");

        return jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> resumoPaciente(
                        rs.getInt("cd_paciente"),
                        rs.getString("nm_paciente"),
                        rs.getString("nr_cpf"),
                        rs.getString("nr_celular"),
                        rs.getString("ds_cidade"),
                        AtivoInativoEnum.fromCodigo(rs.getObject("st_paciente", Integer.class)),
                        rs.getTimestamp("dt_cadastro")
                )
        );
    }

    private String apenasDigitos(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        return valor.replaceAll("\\D", "");
    }

    @Override
    public Optional<Paciente> findById(Integer cdPaciente) {
        return jpaRepository.findById(cdPaciente).map(persistenceMapper::toDomain);
    }

    @Override
    public void deleteById(Integer cdPaciente) {
        jpaRepository.deleteById(cdPaciente);
    }

    private PacienteResponse resumoPaciente(
            Integer cdPaciente,
            String nome,
            String cpf,
            String celular,
            String cidade,
            AtivoInativoEnum status,
            Timestamp dtCadastro
    ) {
        PacienteResponse response = new PacienteResponse();
        response.setCdPaciente(cdPaciente);
        response.setNmPaciente(nome);
        response.setCpf(cpf);
        response.setCelular(celular);
        response.setCidade(cidade);
        response.setStPaciente(status);
        response.setDtCadastro(dtCadastro != null ? dtCadastro.toLocalDateTime() : null);
        return response;
    }
}
