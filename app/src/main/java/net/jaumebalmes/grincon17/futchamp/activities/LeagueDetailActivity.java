package net.jaumebalmes.grincon17.futchamp.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import net.jaumebalmes.grincon17.futchamp.R;
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
 * @author guillermo
 */
public class LeagueDetailActivity extends AppCompatActivity implements OnLoginDialogListener,
        OnListJornadaInteractionListener, OnListEquipoInteractionListener, OnListJugadorInteractionListener {
    LoginDialogFragment loginDialogFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_detail);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_jornada, R.id.navigation_equipos, R.id.navigation_jugadores)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
    /**
     * Este método crea el menú del toolbar
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
     * @param userName el nombre de usuario introducido en el campo de TextEdit
     * @param pwd la contraseña introducida en el campo de TextEdit
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

    }

    @Override
    public void onJugadorClickListener(Jugador jugador) {

    }
}
