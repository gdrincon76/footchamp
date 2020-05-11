package net.jaumebalmes.grincon17.futchamp.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JornadaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public JornadaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Jornada fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}