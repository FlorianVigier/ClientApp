package com.example.clientapp.ui.home;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import org.bson.Document;
import java.util.Arrays;
import java.util.List;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.functions.Functions;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class HomeViewModel extends ViewModel {

    String ClientAppId = "clientapp-hqokj";
    App ClientApp = new App(new AppConfiguration.Builder(ClientAppId).build());

    private MutableLiveData<String> mText = new MutableLiveData<>();

    public HomeViewModel() {


// Acces à la base
        User user = ClientApp.currentUser();
        Functions functionsManager = ClientApp.getFunctions(user);

// Définition des arguments de la fonction
        String ListofArg[] = new String[] {"florian.vigier@gmail.com", "testname"};
        List<String> args = Arrays.asList(ListofArg);

        //functionsManager.callFunctionAsync("Edit_User", args, Document.class)

//Appel de la fonction Edit_User

        functionsManager.callFunctionAsync("Edit_User", args, Document.class, result -> {
            if (result.isSuccess()) {
                Log.v("Collection Found", "La collection est trouvée : " + result.get());
                //UserContent = result.get();
            } else {
                Log.e("Collection Not Found", "La collection n'est pas trouvée : " + result.getError());
            }
        });

        mText.setValue("T'es un gros ... gros nul !!!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}


//val completableDeferred = CompletableDeferred<App.Result<Document>>()
//val functions: Functions = app.getFunctions(app.currentUser())
//functions.callFunctionAsync("assignNewUser", listOf(user.email, user.name), Document::class.java){
//completableDeferred.complete(it)
//}

