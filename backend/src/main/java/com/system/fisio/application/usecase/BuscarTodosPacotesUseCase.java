package com.system.fisio.application.usecase;

import com.system.fisio.application.dto.PacoteFiltro;
import com.system.fisio.application.dto.PacoteResponse;
import com.system.fisio.domain.ports.IPacoteFisioterapiaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuscarTodosPacotesUseCase {

    private final IPacoteFisioterapiaRepository pacoteRepository;

    public BuscarTodosPacotesUseCase(IPacoteFisioterapiaRepository pacoteRepository) {
        this.pacoteRepository = pacoteRepository;
    }

    public List<PacoteResponse> execute(PacoteFiltro filtro) {
        return pacoteRepository.findAllByFiltro(filtro);
    }
}
