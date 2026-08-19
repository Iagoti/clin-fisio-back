package com.system.fisio.application.dto;

import com.system.fisio.domain.enums.AtivoInativoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDespesaRequest {
    private Integer cdCategoriaDespesa;
    private String nmCategoria;
    private AtivoInativoEnum stCategoria;
}
