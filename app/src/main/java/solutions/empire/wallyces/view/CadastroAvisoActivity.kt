package solutions.empire.wallyces.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import com.parse.ParseObject
import kotlinx.android.synthetic.main.activity_cadastro_aviso.*
import kotlinx.android.synthetic.main.inc_formulario_cadastro_aviso.*
import solutions.empire.wallyces.R
import solutions.empire.wallyces.core.BaseActivity

class CadastroAvisoActivity : BaseActivity() {

    var prefs: SharedPreferences? = null
    val PREFS_NAME = "repositorio_local"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_aviso)

        prefs = this.getSharedPreferences(PREFS_NAME,0)
        salvarAvisoNormal()
    }

    private fun salvarAvisoNormal() {
        btn_cadastrar_aviso.setOnClickListener {
            if(this.validarCamposInput()) {
                this.construirAvisoParaSalvar().saveInBackground().onSuccess {
                    var intent = Intent(this, AvisoActivity::class.java)
                    intent.putExtra("tipo_usuario", prefs?.getString("tipo_usuario",""))
                    startActivity(intent)
                    Toast.makeText(this, "Aviso salvo com sucesso", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show()
            }
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
        avisoParse.put("professor_id", prefs?.getString("professor_nome",""))
        return avisoParse
    }

    private fun validarCamposInput(): Boolean {
        if(titulo_cadastro_aviso.text.toString() == ""
                        && descricao_cadastro_aviso.text.toString() == "") {
                    return false
                }
            return true
    }


}
