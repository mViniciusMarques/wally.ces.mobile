package solutions.empire.wallyces.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Base64
import android.view.View
import android.widget.Toast
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_prova.*
import kotlinx.android.synthetic.main.inc_prova_destaque.*
import solutions.empire.wallyces.R
import solutions.empire.wallyces.adapter.ProvaAdapter
import solutions.empire.wallyces.core.BaseActivity
import solutions.empire.wallyces.interfaces.RecycleViewOnClickListenerHack
import solutions.empire.wallyces.model.Prova
import io.github.kobakei.materialfabspeeddial.FabSpeedDialMenu
import android.widget.TextView
import android.support.design.widget.FloatingActionButton
import android.util.Log
import io.github.kobakei.materialfabspeeddial.FabSpeedDial
import solutions.empire.wallyces.R.id.fab






class ProvaActivity : BaseActivity(), RecycleViewOnClickListenerHack {

    val PREFS_NAME = "repositorio_local"
    var prefs: SharedPreferences? = null
    var provas: MutableList<Prova> = arrayListOf()
    var possuiItemParaTransferir : Boolean = false
    var provaParaTransferir: Prova = Prova()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prova)

        prefs = this.getSharedPreferences(PREFS_NAME,0)

        val actionBar              = supportActionBar
        actionBar!!.hide()

        this.inicializarProvaRecycleView()
        this.possuiItemParaTransferir = false
        this.acaoBotaoTransferir()

        ProvaService().execute()

        val menu = FabSpeedDialMenu(this)
        menu.add("Cadastrar Prova").setIcon(R.drawable.ic_action_add)
        menu.add("Transferir Prova").setIcon(R.drawable.ic_action_add)
        menu.add("Obter Prova").setIcon(R.drawable.ic_action_add)


        fab.setMenu(menu)
        fab.addOnMenuItemClickListener { _, _, itemId ->

            Log.e("teste menu", menu.getItem(itemId -1).title.toString())

            when {
                menu.getItem(itemId -1).title == "Cadastrar Prova" -> {
                    Toast.makeText(this, "Cadastro", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, CadastroProvaActivity::class.java))

                }
                menu.getItem(itemId -1).title == "Transferir Prova" -> {
                    Toast.makeText(this, "Transferir", Toast.LENGTH_SHORT).show()
                    this.transferirProva()
                }
                menu.getItem(itemId -1).title == "Obter Prova" -> {
                    Toast.makeText(this, "Obter", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, ConsumidorQrCodeActivity::class.java))


                }
            }


        }




    }

    private fun inicializarProvaRecycleView() {
        val manager                 = LinearLayoutManager(applicationContext)
        manager.orientation         = LinearLayoutManager.VERTICAL
        recycleProva.layoutManager  = manager
        var recycle                 = ProvaAdapter(applicationContext, provas)
        recycle.setmRecycleViewOnClickListenerHack(this)
        recycleProva.adapter        = recycle
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
        titulo_prova_destaque.text      = this.provas[position].nomeProva
        descricao_prova_destaque.text   = this.provas[position].descricaoProva
        valor_prova_destaque.text       = this.provas[position].valorProva.toString()
        data_prova_destaque.text        = this.obterFormatoDeDataBr().format(this.provas[position].dataProva)

        this.provaParaTransferir        = this.provas[position]
        this.possuiItemParaTransferir   = true

    }

    private fun transferirProva() {
        if (this.possuiItemParaTransferir ) {
            var intent = Intent(this,   TransferenciaActivity::class.java )
            intent.putExtra("aluno_destino",    this.prefs?.getString("aluno_object_id",""))
            intent.putExtra("aluno_prova",      this.provaParaTransferir .alunoId.toString())
            intent.putExtra("titulo_prova",     this.provaParaTransferir .nomeProva)
            intent.putExtra("valor_prova",      this.provaParaTransferir .valorProva.toString())
            intent.putExtra("descricao_prova",  this.provaParaTransferir .descricaoProva)
            intent.putExtra("data_prova",       this.obterFormatoDeDataBr().format(this.provaParaTransferir .dataProva))
            intent.putExtra("prioridade_prova", this.provaParaTransferir .prioridadeProva)
            startActivity(intent)

        } else {
            Toast.makeText(this, "Não há prova selecionada para transferir", Toast.LENGTH_SHORT).show()
        }
    }

    private fun codificarTextoParaBase64(prova: String): String {
        val data = prova.reversed().toByteArray(charset("UTF-8"))
        return Base64.encodeToString(data, Base64.DEFAULT)
    }

    private fun decodificarTextoDeBase64(provaCodificada: String): String {
        val datasd = Base64.decode(provaCodificada.reversed(), Base64.DEFAULT)
        return String(datasd, charset("UTF-8"))
    }

    inner class ProvaService : AsyncTask<Void, Void, String>(){

        override fun doInBackground(vararg params: Void?): String? {
            obterProvasEmBackgroundSetarItensNaTela()
            return null
        }

        private fun obterProvasEmBackgroundSetarItensNaTela() {
            val provaQuery = ParseQuery<ParseObject>("prova")
            provaQuery.whereEqualTo("aluno_id", prefs?.getString("aluno_object_id", ""))
            provaQuery.findInBackground { objects, _ ->
                                            objects.forEach { parseObject: ParseObject? ->
                                                construirParametrosDaTela(parseObject)
                                            }
                validarExistenciaDeProva()
                recycleProva.adapter = ProvaAdapter(applicationContext, provas)
                (recycleProva.adapter as ProvaAdapter).setmRecycleViewOnClickListenerHack(this@ProvaActivity)

            }
        }

        private fun construirParametrosDaTela(parseObject: ParseObject?) {
            var prova = Prova()
            prova.nomeProva         = parseObject?.getString("nome")
            prova.descricaoProva    = parseObject?.getString("descricao")
            prova.valorProva        = parseObject?.getInt("valor")
            prova.alunoId           = parseObject?.getString("aluno_id")
            prova.dataProva         = parseObject?.getDate("data")
            provas.add(prova)
        }

        private fun validarExistenciaDeProva() {
            if(provas.isEmpty()) {
                descricao_prova_destaque.textSize   = 30F
                descricao_prova_destaque.text       = "Selecione uma prova!!!"
            }
        }

    }


}
