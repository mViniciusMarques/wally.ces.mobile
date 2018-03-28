package solutions.empire.wallyces.view

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_aviso.*
import solutions.empire.wallyces.R
import solutions.empire.wallyces.adapter.AvisoAdapter
import solutions.empire.wallyces.core.BaseActivity
import solutions.empire.wallyces.model.AvisoCard


class AvisoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aviso)

        var avisos: MutableList<AvisoCard> = arrayListOf()
        val manager = LinearLayoutManager(applicationContext)
        manager.orientation = LinearLayoutManager.VERTICAL
        recycleAviso.layoutManager = manager


        var ac : AvisoCard = AvisoCard()
        ac.titulo = "titulo"
        ac.descricao = "Houve a participação de 18 pessoas (assinaram a lista de presença do treinamento + pessoas online no skype)"
        ac.professor = "Professor X"
        ac.dataCriacao = "26/03/2018"

        var ac2 : AvisoCard = AvisoCard()
        ac2.titulo = "titulo"
        ac2.descricao = "Mesmo que você não tenha ganho o sorteio, caso queira interessado, podemos discutir mais sobre internet das coisas e arduino."
        ac2.professor = "Professor Y"
        ac2.dataCriacao = "26/03/2018"

        var ac3 : AvisoCard = AvisoCard()
        ac3.titulo = "Boa Tarde"
        ac3.descricao = "Gostaria de convidá-los para participar de uma série de apresentações, que ocorrerão na quarta-feira, dia 28/03, no espaço ágil."
        ac3.professor = "Professor Y"
        ac3.dataCriacao = "26/03/2018"

        var ac4 : AvisoCard = AvisoCard()
        ac4.titulo = "Contamos com a presença de todos"
        ac4.descricao = "As apresentações irão começar às 14:00 e cada uma terá a duração de 30 minutos."
        ac4.professor = "Professor Y"
        ac4.dataCriacao = "26/03/2018"

        var ac5 : AvisoCard = AvisoCard()
        ac5.titulo = "titulo"
        ac5.descricao = "Mesmo que você não tenha ganho o sorteio, caso queira interessado, podemos discutir mais sobre internet das coisas e arduino."
        ac5.professor = "Professor Y"
        ac5.dataCriacao = "26/03/2018"

        var ac6 : AvisoCard = AvisoCard()
        ac6.titulo = "Before "
        ac6.descricao = "As informações deste e-mail são confidenciais. O uso não autorizado é proibido por lei."
        ac6.professor = "Professor Y"
        ac6.dataCriacao = "26/03/2018"

        avisos.add(ac)
        avisos.add(ac2)
        avisos.add(ac3)
        avisos.add(ac4)
        avisos.add(ac5)
        avisos.add(ac6)

        recycleAviso.adapter = AvisoAdapter(applicationContext, avisos)

        redirecionarParaAdicionarAviso()
        this.auth = FirebaseAuth(FirebaseApp.getInstance())
      //  Log.e("KURIRIN", this.auth!!.currentUser.email)
       // Log.e("KURIRIN2", this.obterItemRepositorioLocal("professor_nome"))
    }

    private fun redirecionarParaAdicionarAviso() {
        adicionarAviso.setOnClickListener {
            startActivity(Intent(this, CadastroAvisoActivity::class.java))
        }
    }

    inner class QuadroProfessorService : AsyncTask<Void, Void, String>(){

        override fun doInBackground(vararg params: Void?): String? {
            obterOcorrencia()
            return null
        }

        fun obterOcorrencia() {
            val query = ParseQuery<ParseObject>("Ocorrencia")
           // query.whereEqualTo("nome",nomeProfessor)
            query.findInBackground { retorno, parseException ->
                if (parseException == null) {
                    popularCamposComDadosRetornados(retorno)
                } else {
                    // Toast.makeText(applicationContext,"Erro ao obter disciplinas, verifique sua conexão!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun popularCamposComDadosRetornados(retornoParse: MutableList<ParseObject>) {
//            professor = retornoParse.first()
//            nome_professor_qp.text = nomeProfessor
//            data_publicacao_qp.text = retornoParse.first().getString("createdAt")
//            input_disciplina_qp.text = retornoParse.first().getString("disciplina")
//            sala_professor_qp.text = retornoParse.first().getString("sala")
//            input_horario_qp.text = retornoParse.first().getString("horario")
//            txt_aviso_qp.text = retornoParse.first().getString("aviso")
        }

    }
}
