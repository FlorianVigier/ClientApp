package com.example.clientapp.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.clientapp.R;
import com.example.clientapp.ui.login.ForgottenPassword;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    private Button mButtonForgottenPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        Button mButtonForgottenPassword = (Button) view.findViewById(R.id.fragment_account_forgottenPassword);
        mButtonForgottenPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ForgottenPassword.class);
                startActivity(intent);
            }
        });
        return view;
    }
}

