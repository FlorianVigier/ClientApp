package com.example.clientapp.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clientapp.R;

public class GenerateNewPassword extends Activity {
    private Button mNewPassword;
    private EditText mUserName;
    boolean isEmailValid, isRegexValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String emailPattern = "^(.+)@(.+)$";
        EditText mUserName = (EditText) findViewById(R.id.activity_forgotten_password_username);
        Button mNewPassword = (Button) findViewById(R.id.activity_forgotten_password_sendnewpassword);

//TEST USERNAME
        mUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    isEmailValid = false;
                } else {
                    isEmailValid= true;
                }
                if (mUserName.getText().toString().trim().matches(emailPattern)) {
                    isRegexValid = true;
                    Toast.makeText(getApplicationContext(),"Le format de l'adresse mail est valide",Toast.LENGTH_SHORT).show();
                } else {
                    isRegexValid= false;
                    Toast.makeText(getApplicationContext(),"Le format de l'adresse mail n'est pas valide",Toast.LENGTH_SHORT).show();
                }
                if (isEmailValid && isRegexValid) {
                    mNewPassword.setEnabled(true);
                } else {
                    mNewPassword.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

//BOUTON MDP OUBLIE > RENVOI UN NOUVEAU MAIL
        mNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewForgotten) {

                //FONCTION POUR ENVOYER LE NOUVEAU USER

                Intent intentForgotten = new Intent(GenerateNewPassword.this, LoginActivity.class);
                viewForgotten.getContext().startActivity(intentForgotten);
            }
        });

    }
}
