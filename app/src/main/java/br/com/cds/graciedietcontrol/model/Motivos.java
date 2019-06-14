package br.com.cds.graciedietcontrol.model;

import java.io.Serializable;

public class Motivos implements Serializable {

    private long idMotivo;
    private String codMotivo;
    private String motivo;

    public long getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(long idMotivo) {
        this.idMotivo = idMotivo;
    }

    public String getCodMotivo() {
        return codMotivo;
    }

    public void setCodMotivo(String codMotivo) {
        this.codMotivo = codMotivo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
