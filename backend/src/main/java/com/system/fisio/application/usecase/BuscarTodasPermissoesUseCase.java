package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.PermissaoResponse;
import com.system.fisio.domain.ports.IPermissaoRepository;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class BuscarTodasPermissoesUseCase {

    private final IPermissaoRepository permissaoRepository;

    public BuscarTodasPermissoesUseCase(IPermissaoRepository permissaoRepository) {
        this.permissaoRepository = permissaoRepository;
    }

    public List<PermissaoResponse> execute() {
        return permissaoRepository.findAll().stream()
                .map(p -> new PermissaoResponse(p.getCdPermissao(), p.getCdChave(), p.getDsPermissao(), p.getNmModulo()))
                .sorted(Comparator.comparing(PermissaoResponse::nmModulo).thenComparing(PermissaoResponse::cdChave))
                .toList();
    }
}
