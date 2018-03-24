package solutions.empire.wallyces.model

import com.parse.ParseClassName

/**
 * Created by mviniciusmarques on 10/03/18.
 */
@ParseClassName("professor")
open class Professor {

     var objectId: String
     var nome: String
     var permissao: String
     var curso: String
     var email: String

    constructor(objectId: String, nome: String, permissao: String, curso: String, email: String) {
        this.objectId = objectId
        this.nome = nome
        this.permissao = permissao
        this.curso = curso
        this.email = email
    }


}