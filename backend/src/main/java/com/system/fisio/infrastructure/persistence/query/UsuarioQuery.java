package com.system.fisio.infrastructure.persistence.query;

import com.system.fisio.application.dto.UsuarioFiltro;
import com.system.fisio.infrastructure.ports.IUsuarioQuery;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioQuery implements IUsuarioQuery {

    @Override
    public QueryResult findAllByFiltro(UsuarioFiltro filtro) {

        StringBuilder sql = new StringBuilder(
                "SELECT cd_usuario, nm_usuario, email, login, st_usuario, tp_usuario, dt_cadastro " +
                        "FROM usuario WHERE 1=1");

        List<Object> params = new ArrayList<>();
        if (filtro.getNmUsuario() != null && !filtro.getNmUsuario().isBlank()) {
            sql.append(" AND LOWER(nm_usuario) ILIKE ? ");
            params.add("%" + filtro.getNmUsuario().toLowerCase() + "%");
        }
        if (filtro.getUsuarioAtivo() != null) {
            sql.append(" AND st_usuario = ? ");
            params.add(filtro.getUsuarioAtivo());
        }
        return new QueryResult(sql, params);
    }
}
