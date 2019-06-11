package br.com.cds.graciedietcontrol.model;

import java.io.Serializable;

public class SubGrupos implements Serializable {
    private long idSubGrupo;
    private String nome;
    private long idGrupo;

    @Override
    public String toString() {
        return nome;
    }

    public long getIdSubGrupo() {
        return idSubGrupo;
    }

    public void setIdSubGrupo(long idSubGrupo) {
        this.idSubGrupo = idSubGrupo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(long idGrupo) {
        this.idGrupo = idGrupo;
    }
}
