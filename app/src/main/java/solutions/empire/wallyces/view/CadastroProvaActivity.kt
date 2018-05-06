package solutions.empire.wallyces.view

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.parse.ParseObject
import kotlinx.android.synthetic.main.activity_cadastro_prova.*
import kotlinx.android.synthetic.main.inc_cadastrar_prova.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import solutions.empire.wallyces.R
import solutions.empire.wallyces.model.Prova
import java.util.*
import com.google.zxing.WriterException
import android.graphics.Bitmap
import android.graphics.Color
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.os.AsyncTask
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.parse.ParseQuery


class CadastroProvaActivity : AppCompatActivity() {

    val PREFS_NAME = "repositorio_local"
    var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_prova)

        prefs = this.getSharedPreferences(PREFS_NAME,0)

        val actionBar = supportActionBar
        actionBar!!.hide()

        acaoBotaoCadastrar()
        atai()
        ProvaService().execute()

    }


    private fun construirProvaParaSalvar(): Prova {
        var provaParaSalvar = Prova()
        provaParaSalvar.nomeProva = cadastro_prova_titulo.text.toString()
        provaParaSalvar.descricaoProva = cadastro_prova_descricao.text.toString()
        provaParaSalvar.dataProva =  Date()
        provaParaSalvar.valorProva =  Integer.parseInt(cadastro_prova_valor.text.toString())
        provaParaSalvar.alunoId = this.prefs?.getString("aluno_object_id","")
        Log.e("cliquei","cliquei")
        return provaParaSalvar
    }

    private fun salvarProva() {
        val provaParse = ParseObject("prova")
        val prova = this.construirProvaParaSalvar()
        provaParse.put("nome", prova.nomeProva)
        provaParse.put("data", prova.dataProva)
        provaParse.put("valor", prova.valorProva)
        provaParse.put("descricao", prova.descricaoProva)
        provaParse.put("aluno_id", prova.alunoId)
        provaParse.saveInBackground().onSuccess {
            startActivity(Intent(this, ProvaActivity::class.java))
        }
    }

    private fun acaoBotaoCadastrar() {
        btn_cadastrar_prova.setOnClickListener {
            this.salvarProva()
        }
    }

    private fun atai() {
        button2.setOnClickListener {
            startActivity(Intent(this, ProvaActivity::class.java))
        }
    }


    inner class ProvaService : AsyncTask<Void, Void, String>(){

        override fun doInBackground(vararg params: Void?): String? {
            obterProvasAlunoLogado()
            return null
        }

        override fun onPreExecute() {
            super.onPreExecute()

        }

        fun obterProvasAlunoLogado() {
            val provaQuery = ParseQuery<ParseObject>("prova")
            provaQuery.whereEqualTo("aluno_id", prefs?.getString("aluno_object_id", ""))
            provaQuery.findInBackground { retorno, _ ->

                Log.e("totalProvas", retorno.size.toString())

            }
        }

    }

}
