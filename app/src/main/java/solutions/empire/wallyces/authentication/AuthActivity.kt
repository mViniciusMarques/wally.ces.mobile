package solutions.empire.wallyces.authentication

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.inc_campos_login.*
import solutions.empire.wallyces.core.BaseActivity
import solutions.empire.wallyces.view.BuscarProfessorActivity
import solutions.empire.wallyces.view.DashboardActivity
import java.util.*

/**
 * Created by mviniciusmarques on 14/03/18.
 */
class AuthActivity : BaseActivity() {

    val RC_SIGN_IN = 100;

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == RC_SIGN_IN) {
            startActivity(Intent(this, BuscarProfessorActivity::class.java));
        } else {

        }
    }

    fun autenticarProfessor(){

        FirebaseAuth.getInstance().signInWithEmailAndPassword(input_email.text.toString(), input_senha.text.toString())
                .addOnSuccessListener { authResult ->
                    startActivity(Intent(this, DashboardActivity::class.java));

                  //  this.obterDadosProfessor(authResult.user.email!!)
                }.addOnFailureListener { exception ->
            Log.e("ERRO_PROF", exception.message);
            Toast.makeText(this, "Usuario/Senha incorretos", Toast.LENGTH_SHORT).show();
        }


    }

    fun autenticarComGoogleProvider() {
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
}