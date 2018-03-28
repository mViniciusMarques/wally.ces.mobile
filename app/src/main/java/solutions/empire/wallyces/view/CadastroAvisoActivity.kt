package solutions.empire.wallyces.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.parse.ParseObject
import kotlinx.android.synthetic.main.activity_cadastro_aviso.*
import kotlinx.android.synthetic.main.inc_formulario_cadastro_aviso.*
import solutions.empire.wallyces.R

class CadastroAvisoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_aviso)

        salvarAvisoNormal()
    }

    private fun salvarAvisoNormal() {
        btn_cadastrar_aviso.setOnClickListener {
            this.construirAvisoParaSalvar().saveInBackground()
        }
    }

    private fun salvarAvisoPrioritario() {
        btn_cadastrar_aviso.setOnClickListener {
            construirAvisoParaSalvar()
        }
    }

    private fun construirAvisoParaSalvar(): ParseObject {
        val avisoParse = ParseObject("aviso")
        avisoParse.put("titulo", titulo_cadastro_aviso.text.toString())
        avisoParse.put("descricao", descricao_cadastro_aviso.text.toString())
        avisoParse.put("professor_id", "")
        return avisoParse
    }


}
