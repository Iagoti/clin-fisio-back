package com.system.fisio.application.dto;

import com.system.fisio.domain.enums.AtivoInativoEnum;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDespesaResponse {
    private Integer cdCategoriaDespesa;
    private String nmCategoria;
    private AtivoInativoEnum stCategoria;
    private LocalDateTime dtCadastro;
}
