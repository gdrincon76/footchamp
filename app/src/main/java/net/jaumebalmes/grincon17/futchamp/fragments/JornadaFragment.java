package net.jaumebalmes.grincon17.futchamp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import net.jaumebalmes.grincon17.futchamp.R;
import net.jaumebalmes.grincon17.futchamp.adapters.MyJornadaRecyclerViewAdapter;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnListJornadaInteractionListener;
import net.jaumebalmes.grincon17.futchamp.models.Jornada;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Contenedor de la vista de Jornadas
 * @author guillermo
 */
public class JornadaFragment extends Fragment {

    private List<Jornada> jornadaList;
    private OnListJornadaInteractionListener mListener;

    public JornadaFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jornadaList = new ArrayList<>();
        // TODO -> sustituir por retrofit
        try {
            InputStream stream = requireActivity().getAssets().open("jornadas.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            String json = new String(buffer);
            jornadaList = Arrays.asList(new Gson().fromJson(json, Jornada[].class));
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jornada_list, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyJornadaRecyclerViewAdapter(getActivity(), jornadaList, mListener));
        }
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnListJornadaInteractionListener) {
            mListener = (OnListJornadaInteractionListener) context;
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
