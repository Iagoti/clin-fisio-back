package com.system.fisio.infrastructure.persistence.repository;

import com.system.fisio.infrastructure.persistence.entity.CategoriaDespesaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaDespesaJpaRepository extends JpaRepository<CategoriaDespesaEntity, Integer> {
}
