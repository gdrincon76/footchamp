package net.jaumebalmes.grincon17.futchamp.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.Gson;
import net.jaumebalmes.grincon17.futchamp.R;
import net.jaumebalmes.grincon17.futchamp.adapters.MyLeagueRecyclerViewAdapter;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnLeagueListInteractionListener;
import net.jaumebalmes.grincon17.futchamp.models.League;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeagueFragment extends Fragment {

    private int mColumnCount = 2;
    private List<League> leagueList;  // Papa obtener la lista de url de las imagene en el JSON 'leagues.json'
    private OnLeagueListInteractionListener mListener;

    public LeagueFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        leagueList = new ArrayList<>();
        // TODO: esto hay que sustituirlo por retrofit
        try {
            // Aqui se obtiene las url para las imagenes
            InputStream stream = requireActivity().getAssets().open("leagues.json"); // se agrega el archivo que contien las url
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            String json = new String(buffer);
            leagueList = Arrays.asList(new Gson().fromJson(json, League[].class)); // se extrae los datos y se guarda en el arrayList
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_league_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            // Aqui se pasa los datos al daptador de la vista
            recyclerView.setAdapter(new MyLeagueRecyclerViewAdapter(getActivity(), leagueList, mListener));
        }
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnLeagueListInteractionListener) {
            mListener = (OnLeagueListInteractionListener) context;
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
