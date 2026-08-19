package com.system.fisio.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categoria_despesa")
@Getter
@Setter
@NoArgsConstructor
public class CategoriaDespesaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_categoria_despesa")
    private Integer cdCategoriaDespesa;

    @Column(name = "nm_categoria", nullable = false)
    private String nmCategoria;

    @Column(name = "st_categoria")
    private Integer stCategoria;

    @Column(name = "dt_cadastro")
    private LocalDateTime dtCadastro;

    @PrePersist
    private void aplicarPadroes() {
        if (stCategoria == null) {
            stCategoria = 1;
        }
        if (dtCadastro == null) {
            dtCadastro = LocalDateTime.now();
        }
    }
}
