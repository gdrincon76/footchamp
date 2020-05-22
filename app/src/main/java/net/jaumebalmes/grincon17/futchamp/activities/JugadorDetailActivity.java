package net.jaumebalmes.grincon17.futchamp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
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
import net.jaumebalmes.grincon17.futchamp.fragments.LoginDialogFragment;
import net.jaumebalmes.grincon17.futchamp.interfaces.OnLoginDialogListener;
import net.jaumebalmes.grincon17.futchamp.models.Equipo;
import net.jaumebalmes.grincon17.futchamp.models.Jugador;

/**
 * Esta activity muestra la vista del detalle de un jugador.
 * @author guillermo
 */
public class JugadorDetailActivity extends AppCompatActivity implements OnLoginDialogListener {
    LoginDialogFragment loginDialogFragment;
    Toolbar toolbar;
    Jugador jugador;
    Equipo equipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador_detail);

        Gson gson = new Gson();
        jugador = gson.fromJson(getIntent().getStringExtra(getString(R.string.jugador_json)), Jugador.class);
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

    /**
     * Configuración del toolbar
     */
    private void toolbarConf() {
        toolbar = findViewById(R.id.toolbar_detail_view);
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
}
