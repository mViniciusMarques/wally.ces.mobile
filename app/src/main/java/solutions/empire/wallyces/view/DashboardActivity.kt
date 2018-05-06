package solutions.empire.wallyces.view


import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.inc_dashboard_aluno.*
import kotlinx.android.synthetic.main.inc_dashboard_professor.*
import solutions.empire.wallyces.R
import solutions.empire.wallyces.core.BaseActivity

class DashboardActivity : BaseActivity() {

    private val ALUNO = "A"
    private val PROFESSOR = "P"
    private var isAlunoRegistrado: Boolean = false
    val PREFS_NAME = "repositorio_local"
    var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        prefs = this.getSharedPreferences(PREFS_NAME,0)
        val actionBar = supportActionBar
        actionBar!!.hide()

        this.sugestaoAluno()
        this.sugestaoProfessor()
        this.consultarAvisoAluno()
        this.consultarOcorrenciaAluno()

        this.consultarAvisoProfessor()
        this.cadastrarOcorrenciaProfessor()
        this.consultarOcorrenciaProfessor()

        this.cadastrarProvaProvisiorio()

        this.renderizarDashboardPorTipoUsuario()

        this.sairAluno()
        this.sairProfessor()

        AlunoService().execute()
    }

    private fun sugestaoAluno() {
        cartao_sugestao_da.setOnClickListener {
            startActivity(Intent(this, SugestaoActivity::class.java))
        }
    }

    private fun sugestaoProfessor() {
        cartao_sugestao_dp.setOnClickListener {
            startActivity(Intent(this, SugestaoActivity::class.java))
        }
    }


    fun sairProfessor() {
        btn_sair_dp.setOnClickListener { view ->
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_slide_in_bottom) )
            this.deslogar()
        }
    }

    fun sairAluno() {
        btn_sair_da.setOnClickListener { view ->
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fui_slide_out_left) )
            this.deslogar()
        }
    }

    fun cadastrarOcorrenciaProfessor() {
        cartao_cadastrar_dp.setOnClickListener { view ->
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_grow_fade_in_from_bottom) )
            startActivity(Intent(this, CadastroProfessorActivity::class.java))
        }
    }

    fun consultarOcorrenciaProfessor() {
        cartao_consultar_dp.setOnClickListener { view ->
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_grow_fade_in_from_bottom) )
            startActivity(Intent(this, BuscarProfessorActivity::class.java))
        }
    }

    fun consultarAvisoProfessor() {
        cartao_aviso_dp.setOnClickListener { view ->
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_grow_fade_in_from_bottom) )
            startActivity(Intent(this, AvisoActivity::class.java))
        }
    }

    fun consultarOcorrenciaAluno() {
        cartao_consultar_da.setOnClickListener { view ->
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_grow_fade_in_from_bottom) )
            startActivity(Intent(this, BuscarProfessorActivity::class.java))
        }
    }

    fun consultarAvisoAluno() {
        cartao_aviso_da.setOnClickListener { view ->
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_grow_fade_in_from_bottom) )
            startActivity(Intent(this, AvisoActivity::class.java))
        }
    }

    fun cadastrarProvaProvisiorio() {
        cartao_prova_da.setOnClickListener { view ->
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_grow_fade_in_from_bottom) )
            startActivity(Intent(this, CadastroProvaActivity::class.java))
        }
    }

    private fun renderizarDashboardPorTipoUsuario() {
        val tipoUsuarioLogado = intent.getStringExtra("tipo_usuario")
        if (tipoUsuarioLogado == PROFESSOR) {
            include_professor.visibility = View.VISIBLE
        } else if (tipoUsuarioLogado == ALUNO) {
            include_aluno.visibility = View.VISIBLE
        }
    }

    private fun cadastrarAluno() {
        startActivity(Intent(this, CadastroAlunoActivity::class.java))
    }

    open inner class AlunoService : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg p0: Void?): String? {
            if (intent.getStringExtra("tipo_usuario") == ALUNO) {
                hasAlunoCadastroCompleto()
            }
                return ""
        }

        fun hasAlunoCadastroCompleto() {
            val alunoQuery = ParseQuery<ParseObject>("aluno")
            alunoQuery.whereEqualTo("email", obterEmailUsuarioLogado())
            isAlunoRegistrado = alunoQuery.count() != 0

            if(!isAlunoRegistrado) {
                cadastrarAluno()
            } else {
                Log.e("1957", alunoQuery.first.objectId)
                val editor = prefs!!.edit()
                editor.putString("aluno_object_id", alunoQuery.first.objectId)
                editor.commit()
            }

        }

    }
}


