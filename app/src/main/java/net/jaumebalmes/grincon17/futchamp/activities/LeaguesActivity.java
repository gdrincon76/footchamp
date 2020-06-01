package net.jaumebalmes.grincon17.futchamp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.gson.Gson;
import net.jaumebalmes.grincon17.futchamp.R;
import net.jaumebalmes.grincon17.futchamp.conexion.Api;
import net.jaumebalmes.grincon17.futchamp.conexion.Firebase;
import net.jaumebalmes.grincon17.futchamp.fragments.AddLeagueDialogFragment;
import net.jaumebalmes.grincon17.futchamp.fragments.LeagueFragment;
import net.jaumebalmes.grincon17.futchamp.fragments.LoginDialogFragment;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnAddLeagueDialogListener;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnListLeagueInteractionListener;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnLoginDialogListener;
import net.jaumebalmes.grincon17.futchamp.models.League;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta activity carga las listas de ligas
 *
 * @author guillermo
 */
public class LeaguesActivity extends AppCompatActivity implements OnListLeagueInteractionListener, OnLoginDialogListener,
        OnAddLeagueDialogListener {
    private static final int PERMISSION_REQUEST_CODE = 200;
    private SharedPreferences preferences;
    private boolean longClick;
    private Api api;
    private League leagueClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leagues);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new Firebase().authFirebaseUser();
        api = new Api();
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentLeagueList, new LeagueFragment()).commit();
     }

    @Override
    protected void onResume() {
        super.onResume();
        if (!cameraPermissionGranted()) {
            requestPermission();
        }
        if(!storagePermissionGranted()) {
            requestPermission();
        }
        leagueClicked = new League();
        preferences = getSharedPreferences(getString(R.string.my_pref), Context.MODE_PRIVATE);
        invalidateOptionsMenu();
    }

    /**
     * Este método crea y sobreescribe el menú
     * @param menu a modificar
     * @return true
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (preferences.contains(getString(R.string.my_username)) && preferences.contains(getString(R.string.my_username))) {
            menu.clear();
            inflater.inflate(R.menu.toolbar_coordinator_menu, menu);
            menu.removeItem(R.id.add_team);
            menu.removeItem(R.id.add_player);
            menu.removeItem(R.id.add_calendar);
            if(longClick) {
                menu.findItem(R.id.search_icon).setVisible(false);
                menu.findItem(R.id.trash_icon).setVisible(true);
                menu.findItem(R.id.edit_icon).setVisible(true);
                menu.findItem(R.id.add_league).setVisible(false);
                menu.findItem(R.id.logout).setVisible(false);
            } else {
                menu.findItem(R.id.search_icon).setVisible(true);
                menu.findItem(R.id.trash_icon).setVisible(false);
                menu.findItem(R.id.edit_icon).setVisible(false);
                menu.findItem(R.id.add_league).setVisible(true);
                menu.findItem(R.id.logout).setVisible(true);
            }
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

                return true;
            case R.id.trash_icon:
                longClick = false;
                api.deleteLiga(leagueClicked.getId(), getApplicationContext(), getSupportFragmentManager());
                invalidateOptionsMenu();
                return true;
            case R.id.edit_icon:
                longClick = false;
                // TODO: implementar editar


                invalidateOptionsMenu();
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
        api.requestLogin(userName,pwd, getApplicationContext(), this, preferences);
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
        if(longClick) {
            longClick = false;
        }
        startActivity(sendLeague);
    }

    /**
     * Implementación de click largo para selección de liga
     * @param league la liga seleccionada
     */
    @Override
    public void onLeagueLongClickListener(League league) {
        longClick = ! longClick;
        leagueClicked = league;
        
        invalidateOptionsMenu();
    }

    /**
     * Añade una liga nueva
     * @param name nombre liga
     * @param uri imagen liga
     */
    @Override
    public void onAddLeagueClickListener(String name, Uri uri) {
        Log.d("NAME: ", name + " URI: " + uri);
        api.postLeague(name, uri, getApplicationContext(), this, getSupportFragmentManager());
    }

    /**
     * permiso concedido para acceder a la cámara
     * @return true
     */
    private Boolean cameraPermissionGranted() {
        return ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * permiso concedido para acceder al almacenamiento
     * @return true
     */
    private Boolean storagePermissionGranted() {
        return ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Array de los permisos a pedir
     */

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    /**
     * Mensaje que informa que la app necesita permiso en caso de que el ususario lo deniegue
     * @param message a mostrar
     * @param okListener el Dialog listener
     */
    private void showMessagePermission(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), okListener)
                .setNegativeButton(getString(R.string.cancel), null)
                .create()
                .show();

    }

    /**
     * Maneja el resultado de los permisos
     * @param requestCode el código de petición
     * @param permissions los permisos pedidos
     * @param grantResults los resultados con los permisos concedidos
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && requestCode == PERMISSION_REQUEST_CODE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, getString(R.string.permission_granted), Toast.LENGTH_SHORT).show();
            }  else if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.permission_granted), Toast.LENGTH_SHORT).show();
            } else {
                showMessagePermission(getString(R.string.need_permission), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermission();
                    }
                });
            }
        }
    }
}
