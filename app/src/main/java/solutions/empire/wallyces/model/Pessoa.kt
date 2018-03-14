package solutions.empire.wallyces.model

import com.parse.ParseClassName

/**
 * Created by mviniciusmarques on 08/03/18.
 */

@ParseClassName("Pessoa")
open class Pessoa constructor(nome: String, matricula: String) {
    var nome: String;
    var matricula: String;

    init {
        this.nome = nome
        this.matricula = matricula
    }
}