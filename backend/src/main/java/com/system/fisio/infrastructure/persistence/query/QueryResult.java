package com.system.fisio.infrastructure.persistence.query;

import java.util.List;

public class QueryResult {
    private final StringBuilder sql;
    private final List<Object> params;

    public QueryResult(StringBuilder sql, List<Object> params) {
        this.sql = sql;
        this.params = params;
    }

    public StringBuilder getSql() { return sql; }
    public List<Object> getParams() { return params; }
}
