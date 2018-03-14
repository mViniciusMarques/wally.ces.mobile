package solutions.empire.wallyces


import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.inc_dashboard_professor.*
import solutions.empire.wallyces.core.BaseActivity

class DashboardActivity : BaseActivity() {

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

    fun cadastrarOcorrencia() {
        cartao_cadastrar.setOnClickListener { view ->
            startActivity(Intent(this, CadastroProfessorActivity::class.java));
        }
    }

    fun consultarOcorrencia() {
        cartao_consultar.setOnClickListener { view ->
            startActivity(Intent(this, BuscarProfessorActivity::class.java));
        }
    }
}
