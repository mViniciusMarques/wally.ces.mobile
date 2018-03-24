package solutions.empire.wallyces.model;

import com.parse.ParseClassName;

/**
 * Created by mviniciusmarques on 22/03/18.
 */

@ParseClassName("aviso")
public class Aviso {

    private String objectId;
    private String professor_id;
    private String tipo_aviso;
    private String prioridade_aviso;
    private String descricao_aviso;

    public Aviso(String objectId, String professor_id, String tipo_aviso, String prioridade_aviso, String descricao_aviso) {
        this.objectId = objectId;
        this.professor_id = professor_id;
        this.tipo_aviso = tipo_aviso;
        this.prioridade_aviso = prioridade_aviso;
        this.descricao_aviso = descricao_aviso;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getProfessor_id() {
        return professor_id;
    }

    public void setProfessor_id(String professor_id) {
        this.professor_id = professor_id;
    }

    public String getTipo_aviso() {
        return tipo_aviso;
    }

    public void setTipo_aviso(String tipo_aviso) {
        this.tipo_aviso = tipo_aviso;
    }

    public String getPrioridade_aviso() {
        return prioridade_aviso;
    }

    public void setPrioridade_aviso(String prioridade_aviso) {
        this.prioridade_aviso = prioridade_aviso;
    }

    public String getDescricao_aviso() {
        return descricao_aviso;
    }

    public void setDescricao_aviso(String descricao_aviso) {
        this.descricao_aviso = descricao_aviso;
    }
}
