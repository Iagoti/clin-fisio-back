package com.system.fisio.infrastructure.persistence.repository;

import com.system.fisio.infrastructure.persistence.entity.AgendamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoJpaRepository extends JpaRepository<AgendamentoEntity, Integer> {
}
