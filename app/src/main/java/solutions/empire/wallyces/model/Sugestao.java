package solutions.empire.wallyces.model;

/**
 * Created by mvmarques2 on 02/04/2018.
 */

public class Sugestao {

    private String titulo;
    private String modeloCelular;
    private Boolean checkLayout;
    private Boolean checkPerfomance;
    private Boolean checkInconsistencia;
    private Boolean checkDisponibilidade;
    private String descricao;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getModeloCelular() {
        return modeloCelular;
    }

    public void setModeloCelular(String modeloCelular) {
        this.modeloCelular = modeloCelular;
    }

    public Boolean getCheckLayout() {
        return checkLayout;
    }

    public void setCheckLayout(Boolean checkLayout) {
        this.checkLayout = checkLayout;
    }

    public Boolean getCheckPerfomance() {
        return checkPerfomance;
    }

    public void setCheckPerfomance(Boolean checkPerfomance) {
        this.checkPerfomance = checkPerfomance;
    }

    public Boolean getCheckInconsistencia() {
        return checkInconsistencia;
    }

    public void setCheckInconsistencia(Boolean checkInconsistencia) {
        this.checkInconsistencia = checkInconsistencia;
    }

    public Boolean getCheckDisponibilidade() {
        return checkDisponibilidade;
    }

    public void setCheckDisponibilidade(Boolean checkDisponibilidade) {
        this.checkDisponibilidade = checkDisponibilidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
