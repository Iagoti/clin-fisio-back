package com.system.fisio.infrastructure.persistence.repository;

import com.system.fisio.infrastructure.persistence.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoJpaRepository extends JpaRepository<PagamentoEntity, Integer> {
}
