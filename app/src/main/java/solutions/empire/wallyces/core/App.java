package solutions.empire.wallyces.core;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.parse.Parse;

import solutions.empire.wallyces.view.BuscarProfessorActivity;
import solutions.empire.wallyces.view.DashboardActivity;
import solutions.empire.wallyces.authentication.LoginActivity;

/**
 * Created by mviniciusmarques on 08/03/18.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        this.inicializarParse();
        this.tratarLoginUsuario();

    }


    private void inicializarParse() {
// Usando a base caed
//        Parse.initialize(new Parse.Configuration.Builder(this)
//                .applicationId("IfxnM9CeknbXJ0n5voDO4wOLje6vdzURql0DfnVT")
//                .clientKey("1fk2b9w4AumiXlDhBrlV1FSSr2W23gDvyf4C5z6q")
//                .server("https://parseapi.back4app.com/")
//                .build()
//        );

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("JxoYzBC4S0Y8rqAZ55qAVx4Mk7FPHckoCmw5cg6g")
                .clientKey("l9X7Ewg4C1pmR2QdbnECptC4nuuijHiRs81bQXtq")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }

    private void tratarLoginUsuario() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null) {
            direcionarUsuarioLogadoPorTipo(auth);
        } else {
            Intent i = new Intent(this,LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(i);        }
    }

    private void direcionarUsuarioLogadoPorTipo(FirebaseAuth auth) {
        Intent intent;
        if (auth.getCurrentUser().getDisplayName().isEmpty()) {
            intent = new Intent(this,DashboardActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(intent);
        } else {
            intent = new Intent(this,BuscarProfessorActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            startActivity(intent);
        }
    }

}
