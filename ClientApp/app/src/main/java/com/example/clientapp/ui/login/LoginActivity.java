package com.example.clientapp.ui.login;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clientapp.MainActivity;
import com.example.clientapp.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;

public class LoginActivity extends AppCompatActivity {

    // Déclaration des objets
    private Button mRegister; //Envoi sur la page de creation de compte
    private Button mLogin; // Validation du LogIn
    private EditText mEmail; // Champ du login
    private EditText mPassword; // Champ du mot de passe
    private Button mForgottonPassword; // Envoi sur la page de génération d'un nouveau mot de passe
    private App ClientApp;
    boolean isEmailValid, isPasswordValid;
    boolean regexPassword = false;
    boolean regexEmail = false;
    String EmailContent, PasswordContent;

    // Chargement du template
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // MONGODB REALM IMPLEMENTATION
        // Initialise la BDD Realm
        Realm.init(this);


        // Récupération de l'état des objets
        Button mLogin = (Button) findViewById(R.id.activity_login_login);
        mLogin.setEnabled(false);
        Button mRegister = (Button) findViewById(R.id.activity_login_register);
        Button mForgottenPassword = (Button) findViewById(R.id.activity_login_forgottenPassword);
        EditText mEmail = (EditText) findViewById(R.id.activity_login_email);
        EditText mPassword = (EditText) findViewById(R.id.activity_login_password);

        // Référence de l'application sur https://realm.mongodb.com/
        String ClientAppId = "clientapp-hqokj";
        String emailPattern = "^(.+)@(.+)$";
        String passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@!#$%^&+=]).{8,})";

//TEST DE PASSWORD ET EMAIL > VIDE OU REGEX MAUVAIS = mLogin FALSE
//TEST PASSWORD
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    isPasswordValid = false;
                } else {
                    isPasswordValid = true;
                }
                if (mPassword.getText().toString().trim().matches(passwordPattern)) {
                    regexPassword = true;
                } else {
                    regexPassword = false;
                }
                if (isEmailValid && regexEmail && isPasswordValid && regexPassword) {
                    mLogin.setEnabled(true);
                } else {
                    mLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

//TEST USERNAME
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    isEmailValid = false;
                } else {
                    isEmailValid = true;
                }
                if (mEmail.getText().toString().trim().matches(emailPattern)) {
                    regexEmail = true;
                    Toast.makeText(getApplicationContext(), "Le format de l'adresse mail est valide", Toast.LENGTH_SHORT).show();
                } else {
                    regexEmail = false;
                    Toast.makeText(getApplicationContext(), "Le format de l'adresse mail n'est pas valide", Toast.LENGTH_SHORT).show();
                }
                if (isEmailValid && regexEmail && isPasswordValid && regexPassword) {
                    mLogin.setEnabled(true);
                } else {
                    mLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


//LOGIN EN ANONYMOUS
/*
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewMain) {
                App ClientApp = new App(new AppConfiguration.Builder(ClientAppId).build());

                // Test login sur REALM
                Credentials credentials = Credentials.anonymous();
                ClientApp.loginAsync(credentials, result -> {
                    if (result.isSuccess()) {
                        Log.v("QUICKSTART", "Successfully authenticated anonymously.");
                        User user = ClientApp.currentUser();
                        Toast.makeText(getApplicationContext(), "Authentification réussie", Toast.LENGTH_SHORT).show();

                        //Chargement de la page main
                        Intent intentRegister = new Intent(LoginActivity.this, MainActivity.class);
                        viewMain.getContext().startActivity(intentRegister);

                    } else {
                        Log.e("QUICKSTART", "Failed to log in. Error: " + result.getError());
                    }
                });
            }
        });

*/
//LOGIN EN CREDENTIAL PASSWORD EMAIL

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewMain) {
                App ClientApp = new App(new AppConfiguration.Builder(ClientAppId).build());

// Récupération des valeurs des champs sous forme de string
                EmailContent = mEmail.getText().toString();
                PasswordContent = mPassword.getText().toString();
// Test login sur REALM

                Credentials emailPasswordCredentials = Credentials.emailPassword(EmailContent, PasswordContent);
                AtomicReference<User> user = new AtomicReference<User>();
                ClientApp.loginAsync(emailPasswordCredentials, it -> {
                    if (it.isSuccess()) {
                        Log.v("AUTH", "Successfully authenticated using an email and password.");
                        user.set(ClientApp.currentUser());

                        Intent intentMainPage = new Intent(LoginActivity.this, MainActivity.class);
                        viewMain.getContext().startActivity(intentMainPage);

                    } else {
                        Log.e("AUTH", it.getError().toString());
                    }
                });
            }
        });


//BOUTON CREATION DE COMTPE > RENVOI A LA PAGE D'ENREGISTREMENT
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewRegister) {
                Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                viewRegister.getContext().startActivity(intentRegister);
            }
        });


//BOUTON CREATION DE COMTPE > RENVOI A LA PAGE D'ENREGISTREMENT
        mForgottenPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewForgotten) {
                Intent intentForgotten = new Intent(LoginActivity.this, ForgottenPassword.class);
                viewForgotten.getContext().startActivity(intentForgotten);
            }
        });
    }
}

