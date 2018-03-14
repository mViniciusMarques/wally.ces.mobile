package solutions.empire.wallyces.view

import android.os.Bundle
import kotlinx.android.synthetic.main.inc_busca_professor.*
import android.widget.ArrayAdapter
import android.content.Intent
import android.os.AsyncTask
import android.widget.Toast
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_buscar_professor.*
import solutions.empire.wallyces.R
import solutions.empire.wallyces.core.BaseActivity


class BuscarProfessorActivity : BaseActivity() {


    var professoresRetornados: MutableList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_professor)

        //obterProfessoresAutoComplete()
        ProfessorService().execute()
        buscarProfessor()
        sair()
    }


    fun buscarProfessor() {
        btn_buscar_professor.setOnClickListener {
            startActivity( Intent(this, QuadroProfessorActivity:: class.java));
        }
    }


     fun sair() {
        this.btn_sair.setOnClickListener { view ->
            this.deslogar();
        }

    }



    inner class ProfessorService() : AsyncTask<Void, Void, String>(){

        override fun doInBackground(vararg params: Void?): String? {
            obterProfessores()
            return null
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            obterProfessorAutoComplete();
        }

        fun obterProfessores() {
            val query = ParseQuery<ParseObject>("professor")
            query.findInBackground(object : FindCallback<ParseObject> {
                override fun done(retorno: List<ParseObject>, parseException: ParseException?) {
                    if (parseException == null) {
                        retorno.forEach { parseObject: ParseObject ->
                            professoresRetornados.add(parseObject.getString("nome"))
                        }
                    } else {
                        Toast.makeText(applicationContext,"Erro ao obter disciplinas, verifique sua conexão!", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

        fun obterProfessorAutoComplete() {

            val adapter = ArrayAdapter( applicationContext, android.R.layout.simple_spinner_dropdown_item, professoresRetornados)
            auto_input_nome_professor.setAdapter(adapter)

        }
    }

}


