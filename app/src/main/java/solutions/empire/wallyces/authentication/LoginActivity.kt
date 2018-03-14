package solutions.empire.wallyces.authentication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.inc_campos_login.*
import com.firebase.ui.auth.AuthUI
import java.util.*
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.FindCallback
import com.parse.ParseException
import solutions.empire.wallyces.R
import solutions.empire.wallyces.view.BuscarProfessorActivity
import solutions.empire.wallyces.view.DashboardActivity


class LoginActivity : AppCompatActivity() {


    val RC_SIGN_IN = 100;
    val PREFS_NAME = "repositorio_local"
    var prefs: SharedPreferences? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        include_formulario_login.visibility = View.INVISIBLE;
        entrarComGoogle.visibility = View.INVISIBLE;

        prefs = this.getSharedPreferences(PREFS_NAME,0)

    }

    override fun onStart() {
        super.onStart()
        this.seletorDeCamposUsuarios();
        this.entrarComAluno();
        this.entrarComProfessor();
    }

    private fun seletorDeCamposUsuarios() {

        radio_aluno.setOnClickListener(View.OnClickListener {
            if (radio_aluno.isChecked) {
                include_formulario_login.visibility = View.INVISIBLE;
                entrarComGoogle.visibility = View.VISIBLE;
                entrarComGoogle.y = 1200F
            }
        })

        radio_professor.setOnClickListener(View.OnClickListener {
            if (radio_professor.isChecked) {
                include_formulario_login.visibility = View.VISIBLE;
                entrarComGoogle.visibility = View.INVISIBLE;
            }
        })

    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
            if (requestCode == RC_SIGN_IN) {
                startActivity(Intent(this, BuscarProfessorActivity::class.java));
            } else {

            }
    }

    fun entrarComProfessor() {
        btn_entrar_professor.setOnClickListener {
            autenticarProfessor();
        }
    }

    fun entrarComAluno() {
        entrarComGoogle.setOnClickListener(View.OnClickListener {
            logarComGoogle()
        })
    }


    fun logarComGoogle() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                               // AuthUI.IdpConfig.EmailBuilder().build(),
                                AuthUI.IdpConfig.GoogleBuilder().build()
                        ))
                        .build(),
                RC_SIGN_IN)
    }

    fun autenticarProfessor() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(input_email.text.toString(), input_senha.text.toString())
                .addOnSuccessListener { authResult ->
                    Log.e("SUCESSO_PROF", authResult.user.email)
                    startActivity(Intent(this, DashboardActivity::class.java));
                    this.obterDadosProfessor(authResult.user.email!!)
                }.addOnFailureListener { exception ->
            Log.e("ERRO_PROF", exception.message);
            Toast.makeText(this, "Usuario/Senha incorretos", Toast.LENGTH_SHORT).show();
        }
    }

    private fun obterDadosProfessor (email: String) {
        val query  = ParseQuery.getQuery<ParseObject>("professor");
        val editor = prefs!!.edit();
        query.findInBackground(object : FindCallback<ParseObject> {
            override fun done(retorno: List<ParseObject>, parseException: ParseException?) {
                if (parseException == null) {
                    val professor =   retorno.filter { parseObject -> parseObject.getString("email").equals(email) }.single()
                    editor.putString("professor_nome", professor.getString("nome"))
                    editor.putString("professor_email", professor.getString("email"))
                    editor.putString("professor_permissao", professor.getString("permissao"))
                    editor.putString("professor_curso", professor.getString("curso"))
                    editor.commit();
                } else {
                    Toast.makeText(applicationContext,"Erro ao obter dados do professor, verifique sua conexão!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}


