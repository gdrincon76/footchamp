package net.jaumebalmes.grincon17.futchamp.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JugadoresViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public JugadoresViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Jugadores fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}