package com.example.clientapp.ui.login;
import androidx.appcompat.app.AppCompatActivity;
import com.example.clientapp.R;
import com.example.clientapp.ui.login.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Button;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;

public class ForgottenPassword extends AppCompatActivity {

// La fleche en haut/gauche renvoi sur la page LoginActivity
    public boolean onOptionsItemSelected(MenuItem item){ Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class); startActivityForResult(myIntent, 0); return true; }
// Variable App
    private App ClientApp;
    String ClientEmail;
// Déclaration des objets
    private Button mForgottenButton;

// Chargement du template
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);

// Id de la client App
        String ClientAppId = "clientapp-hqokj";

// Récupère l'état du bouton ForgottenPassword
        Button mForgottenButton = (Button) findViewById(R.id.activity_forgotten_password_sendnewpassword);

// La fleche en haut/gauche est activé
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

// Affiche une information lors du click sur le bouton ForgottenPassword
        mForgottenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewRegister) {

                App ClientApp = new App(new AppConfiguration.Builder(ClientAppId).build());

                ClientEmail = mForgottenButton.getText().toString();
                ClientApp.getEmailPassword().sendResetPasswordEmailAsync(ClientEmail, it -> {
                    if (it.isSuccess()) {
                        Log.i("EXAMPLE", "Successfully sent the user a reset password link to " + ClientEmail);
                    } else {
                        Log.e("EXAMPLE", "Failed to send the user a reset password link to " + ClientEmail + ": " + it.getError().getErrorMessage());
                    }
                });

            }
        });
    }
}