package net.jaumebalmes.grincon17.futchamp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import net.jaumebalmes.grincon17.futchamp.R;
import net.jaumebalmes.grincon17.futchamp.models.JugadoresViewModel;

/**
 * PRUEBAS DE CONEXION AQUI PARA LA LISTA DE JUGADORES
 */

public class JugadoresFragment extends Fragment {

    private JugadoresViewModel equiposViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        equiposViewModel =
                ViewModelProviders.of(this).get(JugadoresViewModel.class);
        View root = inflater.inflate(R.layout.fragment_jugadores, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        equiposViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
