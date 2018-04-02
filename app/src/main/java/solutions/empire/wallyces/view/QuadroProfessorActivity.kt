package solutions.empire.wallyces.view

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_quadro_professor.*
import kotlinx.android.synthetic.main.inc_aviso_professor.*
import kotlinx.android.synthetic.main.inc_cartao_professor.*
import kotlinx.android.synthetic.main.inc_resultado_busca.*
import solutions.empire.wallyces.R
import solutions.empire.wallyces.core.BaseActivity


class QuadroProfessorActivity : BaseActivity() {


    var nomeProfessor= ""
    var professor: ParseObject? = null
    var aviso: ParseObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quadro_professor)
        obterDados()
        obterProfessorBuscado()
        validarEntradaSemProfessor()
        QuadroProfessorService().execute()
    }


    fun obterDados() {

       val nome  = intent.getStringExtra("nome_professor")
       val sala =  intent.getStringExtra("sala")
       val horario = intent.getStringExtra("horario")

        input_disciplina_qp.text = nome
        sala_professor_qp.text = sala
        input_horario_qp.text = horario

    }

    fun obterProfessorBuscado() {
        nomeProfessor = intent.getStringExtra("professor_buscado")
        val query  = ParseQuery.getQuery<ParseObject>("professor")
        query.whereEqualTo("nome", nomeProfessor)

    }

    private fun validarEntradaSemProfessor() {
        if(TextUtils.isEmpty(nomeProfessor)){
            startActivity(Intent(this, DashboardActivity::class.java))
            Toast.makeText(this, "É necessário entrar com um professor válido", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.limparTela()
    }

    private fun limparTela() {
        nome_professor_qp.text = ""
        data_publicacao_qp.text =""
        input_disciplina_qp.text = ""
        sala_professor_qp.text = ""
        txt_aviso_qp.text = ""
    }


    inner class QuadroProfessorService : AsyncTask<Void, Void, String>(){

        override fun doInBackground(vararg params: Void?): String? {
            obterOcorrencia()
            return null
        }

        override fun onPreExecute() {
            super.onPreExecute()
            loading_qp.visibility = View.VISIBLE
            loading_qp.isIndeterminate = true
        }

        fun obterOcorrencia() {
            val ocorrenciaQuery = ParseQuery<ParseObject>("Ocorrencia")
            ocorrenciaQuery.whereEqualTo("nome",nomeProfessor)
            ocorrenciaQuery.orderByDescending("createdAt")
            ocorrenciaQuery.findInBackground { retorno, parseException ->
                if (parseException == null) {
                    popularCamposComDadosRetornados(retorno)
                } else {
                    // Toast.makeText(applicationContext,"Erro ao obter disciplinas, verifique sua conexão!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun popularCamposComDadosRetornados(retornoParse: MutableList<ParseObject>) {
            professor = retornoParse.first()
            nome_professor_qp.text = nomeProfessor
            data_publicacao_qp.text = retornoParse.first().getString("createdAt")
            input_disciplina_qp.text = retornoParse.first().getString("disciplina")
            sala_professor_qp.text = retornoParse.first().getString("sala")
            input_horario_qp.text = retornoParse.first().getString("horario")
            txt_aviso_qp.text = retornoParse.first().getString("aviso")
            loading_qp.visibility = View.GONE
        }

    }


}
