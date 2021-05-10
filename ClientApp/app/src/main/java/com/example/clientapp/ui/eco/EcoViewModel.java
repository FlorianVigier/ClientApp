package com.example.clientapp.ui.eco;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EcoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EcoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Page de calcul de l'empreinte eco");
    }

    public LiveData<String> getText() {
        return mText;
    }
}