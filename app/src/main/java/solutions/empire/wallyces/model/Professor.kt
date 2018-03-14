package solutions.empire.wallyces.model

/**
 * Created by mviniciusmarques on 10/03/18.
 */
open class Professor constructor(curso: String, login: String, senha: String, nome: String, matricula: String) : Pessoa(nome, matricula) {

    var curso: String;
    var login: String;
    var senha: String;

    init {
        this.curso = curso;
        this.login = login;
        this.senha = senha;
    }
}