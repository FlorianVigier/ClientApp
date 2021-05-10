package com.example.clientapp.ui.eco;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.clientapp.R;

public class EcoFragment extends Fragment {

    private EcoViewModel ecoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ecoViewModel =
                new ViewModelProvider(this).get(EcoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_eco, container, false);
        final TextView textView = root.findViewById(R.id.text_eco);
        ecoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}