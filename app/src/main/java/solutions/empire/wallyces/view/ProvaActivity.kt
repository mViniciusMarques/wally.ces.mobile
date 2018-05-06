package solutions.empire.wallyces.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_prova.*
import kotlinx.android.synthetic.main.inc_prova_destaque.*
import solutions.empire.wallyces.R
import solutions.empire.wallyces.adapter.ProvaAdapter
import solutions.empire.wallyces.interfaces.RecycleViewOnClickListenerHack
import solutions.empire.wallyces.model.Prova
import java.text.SimpleDateFormat


class ProvaActivity : AppCompatActivity(), RecycleViewOnClickListenerHack {

    val PREFS_NAME = "repositorio_local"
    var prefs: SharedPreferences? = null
    var provas: MutableList<Prova> = arrayListOf()
    var possuiItemParaTransferir : Boolean = false
    var provaParaTransferir: Prova = Prova()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prova)

        this.possuiItemParaTransferir = false


        prefs = this.getSharedPreferences(PREFS_NAME,0)

        val manager = LinearLayoutManager(applicationContext)
        manager.orientation = LinearLayoutManager.VERTICAL
        recycleProva.layoutManager = manager

        var recycle  = ProvaAdapter(applicationContext, provas)

        recycle.setmRecycleViewOnClickListenerHack(this)

        recycleProva.adapter = recycle

        acaoBotaoTransferir()

        btn_excluir_prova.setOnClickListener {
            startActivity(Intent(this, ConsumidorQrCodeActivity::class.java))
        }

        ProvaService().execute()

    }

    override fun onClickListener(view: View?, position: Int) {
        renderizarNoCartaoItemSelecionado(position)
    }

    private fun acaoBotaoTransferir() {
        btn_transferir_prova.setOnClickListener {
            this.transferirProva()
        }
    }

    private fun renderizarNoCartaoItemSelecionado(position: Int) {
        val format = SimpleDateFormat(getString(R.string.PADRAO_DATA_PT_BR))

        titulo_prova_destaque.text = this.provas[position].nomeProva
        descricao_prova_destaque.text = this.provas[position].descricaoProva
        valor_prova_destaque.text = this.provas[position].valorProva.toString()
        data_prova_destaque.text = format.format(this.provas[position].dataProva)

        this.provaParaTransferir = this.provas[position]
        this.possuiItemParaTransferir = true

    }

    private fun transferirProva() {
        val format = SimpleDateFormat(getString(R.string.PADRAO_DATA_PT_BR))
        if (this.possuiItemParaTransferir ) {
            var intent = Intent(this, TransferenciaActivity::class.java )
            intent.putExtra("aluno_destino", this.prefs?.getString("aluno_object_id",""))
            intent.putExtra("aluno_prova", this.provaParaTransferir .alunoId.toString())
            intent.putExtra("titulo_prova", this.provaParaTransferir .nomeProva)
            intent.putExtra("valor_prova", this.provaParaTransferir .valorProva.toString())
            intent.putExtra("descricao_prova", this.provaParaTransferir .descricaoProva)
            intent.putExtra("data_prova", format.format(this.provaParaTransferir .dataProva))
            intent.putExtra("prioridade_prova", this.provaParaTransferir .prioridadeProva)
            startActivity(intent)

        } else {
            Toast.makeText(this, "Não há prova selecionada para transferir", Toast.LENGTH_SHORT).show()
        }
    }

    inner class ProvaService : AsyncTask<Void, Void, String>(){

        override fun doInBackground(vararg params: Void?): String? {
            obterProvasAlunoLogado()
            obterProvasEmBackgroundSetarItensNaTela()
            return null
        }

        fun obterProvasAlunoLogado() {
            val provaQuery = ParseQuery<ParseObject>("prova")
            provaQuery.whereEqualTo("aluno_id", prefs?.getString("aluno_object_id", ""))
            provaQuery.findInBackground { retorno, _ ->

                Log.e("totalProvas", retorno.size.toString())

            }
        }

        private fun obterProvasEmBackgroundSetarItensNaTela() {
            val provaQuery = ParseQuery<ParseObject>("prova")
            provaQuery.whereEqualTo("aluno_id", prefs?.getString("aluno_object_id", ""))
            provaQuery.findInBackground { objects, _ ->
                objects.forEach { parseObject: ParseObject? ->
                    construirParametrosDaTela(parseObject)
                }
                recycleProva.adapter = ProvaAdapter(applicationContext, provas)
                (recycleProva.adapter as ProvaAdapter).setmRecycleViewOnClickListenerHack(this@ProvaActivity)

            }
        }

        private fun construirParametrosDaTela(parseObject: ParseObject?) {
            var prova = Prova()
            prova.nomeProva = parseObject?.getString("nome")
            prova.descricaoProva = parseObject?.getString("descricao")
            prova.valorProva = parseObject?.getInt("valor")
            prova.alunoId = parseObject?.getString("aluno_id")
            prova.dataProva = parseObject?.getDate("data")
            provas.add(prova)
        }

    }


}
