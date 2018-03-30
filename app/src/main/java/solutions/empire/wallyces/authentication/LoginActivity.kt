package solutions.empire.wallyces.authentication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.inc_campos_login.*
import solutions.empire.wallyces.R
import solutions.empire.wallyces.core.BaseActivity
import solutions.empire.wallyces.view.DashboardActivity
import java.util.*


class LoginActivity : BaseActivity() {


    val RC_SIGN_IN = 100
    val PREFS_NAME = "repositorio_local"
    var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        inicializarComFiltrosOcultos()
        prefs = this.getSharedPreferences(PREFS_NAME,0)

        val actionBar = supportActionBar
        actionBar!!.hide()

    }


    override fun onStart() {
        super.onStart()
        this.entrarComAluno()
        this.entrarComProfessor()
        this.renderizarCamposLoginAluno()
        this.renderizarCamposLoginProfessor()
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
            if (requestCode == RC_SIGN_IN) {
                intent = Intent(this, DashboardActivity::class.java )
                intent.putExtra("tipo_usuario", "A")
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Não foi possivel realizar login, tente novamente" , Toast.LENGTH_SHORT).show()
            }
    }



    fun logarAlunoComGoogle() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                AuthUI.IdpConfig.GoogleBuilder().build()
                        ))
                        .build(),
                RC_SIGN_IN)
    }

    fun autenticarProfessorFirebase() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(input_email.text.toString(), input_senha.text.toString())
                .addOnSuccessListener { authResult ->
                    this.obterDadosProfessor(authResult.user.email!!)
                }.addOnFailureListener { exception ->
                    Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
                }
    }

    private fun obterDadosProfessor (email: String) {
        val query  = ParseQuery.getQuery<ParseObject>("professor")
        query.findInBackground { retorno, parseException ->
            if (parseException == null) {

                val professor =   retorno.filter { parseObject -> parseObject.getString("email") == email }.single()
                persistirUsuarioLocalmente(professor)
                redirecionarParaDashboardComDados(professor)

            } else {
                Toast.makeText(applicationContext,parseException.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun redirecionarParaDashboardComDados(professor: ParseObject) {
        intent = Intent(this, DashboardActivity::class.java)
        intent.putExtra("professor_nome", professor.getString("nome"))
        intent.putExtra("professor_email", professor.getString("email"))
        intent.putExtra("professor_permissao", professor.getString("permissao"))
        intent.putExtra("professor_curso", professor.getString("curso"))
        intent.putExtra("tipo_usuario", "P")

        startActivity(intent)
    }

    private fun persistirUsuarioLocalmente(professor: ParseObject) {
        val editor = prefs!!.edit()
        editor.putString("professor_nome", professor.getString("nome"))
        editor.putString("professor_email", professor.getString("email"))
        editor.putString("professor_permissao", professor.getString("permissao"))
        editor.putString("professor_curso", professor.getString("curso"))
        editor.commit()
    }

    private fun renderizarCamposLoginProfessor() {
        radio_professor.setOnClickListener({
            include_formulario_login.visibility = View.VISIBLE
            entrarComGoogle.visibility = View.INVISIBLE
        })
    }

    private fun renderizarCamposLoginAluno() {
        radio_aluno.setOnClickListener({
            include_formulario_login.visibility = View.INVISIBLE
            entrarComGoogle.visibility = View.VISIBLE
            entrarComGoogle.y = 1100F
        })
    }

    private fun inicializarComFiltrosOcultos() {
        include_formulario_login.visibility = View.INVISIBLE
        entrarComGoogle.visibility = View.INVISIBLE
    }


    fun entrarComProfessor() {
        btn_entrar_professor.setOnClickListener {
            autenticarProfessorFirebase()
        }
    }

    fun entrarComAluno() {
        entrarComGoogle.setOnClickListener {
            logarAlunoComGoogle()
        }
    }
}


