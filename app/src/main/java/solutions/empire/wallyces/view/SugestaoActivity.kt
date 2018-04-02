package solutions.empire.wallyces.view

import android.content.SharedPreferences
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sugestao.*
import solutions.empire.wallyces.R
import solutions.empire.wallyces.core.BaseActivity
import solutions.empire.wallyces.model.Sugestao

class SugestaoActivity : BaseActivity() {

    val PREFS_NAME = "repositorio_local"
    var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sugestao)

        prefs = this.getSharedPreferences(PREFS_NAME,0)

        val actionBar = supportActionBar
        actionBar!!.hide()
    }

    private fun salvarNoFirebase() {

    }

    private fun construirSalvarComParametros(): Sugestao {
        var sugestaoParaSalvar = Sugestao()
        sugestaoParaSalvar.titulo =  titulo_sugestao_sb.text.toString()
        sugestaoParaSalvar.modeloCelular =  modelo_celular_sb.text.toString()
        sugestaoParaSalvar.checkLayout = chk_layout.isChecked
        sugestaoParaSalvar.checkPerfomance = chk_perfomance.isChecked
        sugestaoParaSalvar.checkInconsistencia = chk_inconsistencia.isChecked
        sugestaoParaSalvar.checkDisponibilidade = chk_disponibilidade.isChecked
        return sugestaoParaSalvar
    }


}
