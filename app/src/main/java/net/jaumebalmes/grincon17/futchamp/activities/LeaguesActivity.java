package net.jaumebalmes.grincon17.futchamp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.gson.Gson;

import net.jaumebalmes.grincon17.futchamp.R;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnLeagueListInteractionListener;
import net.jaumebalmes.grincon17.futchamp.models.League;

public class LeaguesActivity extends AppCompatActivity implements OnLeagueListInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onLeagueClickListener(League league) {
        String json = new Gson().toJson(league);
        Intent sendLeague = new Intent(LeaguesActivity.this, LeagueDetailActivity.class);
        sendLeague.putExtra(getString(R.string.league_json), json);
        startActivity(sendLeague);
    }
}
