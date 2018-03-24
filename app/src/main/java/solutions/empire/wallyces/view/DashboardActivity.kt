package solutions.empire.wallyces.view


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.inc_dashboard_aluno.*
import kotlinx.android.synthetic.main.inc_dashboard_professor.*
import solutions.empire.wallyces.R
import solutions.empire.wallyces.core.BaseActivity

class DashboardActivity : BaseActivity() {

    private val ALUNO = "A"
    private val PROFESSOR = "P"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)

        this.consultarAvisoAluno()
        this.consultarOcorrenciaAluno()

        this.consultarAvisoProfessor()
        this.cadastrarOcorrenciaProfessor()
        this.consultarOcorrenciaProfessor()

        this.renderizarDashboardPorTipoUsuario()

        this.sairAluno()
        this.sairProfessor()
    }



    fun sairProfessor() {
        btn_sair_dp.setOnClickListener { view ->
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_slide_in_bottom) )
            this.deslogar();
        }
    }

    fun sairAluno() {
        btn_sair_da.setOnClickListener { view ->
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fui_slide_out_left) )
            this.deslogar();
        }
    }

    fun cadastrarOcorrenciaProfessor() {
        cartao_cadastrar_dp.setOnClickListener { view ->
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_grow_fade_in_from_bottom) )
            startActivity(Intent(this, CadastroProfessorActivity::class.java));
        }
    }

    fun consultarOcorrenciaProfessor() {
        cartao_consultar_dp.setOnClickListener { view ->
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_grow_fade_in_from_bottom) )
            startActivity(Intent(this, BuscarProfessorActivity::class.java));
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

    private fun renderizarDashboardPorTipoUsuario() {
        val tipoUsuarioLogado = intent.getStringExtra("tipo_usuario")
        if (tipoUsuarioLogado == PROFESSOR) {
            include_professor.visibility = View.VISIBLE
        } else if (tipoUsuarioLogado == ALUNO) {
            include_aluno.visibility = View.VISIBLE
        }
    }
}
