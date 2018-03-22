package solutions.empire.wallyces.view


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.inc_dashboard_professor.*
import solutions.empire.wallyces.R
import solutions.empire.wallyces.core.BaseActivity

class DashboardActivity : BaseActivity() {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.inc_dashboard_professor)
        super.onCreate(savedInstanceState)

        this.sair()
        this.cadastrarOcorrencia();
        this.consultarOcorrencia();

    }

    fun sair() {
        btn_sair_dp.setOnClickListener { view ->
            this.deslogar();
        }
    }


    @SuppressLint("ResourceType")
    fun cadastrarOcorrencia() {
        cartao_cadastrar.setOnClickListener { view ->
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_grow_fade_in_from_bottom) )
            startActivity(Intent(this, CadastroProfessorActivity::class.java));
        }
    }

    fun consultarOcorrencia() {
        cartao_consultar.setOnClickListener { view ->
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_grow_fade_in_from_bottom) )
            startActivity(Intent(this, BuscarProfessorActivity::class.java));
        }
    }
}
