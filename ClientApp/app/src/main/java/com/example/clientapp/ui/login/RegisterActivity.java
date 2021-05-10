package com.example.clientapp.ui.login;
import androidx.appcompat.app.AppCompatActivity;
import com.example.clientapp.R;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.bson.Document;

import java.util.function.Function;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class RegisterActivity extends AppCompatActivity {

    boolean isFirstNameValid = false, isFamilyNameValid = false, isEmailValid = false, isPasswordValid = false, isPasswordRepeatValid = false;
    boolean regexPassword = false, regexPasswordRepeat = false, regexEmail = false;

    private Button mRegisterValidation;
    private EditText mFirstName;
    private EditText mFamilyName;
    private EditText mEmail;
    private EditText mPassWord;
    private EditText mPasswordRepeat;
    private App ClientApp;
    String ClientEmail, ClientPassword;

    MongoDatabase mongoDatabase;
    MongoClient mongoClient;

    // Référence de l'application sur https://realm.mongodb.com/
    String ClientAppId = "clientapp-hqokj";
    String ClientAppDBAccount = "ClientApp";
    String ClientAppDBPassword = "ClientAppPassword123456789!";

   // public RealmAsyncTask registerUserAsync(String email,
    //                                        String password,
      //                                      App.Callback<Void> callback);

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Valide l'apparaition de la fleche de retour
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Déclaration des éléments du layout
        Button mRegisterValidation = (Button) findViewById(R.id.activity_register_login);
        EditText mFirstName = (EditText) findViewById(R.id.activity_register_firstName);
        EditText mFamilyName = (EditText) findViewById(R.id.activity_register_familyName);
        EditText mEmail = (EditText) findViewById(R.id.activity_register_eMail);
        EditText mPassword = (EditText) findViewById(R.id.activity_register_password);
        EditText mPasswordRepeat = (EditText) findViewById(R.id.activity_register_repeatPassword);

        // Référence au nom de l'app
        String ClientAppId = "clientapp-hqokj";

        //Déclaration des variables
        String emailPattern = "^(.+)@(.+)$";
        String passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@!#$%^&+=]).{8,})";

// VERIFICATION SI CHAMP DE TEXTE FIRSTNAME VIDE OU NON
        mFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) { isFirstNameValid = false; }
                else { isFirstNameValid = true; }

                if (isFirstNameValid && isFamilyNameValid && isEmailValid && regexEmail && isPasswordValid && regexPassword && isPasswordRepeatValid && regexPasswordRepeat) { mRegisterValidation.setEnabled(true); }
                else { mRegisterValidation.setEnabled(false); }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

// VERIFICATION SI CHAMP DE TEXTE FAMILYNAME VIDE OU NON
        mFamilyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) { isFamilyNameValid = false; }
                else { isFamilyNameValid = true; }

                if (isFirstNameValid && isFamilyNameValid && isEmailValid && regexEmail && isPasswordValid && regexPassword && isPasswordRepeatValid && regexPasswordRepeat) { mRegisterValidation.setEnabled(true); }
                else { mRegisterValidation.setEnabled(false); }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

// VERIFICATION SI CHAMP DE TEXTE EMAIL VIDE OU NON
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) { isEmailValid = false; }
                else { isEmailValid = true; }

                if (mEmail.getText().toString().trim().matches(emailPattern)) { regexEmail = true; }
                else { regexEmail = false; }

                if (isFirstNameValid && isFamilyNameValid && isEmailValid && regexEmail && isPasswordValid && regexPassword && isPasswordRepeatValid && regexPasswordRepeat) { mRegisterValidation.setEnabled(true); }
                else { mRegisterValidation.setEnabled(false); }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

// VERIFICATION SI CHAMP DE TEXTE PASSWORD VIDE OU NON
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) { isPasswordValid = false; }
                else { isPasswordValid = true; }

                if (mPassword.getText().toString().trim().matches(passwordPattern)) { regexPassword = true; }
                else { regexPassword = false; }

                if (isFirstNameValid && isFamilyNameValid && isEmailValid && regexEmail && isPasswordValid && regexPassword && isPasswordRepeatValid && regexPasswordRepeat) { mRegisterValidation.setEnabled(true); }
                else { mRegisterValidation.setEnabled(false); }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

// VERIFICATION SI CHAMP DE TEXTE REPEAT PASSWORD VIDE OU NON
        mPasswordRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) { isPasswordRepeatValid = false; }
                else { isPasswordRepeatValid = true; }

                if (mPasswordRepeat.getText().toString().trim().matches(passwordPattern)) { regexPasswordRepeat = true; }
                else { regexPasswordRepeat = false; }

                if (isFirstNameValid && isFamilyNameValid && isEmailValid && regexEmail && isPasswordValid && regexPassword && isPasswordRepeatValid && regexPasswordRepeat) { mRegisterValidation.setEnabled(true); }
                else { mRegisterValidation.setEnabled(false); }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });


//BOUTON DE VALIDATION DE L'ENREGISTREMENT
        mRegisterValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewRegister) {
                App ClientApp = new App(new AppConfiguration.Builder(ClientAppId).build());

                ClientEmail = mEmail.getText().toString();
                ClientPassword = mPassword.getText().toString();

                ClientApp.getEmailPassword().registerUserAsync(ClientEmail, ClientPassword, it -> {
                    if (it.isSuccess()) {
                        Log.i("EXAMPLE", "Successfully registered user.");
                        Intent intentForgotten = new Intent(RegisterActivity.this, LoginActivity.class);
                        viewRegister.getContext().startActivity(intentForgotten);
                    } else {
                        Log.e("EXAMPLE", "Failed to register user: " + it.getError().getErrorMessage());
                    }
                });
            }
        });


    }
}