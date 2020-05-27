package net.jaumebalmes.grincon17.futchamp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.widget.Toast;

import com.google.gson.Gson;

import net.jaumebalmes.grincon17.futchamp.R;
import net.jaumebalmes.grincon17.futchamp.conexion.Api;
import net.jaumebalmes.grincon17.futchamp.conexion.Enlace;
import net.jaumebalmes.grincon17.futchamp.fragments.AddLeagueDialogFragment;
import net.jaumebalmes.grincon17.futchamp.fragments.LoginDialogFragment;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnAddLeagueDialogListener;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnListLeagueInteractionListener;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnLoginDialogListener;
import net.jaumebalmes.grincon17.futchamp.models.League;
import net.jaumebalmes.grincon17.futchamp.repositoryApi.CoordinadorRepositoryApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Esta activity carga las listas de ligas
 *
 * @author guillermo
 */
public class LeaguesActivity extends AppCompatActivity implements OnListLeagueInteractionListener, OnLoginDialogListener,
        OnAddLeagueDialogListener {
    private static final String TAG = "LOGIN";
    private SharedPreferences preferences;
    private MenuInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leagues);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO -> Aquí se debería comprobar con Shared preference si hay un usuario autenticado.
     }

    @Override
    protected void onResume() {
        super.onResume();
        preferences = getSharedPreferences(getString(R.string.my_pref), Context.MODE_PRIVATE);
        invalidateOptionsMenu();
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
            case R.id.logout:
                preferences.edit().remove(getString(R.string.my_username)).apply();
                preferences.edit().remove(getString(R.string.my_pwd)).apply();
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
        requestLogin(userName,pwd);

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

    @Override
    public void onAddLeagueClickListener(String name, Drawable drawable) {

    }
}
