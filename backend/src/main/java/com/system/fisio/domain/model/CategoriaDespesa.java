package com.system.fisio.domain.model;

import com.system.fisio.domain.enums.AtivoInativoEnum;
import com.system.fisio.domain.exception.CategoriaDespesaException;

import java.time.LocalDateTime;

public class CategoriaDespesa {

    private final Integer cdCategoriaDespesa;
    private String nmCategoria;
    private AtivoInativoEnum stCategoria;
    private final LocalDateTime dtCadastro;

    public CategoriaDespesa(
            Integer cdCategoriaDespesa,
            String nmCategoria,
            AtivoInativoEnum stCategoria,
            LocalDateTime dtCadastro
    ) {
        this.cdCategoriaDespesa = cdCategoriaDespesa;
        this.nmCategoria = nmCategoria;
        this.stCategoria = stCategoria != null ? stCategoria : AtivoInativoEnum.ATIVO;
        this.dtCadastro = dtCadastro != null ? dtCadastro : LocalDateTime.now();
        validar();
    }

    private void validar() {
        if (nmCategoria == null || nmCategoria.isBlank()) {
            throw new CategoriaDespesaException("Nome da categoria é obrigatório");
        }
    }

    public void atualizarDados(String nmCategoria, AtivoInativoEnum stCategoria) {
        this.nmCategoria = nmCategoria;
        this.stCategoria = stCategoria != null ? stCategoria : this.stCategoria;
        validar();
    }

    public Integer getCdCategoriaDespesa() { return cdCategoriaDespesa; }
    public String getNmCategoria() { return nmCategoria; }
    public AtivoInativoEnum getStCategoria() { return stCategoria; }
    public LocalDateTime getDtCadastro() { return dtCadastro; }
}
