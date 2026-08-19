package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.DeleteDespesaResponse;
import com.system.fisio.domain.exception.DespesaException;
import com.system.fisio.domain.ports.IDespesaRepository;
import org.springframework.stereotype.Component;

@Component
public class DeletarDespesaUseCase {

    private final IDespesaRepository despesaRepository;

    public DeletarDespesaUseCase(IDespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    public DeleteDespesaResponse execute(Integer cdDespesa) {
        despesaRepository.findById(cdDespesa)
                .orElseThrow(() -> new DespesaException("Despesa não encontrada"));
        despesaRepository.deleteById(cdDespesa);
        return new DeleteDespesaResponse(cdDespesa, "Despesa excluída com sucesso");
    }
}
