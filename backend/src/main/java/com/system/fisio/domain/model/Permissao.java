package com.system.fisio.domain.model;

public class Permissao {

    private final Integer cdPermissao;
    private final String cdChave;
    private final String dsPermissao;
    private final String nmModulo;

    public Permissao(Integer cdPermissao, String cdChave, String dsPermissao, String nmModulo) {
        this.cdPermissao = cdPermissao;
        this.cdChave = cdChave;
        this.dsPermissao = dsPermissao;
        this.nmModulo = nmModulo;
    }

    public Integer getCdPermissao() {
        return cdPermissao;
    }

    public String getCdChave() {
        return cdChave;
    }

    public String getDsPermissao() {
        return dsPermissao;
    }

    public String getNmModulo() {
        return nmModulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Permissao that)) return false;
        return cdChave != null && cdChave.equals(that.cdChave);
    }

    @Override
    public int hashCode() {
        return cdChave != null ? cdChave.hashCode() : 0;
    }
}
