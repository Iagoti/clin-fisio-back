package com.system.fisio.infrastructure.persistence.repository;

import com.system.fisio.infrastructure.persistence.entity.DespesaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaJpaRepository extends JpaRepository<DespesaEntity, Integer> {
}
