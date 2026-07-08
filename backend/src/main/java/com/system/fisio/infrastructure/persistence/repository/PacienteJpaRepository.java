package com.system.fisio.infrastructure.persistence.repository;

import com.system.fisio.infrastructure.persistence.entity.PacienteEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteJpaRepository extends JpaRepository<PacienteEntity, Integer> {
    List<PacienteEntity> findAllByOrderByNmPacienteAsc();
}
