package com.system.fisio.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pacote_fisioterapia")
@Getter
@Setter
@NoArgsConstructor
public class PacoteFisioterapiaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_pacote")
    private Integer cdPacote;

    @Column(name = "cd_paciente", nullable = false)
    private Integer cdPaciente;

    @Column(name = "qt_sessoes_total", nullable = false)
    private Integer qtSessoesTotal;

    @Column(name = "qt_sessoes_consumidas", nullable = false)
    private Integer qtSessoesConsumidas;

    @Column(name = "vl_pacote", nullable = false)
    private BigDecimal valor;

    @Column(name = "dt_inicio", nullable = false)
    private LocalDate dtInicio;

    @Column(name = "dt_conclusao")
    private LocalDate dtConclusao;

    @Column(name = "st_pacote")
    private Integer status;

    @Column(name = "dt_cadastro")
    private LocalDateTime dtCadastro;

    @PrePersist
    private void aplicarPadroes() {
        if (status == null) {
            status = 1;
        }
        if (dtCadastro == null) {
            dtCadastro = LocalDateTime.now();
        }
    }
}
