package br.com.cds.graciedietcontrol.model;

import java.io.Serializable;

public class Alimentos implements Serializable {

    private long idAlimento;
    private String nome;
    private long idSubGrupo;

    @Override
    public String toString() {
        return nome;
    }

    public long getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(long idAlimento) {
        this.idAlimento = idAlimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getIdSubGrupo() {
        return idSubGrupo;
    }

    public void setIdSubGrupo(long idSubGrupo) {
        this.idSubGrupo = idSubGrupo;
    }
}
