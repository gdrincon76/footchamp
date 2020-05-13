package net.jaumebalmes.grincon17.futchamp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import net.jaumebalmes.grincon17.futchamp.R;

public class EquipoFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipo, container, false);
        TextView textView = view.findViewById(R.id.text_dashboard);
        textView.setText("Equipo");
        return view;
    }
}
