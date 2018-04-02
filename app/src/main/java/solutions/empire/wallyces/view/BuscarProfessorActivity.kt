package solutions.empire.wallyces.view

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.adroitandroid.chipcloud.ChipCloud
import com.adroitandroid.chipcloud.ChipListener
import com.adroitandroid.chipcloud.FlowLayout
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_buscar_professor.*
import kotlinx.android.synthetic.main.inc_busca_professor.*
import kotlinx.android.synthetic.main.inc_chips.*
import solutions.empire.wallyces.R
import solutions.empire.wallyces.core.BaseActivity


class BuscarProfessorActivity : BaseActivity() {

    var professoresRetornados: MutableList<String> = arrayListOf()
    var professorSelecionado: String = ""
    var chips: MutableList<String> = arrayListOf()
    var translate: MutableList<String> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_professor)

        buscarProfessor()
        obterProfessorSelecionado()
        ProfessorService().execute()

        inicializarChips()
        desativarEdicaoDeCamposEAtivarLoading()
    }

    private fun inicializarChips() {
        chip_cloud.setGravity(FlowLayout.Gravity.STAGGERED)
        chip_cloud.setMode(ChipCloud.Mode.SINGLE)
        chip_cloud.setChipListener(object : ChipListener {
            override fun chipSelected(index: Int) {
                Log.e("PERL", chips[index])
                lbl_chip_disciplina_bp.text = translate[index]
            }

            override fun chipDeselected(index: Int) {
                lbl_chip_disciplina_bp.text = "CES/JF - 2018"
            }
        })
    }

    private fun buscarProfessor() {
            btn_buscar_professor.setOnClickListener {
                if(validarCampoBusca()) {
                intent = Intent(this, QuadroProfessorActivity::class.java)
                intent.putExtra("professor_buscado", professorSelecionado)
                startActivity(intent)
            } else {
                    Toast.makeText(this,"Campo de busca precisa ser preenchido", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun obterProfessorSelecionado() {
        auto_input_nome_professor.onItemClickListener = AdapterView.OnItemClickListener { _, _, posicao, _ ->
            professorSelecionado = auto_input_nome_professor.adapter.getItem(posicao).toString()
            Log.e("PROFESSOR", auto_input_nome_professor.adapter.getItem(posicao).toString())
        }
    }

    private fun ativarEdicaoDeCamposEDesativarLoading() {
        auto_input_nome_professor.isEnabled = true
        btn_buscar_professor.isEnabled = true
        loadingBuscarProfessor.visibility = View.GONE
    }

    private fun desativarEdicaoDeCamposEAtivarLoading() {
        loadingBuscarProfessor.visibility = View.VISIBLE
        loadingBuscarProfessor.isIndeterminate = true
        auto_input_nome_professor.isEnabled = false
        btn_buscar_professor.isEnabled = false
    }

    private fun validarCampoBusca(): Boolean {
        if(!TextUtils.isEmpty(auto_input_nome_professor.text)){
           return true
        }
        return false
    }


    inner class ProfessorService : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void?): String? {
            obterProfessores()
            obterChips()
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            obterProfessorAutoComplete()
        }

        private fun obterProfessores() {
            val query = ParseQuery<ParseObject>("professor")
            query.findInBackground { retorno, _ ->
                retorno.forEach { parseObject: ParseObject ->
                    professoresRetornados.add(parseObject.getString("nome"))
                }
                ativarEdicaoDeCamposEDesativarLoading()
            }
        }

       private  fun obterProfessorAutoComplete() {
            val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, professoresRetornados)
            auto_input_nome_professor.setAdapter(adapter)
        }

        private fun obterChips() {
            val ocorrenciaQuery = ParseQuery<ParseObject>("Ocorrencia")
            val disciplinaaQuery = ParseQuery<ParseObject>("disciplina")
            val r =  disciplinaaQuery.whereMatchesKeyInQuery("nome","disciplina",ocorrenciaQuery )
            r. findInBackground { objects, _ ->
                objects.forEach { parseObject: ParseObject? ->
                    Log.e("IDWK", parseObject?.getString("nome") + " - " + parseObject?.getString("label"))
                    translate.add(parseObject?.getString("nome").toString())
                    chips.add(parseObject?.getString("label")!!)
                }
                chip_cloud.addChips(chips.toTypedArray())
            }

        }
    }
}

