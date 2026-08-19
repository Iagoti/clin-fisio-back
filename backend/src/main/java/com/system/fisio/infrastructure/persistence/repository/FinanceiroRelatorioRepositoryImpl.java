package com.system.fisio.infrastructure.persistence.repository;

import com.system.fisio.application.dto.DashboardFinanceiroResponse;
import com.system.fisio.application.dto.FluxoCaixaPontoResponse;
import com.system.fisio.domain.enums.StatusDespesaEnum;
import com.system.fisio.domain.enums.StatusPagamentoEnum;
import com.system.fisio.domain.ports.IFinanceiroRelatorioRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FinanceiroRelatorioRepositoryImpl implements IFinanceiroRelatorioRepository {

    private final JdbcTemplate jdbcTemplate;

    public FinanceiroRelatorioRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<FluxoCaixaPontoResponse> gerarFluxoCaixa(LocalDate dataInicio, LocalDate dataFim) {
        Map<LocalDate, BigDecimal> entradasPorDia = somarPorDia(
                "SELECT dt_pagamento::date AS dia, SUM(vl_pagamento) AS total FROM pagamento " +
                        "WHERE st_pagamento = ? AND dt_pagamento::date BETWEEN ? AND ? GROUP BY dt_pagamento::date",
                StatusPagamentoEnum.PAGO.getCodigo(), dataInicio, dataFim
        );
        Map<LocalDate, BigDecimal> saidasPorDia = somarPorDia(
                "SELECT dt_pagamento::date AS dia, SUM(vl_despesa) AS total FROM despesa " +
                        "WHERE st_despesa = ? AND dt_pagamento::date BETWEEN ? AND ? GROUP BY dt_pagamento::date",
                StatusDespesaEnum.PAGO.getCodigo(), dataInicio, dataFim
        );

        List<FluxoCaixaPontoResponse> pontos = new ArrayList<>();
        BigDecimal saldoAcumulado = BigDecimal.ZERO;
        for (LocalDate dia = dataInicio; !dia.isAfter(dataFim); dia = dia.plusDays(1)) {
            BigDecimal entradas = entradasPorDia.getOrDefault(dia, BigDecimal.ZERO);
            BigDecimal saidas = saidasPorDia.getOrDefault(dia, BigDecimal.ZERO);
            BigDecimal saldoDia = entradas.subtract(saidas);
            saldoAcumulado = saldoAcumulado.add(saldoDia);
            pontos.add(new FluxoCaixaPontoResponse(dia, entradas, saidas, saldoDia, saldoAcumulado));
        }
        return pontos;
    }

    @Override
    public DashboardFinanceiroResponse gerarDashboard() {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioMes = hoje.withDayOfMonth(1);
        LocalDate fimMes = hoje.withDayOfMonth(hoje.lengthOfMonth());

        BigDecimal totalAReceber = queryScalar(
                "SELECT COALESCE(SUM(vl_pagamento),0) FROM pagamento WHERE st_pagamento = ?",
                StatusPagamentoEnum.PENDENTE.getCodigo()
        );
        BigDecimal recebidoNoMes = queryScalar(
                "SELECT COALESCE(SUM(vl_pagamento),0) FROM pagamento WHERE st_pagamento = ? AND dt_pagamento::date BETWEEN ? AND ?",
                StatusPagamentoEnum.PAGO.getCodigo(), inicioMes, fimMes
        );
        BigDecimal totalAPagar = queryScalar(
                "SELECT COALESCE(SUM(vl_despesa),0) FROM despesa WHERE st_despesa = ?",
                StatusDespesaEnum.PENDENTE.getCodigo()
        );
        BigDecimal pagoNoMes = queryScalar(
                "SELECT COALESCE(SUM(vl_despesa),0) FROM despesa WHERE st_despesa = ? AND dt_pagamento::date BETWEEN ? AND ?",
                StatusDespesaEnum.PAGO.getCodigo(), inicioMes, fimMes
        );
        BigDecimal inadimplencia = queryScalar(
                "SELECT COALESCE(SUM(vl_pagamento),0) FROM pagamento WHERE st_pagamento = ? AND dt_vencimento < ?",
                StatusPagamentoEnum.PENDENTE.getCodigo(), hoje
        );
        BigDecimal saldoPeriodo = recebidoNoMes.subtract(pagoNoMes);

        return new DashboardFinanceiroResponse(totalAReceber, recebidoNoMes, totalAPagar, pagoNoMes, saldoPeriodo, inadimplencia);
    }

    private Map<LocalDate, BigDecimal> somarPorDia(String sql, Object... params) {
        return jdbcTemplate.query(sql, rs -> {
            Map<LocalDate, BigDecimal> resultado = new HashMap<>();
            while (rs.next()) {
                resultado.put(rs.getObject("dia", LocalDate.class), rs.getBigDecimal("total"));
            }
            return resultado;
        }, params);
    }

    private BigDecimal queryScalar(String sql, Object... params) {
        BigDecimal resultado = jdbcTemplate.queryForObject(sql, BigDecimal.class, params);
        return resultado != null ? resultado : BigDecimal.ZERO;
    }
}
