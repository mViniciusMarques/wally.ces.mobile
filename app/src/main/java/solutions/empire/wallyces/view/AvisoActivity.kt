package solutions.empire.wallyces.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_aviso.*
import kotlinx.android.synthetic.main.activity_buscar_professor.*
import solutions.empire.wallyces.R
import solutions.empire.wallyces.adapter.AvisoAdapter
import solutions.empire.wallyces.core.BaseActivity
import solutions.empire.wallyces.dbParseTable.AvisoTableEnum
import solutions.empire.wallyces.model.AvisoCard
import java.text.SimpleDateFormat


class AvisoActivity : BaseActivity() {

    val PREFS_NAME = "repositorio_local"
    var prefs: SharedPreferences? = null
    var avisos: MutableList<AvisoCard> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aviso)

        val manager = LinearLayoutManager(applicationContext)
        manager.orientation = LinearLayoutManager.VERTICAL
        recycleAviso.layoutManager = manager

        val actionBar = supportActionBar
        actionBar!!.hide()

        QuadroProfessorService().execute()

        loadingAvisos.isIndeterminate = true
        loadingAvisos.visibility = View.VISIBLE



        recycleAviso.adapter = AvisoAdapter(applicationContext, avisos)
        prefs = this.getSharedPreferences(PREFS_NAME,0)
        redirecionarParaAdicionarAviso()
        renderizarBotaoAvisoComPermissao()
    }

    private fun renderizarBotaoAvisoComPermissao() {
        adicionarAviso.visibility = View.GONE
        if(prefs?.getString("tipo_usuario","").equals("P")){
            adicionarAviso.bringToFront()
            adicionarAviso.visibility = View.VISIBLE
        }
    }

    private fun redirecionarParaAdicionarAviso() {
        adicionarAviso.bringToFront()
        adicionarAviso.setOnClickListener {
            startActivity(Intent(view.context, CadastroAvisoActivity::class.java))
        }
    }

    inner class QuadroProfessorService : AsyncTask<Void, Void, String>(){

        override fun doInBackground(vararg params: Void?): String? {
            obterAvisosEmBackgroundSetarItensNaTela()
            return null
        }

        fun obterProfessorAviso() {
            val avisoQuery = ParseQuery<ParseObject>(AvisoTableEnum.TABELA_AVISO.toString())
            val professorQuery = ParseQuery<ParseObject>("professor")
            avisoQuery.whereExists("professor_id")
            avisoQuery.orderByAscending("prioridade_aviso")
            val r =  professorQuery.whereMatchesKeyInQuery("objectId","professor_id",avisoQuery)
            r.findInBackground { objects, _ ->
                objects.forEach { parseObject: ParseObject? ->
                    Log.e("Definition_of_insanity", parseObject?.getString("nome"))
                }
            }
        }

        private fun obterAvisosEmBackgroundSetarItensNaTela() {
            val avisoQuery = ParseQuery<ParseObject>(AvisoTableEnum.TABELA_AVISO.toString())
            avisoQuery.orderByAscending(AvisoTableEnum.PRIORIDADE.toString())
            avisoQuery.findInBackground { objects, _ ->
                objects.forEach { parseObject: ParseObject? ->
                    construirParametrosDaTela(parseObject)
                }
                recycleAviso.adapter = AvisoAdapter(applicationContext, avisos)
                loadingAvisos.visibility = View.GONE
            }
        }

        private fun construirParametrosDaTela(parseObject: ParseObject?) {
            var aviso = AvisoCard()
            val format = SimpleDateFormat(getString(R.string.PADRAO_DATA_PT_BR))
            aviso.titulo = parseObject?.getString(AvisoTableEnum.TITULO.toString())
            aviso.descricao = parseObject?.getString(AvisoTableEnum.DESCRICAO.toString())
            aviso.dataCriacao = format.format(parseObject?.createdAt!!)
            aviso.professor = parseObject.getString(AvisoTableEnum.PROFESSOR_ID.toString())
            avisos.add(aviso)
        }


    }

}
