package solutions.empire.wallyces.view

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import bolts.Task
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.inc_cartao_professor.*
import solutions.empire.wallyces.R
import com.parse.FindCallback
import com.parse.ParseException
import kotlinx.android.synthetic.main.activity_quadro_professor.*
import kotlinx.android.synthetic.main.inc_aviso_professor.*
import kotlinx.android.synthetic.main.inc_busca_professor.*
import kotlinx.android.synthetic.main.inc_cadastro_cartao.*
import kotlinx.android.synthetic.main.inc_resultado_busca.*


class QuadroProfessorActivity : AppCompatActivity() {


    var nomeProfessor= "";
    var professor: ParseObject? = null;
    var aviso: ParseObject? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quadro_professor)
        obterDados();
        obterProfessorBuscado()
        if (intent.getStringExtra("nome_professor") == null ){
            QuadroProfessorService().execute()
        }

    }


    fun obterDados() {

       val nome  = intent.getStringExtra("nome_professor")
       val sala =  intent.getStringExtra("sala")
       val horario = intent.getStringExtra("horario")

        input_disciplina_qp.setText(nome);
        sala_professor_qp.setText(sala);
        input_horario_qp.setText(horario);

    }

    fun obterProfessorBuscado() {
        nomeProfessor = intent.getStringExtra("professor_buscado")
        val query  = ParseQuery.getQuery<ParseObject>("professor");
        query.whereEqualTo("nome", nomeProfessor);

    }


    inner class QuadroProfessorService() : AsyncTask<Void, Void, String>(){

        override fun doInBackground(vararg params: Void?): String? {
            //obterAvisoProfessor()
            //obterProfessor()
            obterOcorrencia()
            return null
        }

        override fun onPreExecute() {
            super.onPreExecute()
            loading_qp.visibility = View.VISIBLE;
            loading_qp.bringToFront();
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            loading_qp.visibility = View.GONE;
        }

        fun obterOcorrencia() {
            val query = ParseQuery<ParseObject>("Ocorrencia")
            query.whereEqualTo("nome",nomeProfessor)
            query.findInBackground { retorno, parseException ->
                if (parseException == null) {
                    professor = retorno.first();
                    nome_professor_qp.setText(nomeProfessor)
                    data_publicacao_qp.setText(retorno.first().getString("createdAt"))
                    input_disciplina_qp.setText(retorno.first().getString("disciplina"));
                    sala_professor_qp.setText(retorno.first().getString("sala"));
                    input_horario_qp.setText(retorno.first().getString("horario"));
                    txt_aviso_qp.setText(retorno.first().getString("aviso"))
                } else {
                    // Toast.makeText(applicationContext,"Erro ao obter disciplinas, verifique sua conexão!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


}
