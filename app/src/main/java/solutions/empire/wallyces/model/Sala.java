package solutions.empire.wallyces.model;

import com.parse.ParseClassName;

/**
 * Created by mviniciusmarques on 22/03/18.
 */

@ParseClassName("sala")
public class Sala {

    private String objectId;
    private String nome;
    private String predio;
    private String andar;

    public Sala(String objectId, String nome, String predio, String andar) {
        this.objectId = objectId;
        this.nome = nome;
        this.predio = predio;
        this.andar = andar;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPredio() {
        return predio;
    }

    public void setPredio(String predio) {
        this.predio = predio;
    }

    public String getAndar() {
        return andar;
    }

    public void setAndar(String andar) {
        this.andar = andar;
    }
}
