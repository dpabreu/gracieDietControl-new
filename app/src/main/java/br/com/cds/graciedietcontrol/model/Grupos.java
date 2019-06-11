package br.com.cds.graciedietcontrol.model;

import java.io.Serializable;

public class Grupos implements Serializable {

    private long idGrupo;
    private String nome;

    @Override
    public String toString() {
        return nome;
    }

    public long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(long idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
