package solutions.empire.wallyces.view

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.inc_cartao_professor.*
import solutions.empire.wallyces.R
import kotlinx.android.synthetic.main.activity_quadro_professor.*
import kotlinx.android.synthetic.main.inc_aviso_professor.*
import kotlinx.android.synthetic.main.inc_resultado_busca.*


class QuadroProfessorActivity : AppCompatActivity() {


    var nomeProfessor= ""
    var professor: ParseObject? = null
    var aviso: ParseObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quadro_professor)
        obterDados()
        obterProfessorBuscado()
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

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
        }

        fun obterOcorrencia() {
            val query = ParseQuery<ParseObject>("Ocorrencia")
            query.whereEqualTo("nome",nomeProfessor)
            query.findInBackground { retorno, parseException ->
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
