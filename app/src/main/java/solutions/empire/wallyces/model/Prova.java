package solutions.empire.wallyces.model;

import java.util.Date;

/**
 * Created by mviniciusmarques on 25/04/18.
 */

public class Prova {

    private String nomeProva;
    private String descricaoProva;
    private Date dataProva;
    private String prioridadeProva;
    private Integer valorProva;
    private String alunoId;

    public Prova(String nomeProva, String descricaoProva, Date dataProva, String prioridadeProva, Integer valorProva, String alunoId ) {
        this.nomeProva = nomeProva;
        this.descricaoProva = descricaoProva;
        this.dataProva = dataProva;
        this.prioridadeProva = prioridadeProva;
        this.valorProva = valorProva;
        this.alunoId = alunoId;
    }

    public Prova() {
    }

    public String getNomeProva() {
        return nomeProva;
    }

    public void setNomeProva(String nomeProva) {
        this.nomeProva = nomeProva;
    }

    public String getDescricaoProva() {
        return descricaoProva;
    }

    public void setDescricaoProva(String descricaoProva) {
        this.descricaoProva = descricaoProva;
    }

    public Date getDataProva() {
        return dataProva;
    }

    public void setDataProva(Date dataProva) {
        this.dataProva = dataProva;
    }

    public String getPrioridadeProva() {
        return prioridadeProva;
    }

    public void setPrioridadeProva(String prioridadeProva) {
        this.prioridadeProva = prioridadeProva;
    }

    public Integer getValorProva() {
        return valorProva;
    }

    public void setValorProva(Integer valorProva) {
        this.valorProva = valorProva;
    }

    public String getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(String alunoId) {
        this.alunoId = alunoId;
    }
}
