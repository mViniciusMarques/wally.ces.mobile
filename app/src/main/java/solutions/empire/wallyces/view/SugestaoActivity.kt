package solutions.empire.wallyces.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.parse.ParseObject
import kotlinx.android.synthetic.main.activity_sugestao.*
import solutions.empire.wallyces.R
import solutions.empire.wallyces.core.BaseActivity
import solutions.empire.wallyces.model.Sugestao

class SugestaoActivity : BaseActivity() {

    var sugestaoParse: ParseObject = ParseObject("sugestao")
    val PREFS_NAME = "repositorio_local"
    var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sugestao)

        prefs = this.getSharedPreferences(PREFS_NAME,0)

        val actionBar = supportActionBar
        actionBar!!.hide()

        this.salvarSugestao()
    }

    private fun construirSalvarComParametros(): Sugestao {
        var sugestaoParaSalvar = Sugestao()
        sugestaoParaSalvar.descricao = descircao_sb.text.toString()
        sugestaoParaSalvar.titulo =  titulo_sugestao_sb.text.toString()
        sugestaoParaSalvar.modeloCelular =  modelo_celular_sb.text.toString()
        sugestaoParaSalvar.checkLayout = chk_layout.isChecked
        sugestaoParaSalvar.checkPerfomance = chk_perfomance.isChecked
        sugestaoParaSalvar.checkInconsistencia = chk_inconsistencia.isChecked
        sugestaoParaSalvar.checkDisponibilidade = chk_disponibilidade.isChecked
        return sugestaoParaSalvar
    }

    private fun salvarSugestao() {
        btn_notificar_bug_sb.setOnClickListener {
            val sugestao = construirSalvarComParametros()
            this.sugestaoParse.put("titulo", sugestao.titulo)
            this.sugestaoParse.put("descricao", sugestao.descricao)
            this.sugestaoParse.put("check_layout", sugestao.checkLayout)
            this.sugestaoParse.put("modelo_celular", sugestao.modeloCelular)
            this.sugestaoParse.put("check_perfomance", sugestao.checkPerfomance)
            this.sugestaoParse.put("check_inconsistencia", sugestao.checkInconsistencia)
            this.sugestaoParse.put("check_disponibilidade", sugestao.checkDisponibilidade)
            this.sugestaoParse.saveInBackground().onSuccess {
                var intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra("tipo_usuario", prefs?.getString("tipo_usuario",""))
                startActivity(intent)
            }
        }
    }


}
