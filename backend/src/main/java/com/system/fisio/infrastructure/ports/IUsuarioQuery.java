package com.system.fisio.infrastructure.ports;

import com.system.fisio.application.dto.UsuarioFiltro;
import com.system.fisio.infrastructure.persistence.query.QueryResult;

public interface IUsuarioQuery {

    QueryResult findAllByFiltro(UsuarioFiltro filtro);
}
