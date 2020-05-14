package net.jaumebalmes.grincon17.futchamp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import net.jaumebalmes.grincon17.futchamp.R;
import net.jaumebalmes.grincon17.futchamp.adapters.MyJugadorRecyclerViewAdapter;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnListJugadorInteractionListener;
import net.jaumebalmes.grincon17.futchamp.models.Jugador;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contenedor de la vista de Jugadores
 * @author guillermo
 */
public class JugadorFragment extends Fragment {

    private List<Jugador> jugadorList;
    private OnListJugadorInteractionListener mListener;

    public JugadorFragment(){ }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jugadorList = new ArrayList<>();
        // TODO -> Sustituir por retrofit
        try {
            InputStream stream = requireActivity().getAssets().open("jugadores.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            String json = new String(buffer);
            jugadorList = Arrays.asList(new Gson().fromJson(json, Jugador[].class));
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jugador_list, container, false);
        if(view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyJugadorRecyclerViewAdapter(getActivity(), jugadorList, mListener));
        }
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnListJugadorInteractionListener) {
            mListener = (OnListJugadorInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
