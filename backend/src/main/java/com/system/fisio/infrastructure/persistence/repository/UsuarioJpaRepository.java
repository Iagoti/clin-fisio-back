package com.system.fisio.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.system.fisio.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import com.system.fisio.infrastructure.persistence.entity.UsuarioEntity;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Integer> {
    Optional<UsuarioEntity> findByEmail(String email);
    List<UsuarioEntity> findAll();
}
