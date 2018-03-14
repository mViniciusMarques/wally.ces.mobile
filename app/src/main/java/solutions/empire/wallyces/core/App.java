package solutions.empire.wallyces.core;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.parse.Parse;

import solutions.empire.wallyces.BuscarProfessorActivity;
import solutions.empire.wallyces.CadastroProfessorActivity;
import solutions.empire.wallyces.DashboardActivity;
import solutions.empire.wallyces.LoginActivity;

/**
 * Created by mviniciusmarques on 08/03/18.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        this.inicializarParse();
        this.verificarUsuarioLogado();

    }


    private void inicializarParse() {
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("IfxnM9CeknbXJ0n5voDO4wOLje6vdzURql0DfnVT")
                .clientKey("1fk2b9w4AumiXlDhBrlV1FSSr2W23gDvyf4C5z6q")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }

    private void verificarUsuarioLogado() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null) {
            Log.e("PROVIDER_ID", auth.getCurrentUser().getDisplayName() + "    " +   auth.getCurrentUser().getProviderId() );
            direcionarUsuario(auth);
        } else {
            Intent i = new Intent(this,LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(i);        }
    }

    private void direcionarUsuario(FirebaseAuth auth) {
        Intent intent;
        if (auth.getCurrentUser().getDisplayName() == "") {
            intent = new Intent(this,DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(intent);
        } else {
            intent = new Intent(this,BuscarProfessorActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(intent);
        }
    }

}
