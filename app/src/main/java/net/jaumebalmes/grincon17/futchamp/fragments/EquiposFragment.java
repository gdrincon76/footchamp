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
import net.jaumebalmes.grincon17.futchamp.models.EquiposViewModel;

public class EquiposFragment extends Fragment {

    private EquiposViewModel equiposViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        equiposViewModel =
                ViewModelProviders.of(this).get(EquiposViewModel.class);
        View root = inflater.inflate(R.layout.fragment_equipos, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        equiposViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
