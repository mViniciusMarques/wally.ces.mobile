package solutions.empire.wallyces.model;

/**
 * Created by mviniciusmarques on 20/04/18.
 */

public class Aluno {

    private String nome;
    private String email;
    private String curso;
    private String objectId;

    public Aluno(String nome, String email, String curso, String objectId) {
        this.nome = nome;
        this.email = email;
        this.curso = curso;
        this.objectId = objectId;
    }

    public Aluno() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
