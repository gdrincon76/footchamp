package net.jaumebalmes.grincon17.futchamp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import net.jaumebalmes.grincon17.futchamp.fragments.LoginDialogFragment;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnListJugadorInteractionListener;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnLoginDialogListener;
import net.jaumebalmes.grincon17.futchamp.models.Equipo;
import net.jaumebalmes.grincon17.futchamp.models.Jugador;
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
public class EquipoDetailActivity extends AppCompatActivity implements OnLoginDialogListener {
    private static final String TAG = "JUGADOR"; //  Para mostrar mensajes por consola
    private static final int COLUMNS = 3;
    private LoginDialogFragment loginDialogFragment;
    private Equipo equipo;
    private String nombreEquipo;
    private OnListJugadorInteractionListener mListener;
    private List<Jugador> jugadorList;

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
        };

        ImageView imageViewLogo = findViewById(R.id.imageViewEquipoLogo);
        loadImg(equipo.getLogo(), imageViewLogo);
        obtenerDatosJugadores();

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

    @Override
    public void onLoginClickListener(String userName, String pwd) {

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

                    Log.d("EQUIPO", String.valueOf(jugadorList.get(0)));

                    RecyclerView recyclerView = findViewById(R.id.recycler_jugadores_equipo);
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), COLUMNS));
                    recyclerView.setAdapter(new MyJugadorRecyclerViewAdapter(getApplicationContext(), jugadorList, mListener));
                    // Muestra los datos que llegan en la consola
                    for (int i = 0; i < jugadorList.size(); i++) {
                        Log.e(TAG, "Liga: " + jugadorList.get(i).getNombre());
                    }

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error en la descarga.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 500);
                    toast.show();
                    Log.e(TAG, " ERROR AL CARGAR JUGADORES: onResponse" + response.errorBody());
                }
            }

            // Aqui, se mostrara si la conexion a la API falla.
            @Override
            public void onFailure(Call<ArrayList<Jugador>> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error en la conexion a la red.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 500);
                toast.show();
                Log.e(TAG, " => ERROR LISTA JUGADORES => onFailure: " + t.getMessage());
            }
        });
    }

}
