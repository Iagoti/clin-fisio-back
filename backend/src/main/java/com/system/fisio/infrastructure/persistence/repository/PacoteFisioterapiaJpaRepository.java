package com.system.fisio.infrastructure.persistence.repository;

import com.system.fisio.infrastructure.persistence.entity.PacoteFisioterapiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacoteFisioterapiaJpaRepository extends JpaRepository<PacoteFisioterapiaEntity, Integer> {
}
