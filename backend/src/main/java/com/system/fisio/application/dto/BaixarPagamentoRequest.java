package com.system.fisio.application.dto;

import com.system.fisio.domain.enums.FormaPagamentoEnum;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaixarPagamentoRequest {
    private FormaPagamentoEnum formaPagamento;
    private LocalDateTime dtPagamento;
}
