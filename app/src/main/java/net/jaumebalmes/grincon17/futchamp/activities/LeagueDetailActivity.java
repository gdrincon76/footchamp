package net.jaumebalmes.grincon17.futchamp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import net.jaumebalmes.grincon17.futchamp.R;
import net.jaumebalmes.grincon17.futchamp.fragments.EquipoFragment;
import net.jaumebalmes.grincon17.futchamp.fragments.JornadaFragment;
import net.jaumebalmes.grincon17.futchamp.fragments.JugadorFragment;
import net.jaumebalmes.grincon17.futchamp.fragments.LoginDialogFragment;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnListEquipoInteractionListener;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnListJornadaInteractionListener;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnListJugadorInteractionListener;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnLoginDialogListener;
import net.jaumebalmes.grincon17.futchamp.models.Equipo;
import net.jaumebalmes.grincon17.futchamp.models.Jornada;
import net.jaumebalmes.grincon17.futchamp.models.Jugador;
import net.jaumebalmes.grincon17.futchamp.models.League;


/**
 * Esta activity carga la vista principal que consiste en un menu inferior de navegación de tres pestañas
 *
 * @author guillermo
 */
public class LeagueDetailActivity extends AppCompatActivity implements OnLoginDialogListener,
        OnListJornadaInteractionListener, OnListEquipoInteractionListener, OnListJugadorInteractionListener, BottomNavigationView.OnNavigationItemSelectedListener {
    LoginDialogFragment loginDialogFragment;
    Toolbar toolbar;
    League league;
    Bundle bundle;
    Fragment currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_detail);
        Gson gson = new Gson();
        league = gson.fromJson(getIntent().getStringExtra(getString(R.string.league_json)), League.class);
        bundle = new Bundle();
        bundle.putString("LEAGUE", league.getName());
        toolbarConf();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment,new JornadaFragment()).commit();
        navView.setOnNavigationItemSelectedListener(this);
    }

    /**
     * Método para navegar entre los fragments.
     * @param item el item seleccionado del menú.
     * @return true
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_equipos :
                currentFragment = new EquipoFragment();
                currentFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,currentFragment, "LEAGUE").commit();
                break;
            case R.id.navigation_jornada :
                currentFragment = new JornadaFragment();
                currentFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,currentFragment, "LEAGUE").commit();
                break;
            case R.id.navigation_jugadores :
                currentFragment = new JugadorFragment();
                currentFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,currentFragment, "LEAGUE").commit();
                break;
        }
        return true;
    }

    /**
     * Configuración del toolbar
     */
    private void toolbarConf() {
        toolbar = findViewById(R.id.toolbar_detail_view);
        toolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageView toolbarImg = findViewById(R.id.toolbar_image);
        TextView toolbarTittle = findViewById(R.id.textViewTitle);
        getResources().getDimension(R.dimen.toolbar_title_center);
        toolbarTittle.setY(getResources().getDimension(R.dimen.toolbar_title_center));
        toolbarTittle.setText(league.getName());
        loadImg(league.getLogo(), toolbarImg);
    }

    /**
     *
     * @param url de la imagen
     * @param imageView la vista para poner la imagen
     */
    private void loadImg(String url, ImageView imageView) {
        Glide.with(getApplicationContext())
                .load(url)
                .error(R.mipmap.ic_launcher)
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
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

    @Override
    public void onJornadaClickListener(Jornada jornada) {

    }

    @Override
    public void onEquipoClickListener(Equipo equipo) {
        String json = new Gson().toJson(equipo);
        Intent sendEquipo = new Intent(LeagueDetailActivity.this, EquipoDetailActivity.class);
        sendEquipo.putExtra(getString(R.string.equipo_json), json);
        startActivity(sendEquipo);
    }

    @Override
    public void onJugadorClickListener(Jugador jugador) {
        String json = new Gson().toJson(jugador);
        Intent sendJugador = new Intent(LeagueDetailActivity.this, JugadorDetailActivity.class);
        sendJugador.putExtra(getString(R.string.jugador_json), json);
        startActivity(sendJugador);

    }


}
