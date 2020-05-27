package net.jaumebalmes.grincon17.futchamp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import net.jaumebalmes.grincon17.futchamp.R;
import net.jaumebalmes.grincon17.futchamp.conexion.Api;
import net.jaumebalmes.grincon17.futchamp.conexion.Enlace;
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
import net.jaumebalmes.grincon17.futchamp.repositoryApi.CoordinadorRepositoryApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Esta activity carga la vista principal que consiste en un menu inferior de navegación de tres pestañas
 *
 * @author guillermo
 */
public class LeagueDetailActivity extends AppCompatActivity implements OnLoginDialogListener,
        OnListJornadaInteractionListener, OnListEquipoInteractionListener, OnListJugadorInteractionListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "LOGIN";
    private SharedPreferences preferences;
    private MenuInflater inflater;
    private League league;
    private Bundle bundle;
    private boolean longClick;

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

    @Override
    protected void onResume() {
        super.onResume();
        preferences = getSharedPreferences(getString(R.string.my_pref), Context.MODE_PRIVATE);
        invalidateOptionsMenu();
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
                Fragment currentFragment = new EquipoFragment();
                currentFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, currentFragment, "LEAGUE").commit();
                break;
            case R.id.navigation_jornada :
                currentFragment = new JornadaFragment();
                currentFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, currentFragment, "LEAGUE").commit();
                break;
            case R.id.navigation_jugadores :
                currentFragment = new JugadorFragment();
                currentFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, currentFragment, "LEAGUE").commit();
                break;
        }
        return true;
    }

    /**
     * Configuración del toolbar
     */
    private void toolbarConf() {
        Toolbar toolbar = findViewById(R.id.toolbar_detail_view);
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
        inflater = getMenuInflater();
        if (preferences.contains(getString(R.string.my_username)) && preferences.contains(getString(R.string.my_username))) {
            inflater.inflate(R.menu.toolbar_coordinator_menu, menu);
        } else {
            inflater.inflate(R.menu.toolbar_login_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (preferences.contains(getString(R.string.my_username)) && preferences.contains(getString(R.string.my_username))) {
            menu.clear();
            inflater.inflate(R.menu.toolbar_coordinator_menu, menu);
        }
        if(longClick) {
            menu.findItem(R.id.trash_icon).setVisible(true);
        } else {
            menu.findItem(R.id.trash_icon).setVisible(false);
        }
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
                LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
                loginDialogFragment.show(getSupportFragmentManager(), getString(R.string.login_txt));
                return true;
            case R.id.logout:
                preferences.edit().remove(getString(R.string.my_username)).apply();
                preferences.edit().remove(getString(R.string.my_pwd)).apply();
                invalidateOptionsMenu();
            case R.id.trash_icon:
                longClick = false;
                invalidateOptionsMenu();
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
        requestLogin(userName, pwd);
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

    @Override
    public void onJugadorLongClickListener(Jugador jugador) {
        longClick = true;
        invalidateOptionsMenu();
    }

    private void requestLogin(final String user, final String pwd) {

        Enlace enlace = new Enlace(); // para obtener los enlaces de conexion a la api
        Api api = new Api(); // para obtener la conexion a la API
        Retrofit retrofit = api.getConexion(enlace.getLink(enlace.COORDINADOR));
        CoordinadorRepositoryApi coordinadorRepositoryApi = retrofit.create(CoordinadorRepositoryApi.class);
        Call<Boolean> loginSuccess = coordinadorRepositoryApi.verificarAutorizacion(user, pwd);

        // Aqui se realiza la solicitud al servidor de forma asincrónicamente y se obtiene 2 respuestas.
        loginSuccess.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                if (response.isSuccessful()) {
                    // Aqui se aplica a la vista los datos obtenidos de la API que estan almacenados en el ArrayList
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(getString(R.string.my_username), user);
                    editor.putString(getString(R.string.my_pwd), pwd);
                    editor.apply();
                    Log.d(TAG, " RESPUESTA DE SEGURIDAD: " + response.body());
                    invalidateOptionsMenu();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error en la descarga.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 500);
                    toast.show();
                    Log.e(TAG, " NO TIENE AUTORIZACION: onResponse: " + response.errorBody());
                }
            }
            // Aqui, se mostrara si la conexion a la API falla.
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error en la conexion a la red.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 500);
                toast.show();
                Log.e(TAG, " => ERROR VERIFICAR LA CONEXION => onFailure: " + t.getMessage());
            }
        });
    }
}
