package br.com.cds.graciedietcontrol.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Refeicoes implements Serializable {

    private long idRefeicao;
    private TipoRefeicao tipoRefeicao;
    private Integer refeicaoValida;
    private List<Alimentos> alimentos;
    private Date dataRefeicao;

    public long getIdRefeicao() {
        return idRefeicao;
    }

    public TipoRefeicao getTipoRefeicao() {
        return tipoRefeicao;
    }

    public Integer getRefeicaoValida() {
        return refeicaoValida;
    }

    public List<Alimentos> getAlimentos() {
        return alimentos;
    }

    public Date getDataRefeicao() {
        return dataRefeicao;
    }

    public void setIdRefeicao(long idRefeicao) {
        this.idRefeicao = idRefeicao;
    }

    public void setTipoRefeicao(TipoRefeicao tipoRefeicao) {
        this.tipoRefeicao = tipoRefeicao;
    }

    public void setRefeicaoValida(Integer refeicaoValida) {
        this.refeicaoValida = refeicaoValida;
    }

    public void setAlimentos(List<Alimentos> alimentos) {
        this.alimentos = alimentos;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
