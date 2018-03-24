package solutions.empire.wallyces.model

import com.parse.ParseClassName

/**
 * Created by mviniciusmarques on 10/03/18.
 */
@ParseClassName("Ocorrencia")
class Ocorrencia {

    var objectId: String
    var nome: String
    var disciplina: String
    var horario: String
    var sala: String
    var aviso: String

    constructor(objectId: String?, nome: String?, disciplina: String?, horario: String?, sala: String?, aviso: String?) {
        this.objectId = objectId!!
        this.nome = nome!!
        this.disciplina = disciplina!!
        this.horario = horario!!
        this.sala = sala!!
        this.aviso = aviso!!
    }

}