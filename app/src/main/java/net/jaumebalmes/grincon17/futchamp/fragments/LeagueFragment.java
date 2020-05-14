package net.jaumebalmes.grincon17.futchamp.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.Gson;
import net.jaumebalmes.grincon17.futchamp.R;
import net.jaumebalmes.grincon17.futchamp.adapters.MyLeagueRecyclerViewAdapter;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnListLeagueInteractionListener;
import net.jaumebalmes.grincon17.futchamp.models.League;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Contenedor de la vista de ligas
 * @author guillermo
 */
public class LeagueFragment extends Fragment {

    private int mColumnCount = 2;
    private List<League> leagueList;
    private OnListLeagueInteractionListener mListener;

    public LeagueFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        leagueList = new ArrayList<>();
        // TODO: esto hay que sustituirlo por retrofit
        try {
            InputStream stream = requireActivity().getAssets().open("leagues.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            String json = new String(buffer);
            leagueList = Arrays.asList(new Gson().fromJson(json, League[].class));
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_league_list, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            recyclerView.setAdapter(new MyLeagueRecyclerViewAdapter(getActivity(), leagueList, mListener));
        }
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnListLeagueInteractionListener) {
            mListener = (OnListLeagueInteractionListener) context;
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
