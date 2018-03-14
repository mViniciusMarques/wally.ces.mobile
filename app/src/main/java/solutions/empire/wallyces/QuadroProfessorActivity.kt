package solutions.empire.wallyces

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.parse.ParseObject
import kotlinx.android.synthetic.main.inc_cartao_professor.*

class QuadroProfessorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quadro_professor)
        obterDados();
    }

    fun obterProfessorParse() {
        val p :  ParseObject =  ParseObject("Professor")
            p.put("nome","Dream Seller")
            p.put("curso", "curso")

    }

    fun obterDados() {

       val nome  = intent.getStringExtra("nome_professor")
       val sala =  intent.getStringExtra("sala")
       val horario = intent.getStringExtra("horario")

        input_disciplina_qp.setText(nome);
        sala_professor_qp.setText(sala);
        input_horario_qp.setText(horario);

        Log.e("PASS_INTENT " , nome + " " + sala + " " + horario)
    }
}
