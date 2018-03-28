package solutions.empire.wallyces.core

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import solutions.empire.wallyces.authentication.LoginActivity
import solutions.empire.wallyces.util.AppConstantes

/**
 * Created by mviniciusmarques on 11/03/18.
 */
open class BaseActivity : AppCompatActivity(){

    val REPOSITORIO_SHARED_PREFERENCES = "repositorio_local_shared_preferences"
    var sharedPreferences: SharedPreferences? = null
    var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        primeiraGeracao()
    }

    fun primeiraGeracao() {
        this.sharedPreferences = this.getSharedPreferences(REPOSITORIO_SHARED_PREFERENCES,0)
        this.auth = FirebaseAuth(FirebaseApp.getInstance())
    }

    protected open fun deslogar() {
        AuthUI.getInstance().signOut(this)
        startActivity(Intent(this, LoginActivity::class.java))
    }

    protected open fun obterUsuarioLogado(): String{
        this.auth = FirebaseAuth(FirebaseApp.getInstance())
        return auth?.currentUser?.displayName.toString()
    }

    protected open fun obterItemRepositorioLocal(chave: String): String {
        return this.sharedPreferences!!.getString(chave, AppConstantes.STRING_VAZIA.toString())
    }

    protected open fun inserirItemRepositorioLocal(chave: String, valor: String) {
        val manipuladorRepositorioLocal = sharedPreferences!!.edit()
        manipuladorRepositorioLocal.putString(chave,valor)
        manipuladorRepositorioLocal.commit()
    }



}


