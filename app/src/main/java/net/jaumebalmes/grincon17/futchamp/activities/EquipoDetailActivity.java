package net.jaumebalmes.grincon17.futchamp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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
import com.google.gson.Gson;
import net.jaumebalmes.grincon17.futchamp.R;
import net.jaumebalmes.grincon17.futchamp.adapters.MyJugadorRecyclerViewAdapter;
import net.jaumebalmes.grincon17.futchamp.conexion.Api;
import net.jaumebalmes.grincon17.futchamp.conexion.Enlace;
import net.jaumebalmes.grincon17.futchamp.fragments.AddLeagueDialogFragment;
import net.jaumebalmes.grincon17.futchamp.fragments.LoginDialogFragment;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnAddLeagueDialogListener;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnListJugadorInteractionListener;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnLoginDialogListener;
import net.jaumebalmes.grincon17.futchamp.models.Equipo;
import net.jaumebalmes.grincon17.futchamp.models.Jugador;
import net.jaumebalmes.grincon17.futchamp.repositoryApi.CoordinadorRepositoryApi;
import net.jaumebalmes.grincon17.futchamp.repositoryApi.JugadorRepositoryApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Esta activity muestra la vista del detalle de un equipo.
 * @author guillermo
 */
public class EquipoDetailActivity extends AppCompatActivity implements OnLoginDialogListener, OnAddLeagueDialogListener {
    private static final String TAG_JUGADOR = "JUGADOR"; //  Para mostrar mensajes por consola
    private static final int COLUMNS = 3;
    private static final String TAG_LOGIM = "LOGIN";
    private SharedPreferences preferences;
    private MenuInflater inflater;
    private LoginDialogFragment loginDialogFragment;
    private Equipo equipo;
    private String nombreEquipo;
    private OnListJugadorInteractionListener mListener;
    private List<Jugador> jugadorList;
    private boolean longClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo_detail);
        Gson gson = new Gson();
        equipo = gson.fromJson(getIntent().getStringExtra(getString(R.string.equipo_json)), Equipo.class);
        nombreEquipo = equipo.getName();
        toolbarConf();
        mListener = new OnListJugadorInteractionListener() {
            @Override
            public void onJugadorClickListener(Jugador jugador) {
                String json = new Gson().toJson(jugador);
                Intent sendJugador = new Intent(EquipoDetailActivity.this, JugadorDetailActivity.class);
                sendJugador.putExtra(getString(R.string.jugador_json), json);
                startActivity(sendJugador);
            }

            @Override
            public void onJugadorLongClickListener(Jugador jugador) {
                longClick = true;
                invalidateOptionsMenu();
            }
        };

        ImageView imageViewLogo = findViewById(R.id.imageViewEquipoLogo);
        loadImg(equipo.getLogo(), imageViewLogo);
        obtenerDatosJugadores();
    }

    @Override
    protected void onResume() {
        super.onResume();
        preferences = getSharedPreferences(getString(R.string.my_pref), Context.MODE_PRIVATE);
        invalidateOptionsMenu();
    }

    /**
     * Configuración del toolbar
     */
    private void toolbarConf() {
        Toolbar toolbar = findViewById(R.id.toolbar_detail_view);
        toolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView toolbarImg = findViewById(R.id.toolbar_image);
        TextView toolbarTittle = findViewById(R.id.textViewTitle);
        TextView toolbarSubTitle = findViewById(R.id.textViewSubTitle);
        toolbarTittle.setText(equipo.getName());
        toolbarSubTitle.setText(equipo.getLeague().getName());
        loadImg(equipo.getLogo(), toolbarImg);
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
            if(longClick) {
                menu.findItem(R.id.trash_icon).setVisible(true);
            } else {
                menu.findItem(R.id.trash_icon).setVisible(false);
            }
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
                loginDialogFragment = new LoginDialogFragment();
                loginDialogFragment.show(getSupportFragmentManager(), getString(R.string.login_txt));
                return true;
            case R.id.add_league:
                AddLeagueDialogFragment addLeagueDialogFragment = new AddLeagueDialogFragment();
                addLeagueDialogFragment.show(getSupportFragmentManager(), getString(R.string.add_new_league));
                return true;
            case R.id.logout:
                preferences.edit().remove(getString(R.string.my_username)).apply();
                preferences.edit().remove(getString(R.string.my_pwd)).apply();
                invalidateOptionsMenu();
                return true;
            case R.id.trash_icon:
                longClick = false;
                invalidateOptionsMenu();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onLoginClickListener(String userName, String pwd) {
        requestLogin(userName, pwd);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // =============================================================================================
    // =============================================================================================
    // CONEXION A LA API
    private void obtenerDatosJugadores() {
        Enlace enlace = new Enlace(); // para obtener los enlaces de conexion a la api
        Api api = new Api(); // para obtener la conexion a la API
        Retrofit retrofit = api.getConexion(enlace.getLink(enlace.JUGADOR));
        JugadorRepositoryApi jugadorRepositoryApi = retrofit.create(JugadorRepositoryApi.class);
        Call<ArrayList<Jugador>> jugadorAnswerCall = jugadorRepositoryApi.obtenerListaJugadoresEquipo(nombreEquipo);
        jugadorList = new ArrayList<>();
        // Aqui se realiza la solicitud al servidor de forma asincrónicamente y se obtiene 2 respuestas.
        jugadorAnswerCall.enqueue(new Callback<ArrayList<Jugador>>() {
            @Override
            public void onResponse(Call<ArrayList<Jugador>> call, Response<ArrayList<Jugador>> response) {

                if (response.isSuccessful()) {
                    // Aqui se aplica a la vista los datos obtenidos de la API que estan almacenados en el ArrayList
                    jugadorList = response.body();

                    RecyclerView recyclerView = findViewById(R.id.recycler_jugadores_equipo);
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), COLUMNS));
                    recyclerView.setAdapter(new MyJugadorRecyclerViewAdapter(getApplicationContext(), jugadorList, mListener));
                    // Muestra los datos que llegan en la consola
                    for (int i = 0; i < jugadorList.size(); i++) {
                        Log.e(TAG_JUGADOR, "Liga: " + jugadorList.get(i).getNombre());
                    }

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error en la descarga.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 500);
                    toast.show();
                    Log.e(TAG_JUGADOR, " ERROR AL CARGAR JUGADORES: onResponse" + response.errorBody());
                }
            }

            // Aqui, se mostrara si la conexion a la API falla.
            @Override
            public void onFailure(Call<ArrayList<Jugador>> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error en la conexion a la red.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 500);
                toast.show();
                Log.e(TAG_JUGADOR, " => ERROR LISTA JUGADORES => onFailure: " + t.getMessage());
            }
        });
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
                    Log.d(TAG_LOGIM, " RESPUESTA DE SEGURIDAD: " + response.body());
                    invalidateOptionsMenu();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error en la descarga.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 500);
                    toast.show();
                    Log.e(TAG_LOGIM, " NO TIENE AUTORIZACION: onResponse: " + response.errorBody());
                }
            }
            // Aqui, se mostrará si la conexión a la API falla.
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error en la conexion a la red.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 500);
                toast.show();
                Log.e(TAG_LOGIM, " => ERROR VERIFICAR LA CONEXION => onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onAddLeagueClickListener(String name, Drawable drawable) {

    }
}
