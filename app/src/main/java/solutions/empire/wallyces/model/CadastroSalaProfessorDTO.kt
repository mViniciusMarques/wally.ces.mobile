package solutions.empire.wallyces.model

/**
 * Created by mviniciusmarques on 11/03/18.
 */
class CadastroSalaProfessorDTO {

    var sala: String;
    var horario: String;
    var exibirAviso: Boolean;
    var aviso: String;

    constructor(sala: String, horario: String, exibirAviso: Boolean, aviso: String) {
        this.sala = sala
        this.horario = horario
        this.exibirAviso = exibirAviso
        this.aviso = aviso
    }


}