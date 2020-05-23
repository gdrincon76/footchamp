package net.jaumebalmes.grincon17.futchamp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import net.jaumebalmes.grincon17.futchamp.R;
import net.jaumebalmes.grincon17.futchamp.fragments.LoginDialogFragment;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnListLeagueInteractionListener;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnLoginDialogListener;
import net.jaumebalmes.grincon17.futchamp.models.League;

/**
 * Esta activity carga las listas de ligas
 *
 * @author guillermo
 */
public class LeaguesActivity extends AppCompatActivity implements OnListLeagueInteractionListener, OnLoginDialogListener {
    LoginDialogFragment loginDialogFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leagues);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // TODO -> Aquí se debería comprobar con Shared preference si hay un usuario autenticado.
     }
    /**
     * Este método crea el menú del toolbar
     *
     * @param menu el menú del sistema
     * @return true para que muestre el menú
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO: implementar una condición si el usuario es coordinador y está logueado usar su menú,
        //  en caso contrario cargar el menú de login
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_login_menu, menu);
        return true;
    }

    /**
     * Este método sirve para elegir un elemento del menú
     *
     * @param item los elementos del menú
     * @return el padre
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_icon:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.account_login:
                loginDialogFragment = new LoginDialogFragment();
                loginDialogFragment.show(getSupportFragmentManager(), getString(R.string.login_txt));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Es la implementación del click del login de la ventana flotante
     *
     * @param userName el nombre de usuario introducido en el campo de TextEdit
     * @param pwd      la contraseña introducida en el campo de TextEdit
     */
    @Override
    public void onLoginClickListener(String userName, String pwd) {
        // TODO: aquí se debe implementar la llamada a la api para comprobar que el usuario se loguea,
        //  guardarlo en el shared preferences cargar el nuevo menú de usuario autenticado
        Toast.makeText(this, "Name: " + userName + "pwd: " + pwd, Toast.LENGTH_LONG).show();
    }

    /**
     * Este método es la implementación de la interfaz OnListJugadorInteractionListener
     * que abre la liga seleccionada de la lista
     *
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
