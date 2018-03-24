package solutions.empire.wallyces.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.FindCallback
import com.parse.ParseException
import kotlinx.android.synthetic.main.activity_cadastro_professor.*
import kotlinx.android.synthetic.main.inc_cadastro_cartao.*
import solutions.empire.wallyces.R
import solutions.empire.wallyces.model.CadastroSalaProfessorDTO
import solutions.empire.wallyces.model.Ocorrencia


class CadastroProfessorActivity : AppCompatActivity()  {

    var disciplinasRetornadas: MutableList<String> = arrayListOf()
    val horarios = ArrayList<String>()
    val salas = ArrayList<String>()
    var ocorrencia: Ocorrencia = Ocorrencia("undefinied","","","","","")
    var prefs: SharedPreferences? = null
    val PREFS_NAME = "repositorio_local"
    var nome: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_professor)
        Disciplina().execute()
        // this.spinnerHorario();
        this.spinnerSala()
        this.inicializarAviso()
        this.inicializarHorario()
        this.obterDisciplinaSelecionada();

        this.obterHorarioMatutino()
        this.obterHorarioVespertino()
        this.obterHorarioNoturno()

        prefs = this.getSharedPreferences(PREFS_NAME,0)
        var p = prefs!!.getString("professor_nome","")
        // nome_professor.setText("Olá, " + prefs!!.getString("professor_nome",""))

        var s = p.split(" ")
        this.nome = p
        Log.e("Mario", s[0])

        nome_professor.text = "Olá, " + s[0]

        this.obterHorarioSelecionado()
        this.obterSalaSelecionada()

        this.salvar()

    }

    private fun exibirAviso() {
       if( !seletor_aviso.isChecked ) {
           lbl_aviso.visibility = View.GONE
           input_aviso_cp.visibility = View.GONE
       } else {
           lbl_aviso.visibility = View.VISIBLE
           input_aviso_cp.visibility = View.VISIBLE
       }
    }

    private fun exibirHorario() {
      //  if( !turno_manha.isChecked || !turno_tarde.isChecked || !turno_noite.isChecked ) {
        if( !radio_turno.isDirty) {
            lbl_horarios.visibility = View.GONE
            dropdown_horarios.visibility = View.GONE
        } else {
            lbl_horarios.visibility = View.VISIBLE
            dropdown_horarios.visibility = View.VISIBLE
        }
    }

    private fun inicializarHorario() {
        lbl_horarios.visibility = View.GONE
        dropdown_horarios.visibility = View.GONE
        this.exibirHorario()
    }

    private fun inicializarAviso() {
        lbl_aviso.visibility = View.GONE
        input_aviso_cp.visibility = View.GONE
        seletor_aviso.setOnClickListener {
            this.exibirAviso()
        }
    }


    private fun spinnerSala() {

        salas.add("Selecione...")
        salas.add("Prédio - Lab I - Laboratório 1")
        salas.add("Prédio - Lab 6 - Laboratório 6")
        salas.add("Academia - Sala - 307")
        salas.add("Academia - Sala - 312")


        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, salas)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        dropdown_sala.adapter = dataAdapter
    }

    private fun obterHorarioMatutino() {
        turno_manha.setOnClickListener {
            this.horarios.clear()
            horarios.add("Selecione...")
            horarios.add("07:30 - 08:50")
            horarios.add("09:10 - 10:30")
            horarios.add("10:50 - 12:30")

            val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, horarios)
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            this.exibirHorario()
            dropdown_horarios.adapter = dataAdapter
        }
    }

    private fun obterHorarioVespertino() {
        turno_tarde.setOnClickListener {
            this.horarios.clear()
            horarios.add("Selecione...")
            horarios.add("17:10 - 18:50")


            val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, horarios)
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            this.exibirHorario()
            dropdown_horarios.adapter = dataAdapter
        }
    }

    private fun obterHorarioNoturno() {
        turno_noite.setOnClickListener { view ->
            this.horarios.clear()
            horarios.add("Selecione...")
            horarios.add("18:50 - 20:30")
            horarios.add("20:50 - 22:30")

            val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, horarios)
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            this.exibirHorario()
            dropdown_horarios.adapter = dataAdapter

        }
    }

    private fun obterHorarioSelecionado() {
        dropdown_horarios.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posicao: Int, id: Long) {
                ocorrencia?.horario = horarios[posicao]
            }
        }
    }

    private fun obterSalaSelecionada() {
        dropdown_sala.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posicao: Int, id: Long) {
               ocorrencia?.sala = salas[posicao]

            }
        }
    }

    private fun montarOcorrenciaParaSalvar(): ParseObject {
        val ocorrenciaParse = ParseObject("Ocorrencia")
        ocorrenciaParse.put("nome", this.nome!!)
        ocorrenciaParse.put("sala", ocorrencia?.sala)
        ocorrenciaParse.put("aviso", ocorrencia?.aviso)
        ocorrenciaParse.put("horario", ocorrencia?.horario)
        ocorrenciaParse.put("disciplina", ocorrencia?.disciplina)

        return ocorrenciaParse
    }


    private fun salvar() {
        salvar_cp.setOnClickListener { view ->
            this.montarOcorrenciaParaSalvar().saveInBackground()
            Toast.makeText(applicationContext,"Registro salvo com sucesso!", Toast.LENGTH_SHORT).show()
        }

    }

    fun obterDisciplinaSelecionada() {
        disciplina_professor.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, posicao, id ->
            ocorrencia.disciplina = disciplina_professor.adapter.getItem(posicao).toString()
        }
    }

    inner class Disciplina : AsyncTask<Void, Void, String>(){

        override fun doInBackground(vararg params: Void?): String? {
            obterDisciplinas()
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            obterDiscAutoComplete()
        }

        private fun obterDisciplinas() {
            val query = ParseQuery<ParseObject>("disciplina")
            query.findInBackground { retorno, parseException ->
                if (parseException == null) {
                    retorno.forEach { parseObject: ParseObject ->
                        disciplinasRetornadas.add(parseObject.getString("nome"))
                    }
                } else {
                    Toast.makeText(applicationContext,"Erro ao obter disciplinas, verifique sua conexão!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun obterDiscAutoComplete() {

            val adapter = ArrayAdapter( applicationContext, android.R.layout.simple_list_item_1, disciplinasRetornadas)
            disciplina_professor.setAdapter(adapter)

        }
    }
}


