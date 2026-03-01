package com.system.fisio.application.dto;

public class UsuarioFiltro {
    private String nmUsuario;
    private Integer usuarioAtivo;

    public UsuarioFiltro(String nmUsuario, Integer usuarioAtivo) {
        this.nmUsuario = nmUsuario;
        this.usuarioAtivo = usuarioAtivo;
    }
    public String getNmUsuario() {
        return nmUsuario;
    }

    public void setNmUsuario(String nmUsuario) {
        this.nmUsuario = nmUsuario;
    }

    public Integer getUsuarioAtivo() {
        return usuarioAtivo;
    }

    public void setUsuarioAtivo(Integer usuarioAtivo) {
        this.usuarioAtivo = usuarioAtivo;
    }
}
