package solutions.empire.wallyces.core

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import solutions.empire.wallyces.LoginActivity

/**
 * Created by mviniciusmarques on 11/03/18.
 */
open class BaseActivity : AppCompatActivity(){

    val preferencias: String = "general_preferences";

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    protected open fun deslogar () {
        AuthUI.getInstance().signOut(this);
        startActivity(Intent(this, LoginActivity::class.java));
    }

}