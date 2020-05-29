package net.jaumebalmes.grincon17.futchamp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import net.jaumebalmes.grincon17.futchamp.R;
import net.jaumebalmes.grincon17.futchamp.conexion.Api;
import net.jaumebalmes.grincon17.futchamp.conexion.Enlace;
import net.jaumebalmes.grincon17.futchamp.conexion.Firebase;
import net.jaumebalmes.grincon17.futchamp.fragments.AddEquipoDialogFragment;
import net.jaumebalmes.grincon17.futchamp.fragments.AddLeagueDialogFragment;
import net.jaumebalmes.grincon17.futchamp.fragments.LeagueFragment;
import net.jaumebalmes.grincon17.futchamp.fragments.LoginDialogFragment;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnAddEquipoDialogListener;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnAddLeagueDialogListener;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnLoginDialogListener;
import net.jaumebalmes.grincon17.futchamp.models.Equipo;
import net.jaumebalmes.grincon17.futchamp.models.Jugador;
import net.jaumebalmes.grincon17.futchamp.models.League;
import net.jaumebalmes.grincon17.futchamp.repositoryApi.CoordinadorRepositoryApi;
import net.jaumebalmes.grincon17.futchamp.repositoryApi.LeagueRepositoryApi;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Esta activity muestra la vista del detalle de un jugador.
 * @author guillermo
 */
public class JugadorDetailActivity extends AppCompatActivity implements OnLoginDialogListener, OnAddLeagueDialogListener,
        OnAddEquipoDialogListener {
    private static final String TAG = "LOGIN";
    private SharedPreferences preferences;
    private MenuInflater inflater;
    private Equipo equipo;
    private Enlace enlace;
    private Api api;
    private Retrofit retrofit;
    private LeagueRepositoryApi leagueRepositoryApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador_detail);
        new Firebase().authFirebaseUser();
        enlace = new Enlace(); // para obtener los enlaces de conexion a la api
        api = new Api(); // para obtener la conexion a la API
        Gson gson = new Gson();
        Jugador jugador = gson.fromJson(getIntent().getStringExtra(getString(R.string.jugador_json)), Jugador.class);
        equipo = jugador.getEquipo();
        toolbarConf();

        ImageView jugadorImg = findViewById(R.id.imageViewJugadorImg);
        TextView jugadorName = findViewById(R.id.text_jugador_name);
        TextView jugadorSurName = findViewById(R.id.text_jugador_surname);
        TextView jugadorDorsal = findViewById(R.id.text_jugador_dorsal);
        TextView jugadorPosicion = findViewById(R.id.text_jugador_posicion);
        TextView jugadorEmail = findViewById(R.id.text_jugador_email);

        jugadorName.setText(jugador.getNombre());
        jugadorSurName.setText(jugador.getApellidos());
        jugadorDorsal.setText(jugador.getDorsal());
        jugadorPosicion.setText(jugador.getPosicion());
        jugadorEmail.setText(jugador.getEmail());
        loadImg(jugador.getImagen(), jugadorImg);
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
                .centerInside() //
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
            case R.id.add_league:
                AddLeagueDialogFragment addLeagueDialogFragment = new AddLeagueDialogFragment();
                addLeagueDialogFragment.show(getSupportFragmentManager(), getString(R.string.add_new_league));
                return true;
            case R.id.add_team:
                AddEquipoDialogFragment addEquipoDialogFragment = new AddEquipoDialogFragment();
                addEquipoDialogFragment.show(getSupportFragmentManager(), getString(R.string.add_new_team));
                return true;

            case R.id.add_player:
                startActivity(new Intent(this, AddJugadorActivity.class));
                return true;
            case R.id.logout:
                preferences.edit().remove(getString(R.string.my_username)).apply();
                preferences.edit().remove(getString(R.string.my_pwd)).apply();
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
    public void onAddLeagueClickListener(String name, Uri uri) {
        Log.d("NAME: ", name + " URI: " + uri);
        postLeague(name, uri);
    }

    @Override
    public void onAddEquipoClickListener(String name, String leagueName) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // =============================================================================================
    // =============================================================================================
    // LLamadas a la API
    private void postLeague(final String leagueName, Uri filePath) {
        if (filePath != null) {
            retrofit = api.getConexion(enlace.getLink(enlace.LIGA));
            leagueRepositoryApi = retrofit.create(LeagueRepositoryApi.class);

            FirebaseStorage storage = FirebaseStorage.getInstance();
            final StorageReference reference = storage.getReference().child("images/" + UUID.randomUUID().toString());
            reference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.i("STATE", "carga OK");
                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String url = task.getResult().toString();
                            Log.i("RESULT", url);
                            League league = new League();
                            league.setName(leagueName);
                            league.setLogo(url);

                            Call<League> addNewLeague = leagueRepositoryApi.postLeague(league);
                            addNewLeague.enqueue(new Callback<League>() {
                                @Override
                                public void onResponse(Call<League> call, Response<League> response) {
                                    if(response.isSuccessful()) {
                                        Log.i("LEAGUE", " RESPUESTA: " + response.body());
                                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLeagueList, new LeagueFragment()).commit();
                                    } else {
                                        Log.e("LEAGUE", "ERROR: " + response.errorBody());
                                    }
                                }

                                @Override
                                public void onFailure(Call<League> call, Throwable t) {
                                    Log.e("LEAGUE", " => ERROR  => onFailure: " + t.getMessage());
                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("STATE", "Fallo: " + e.getMessage());
                }
            });
        }
    }

    private void requestLogin(final String user, final String pwd) {

        enlace = new Enlace(); // para obtener los enlaces de conexion a la api
        api = new Api(); // para obtener la conexion a la API
        retrofit = api.getConexion(enlace.getLink(enlace.COORDINADOR));
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
            // Aqui, se mostrará si la conexión a la API falla.
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.login_failed), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 500);
                toast.show();
                Log.e(TAG, " => ERROR VERIFICAR LA CONEXION => onFailure: " + t.getMessage());
            }
        });
    }
}
