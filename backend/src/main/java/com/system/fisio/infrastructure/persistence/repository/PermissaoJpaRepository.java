package com.system.fisio.infrastructure.persistence.repository;

import com.system.fisio.infrastructure.persistence.entity.PermissaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissaoJpaRepository extends JpaRepository<PermissaoEntity, Integer> {
}
