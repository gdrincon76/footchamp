package net.jaumebalmes.grincon17.futchamp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;

import net.jaumebalmes.grincon17.futchamp.R;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnListLeagueInteractionListener;
import net.jaumebalmes.grincon17.futchamp.models.League;
/**
 * Esta activity carga las listas de ligas
 * @author guillermo
 */
public class LeaguesActivity extends AppCompatActivity implements OnListLeagueInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leagues);
        // TODO -> Aquí se debería comprobar con Shared preference si hay un usuario autenticado.
    }
    /**
     * Este método es la implementación de la interfaz OnListJugadorInteractionListener
     * que abre la liga seleccionada de la lista
     * @param league la liga seleccionada
     */
    @Override
    public void onLeagueClickListener(League league) {
        String json = new Gson().toJson(league);
        Intent sendLeague = new Intent(LeaguesActivity.this, LeagueDetailActivity.class);
        sendLeague.putExtra(getString(R.string.league_json), json);
        startActivity(sendLeague);
    }
}
