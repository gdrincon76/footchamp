package net.jaumebalmes.grincon17.futchamp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import net.jaumebalmes.grincon17.futchamp.R;
import net.jaumebalmes.grincon17.futchamp.conexion.Api;
import net.jaumebalmes.grincon17.futchamp.conexion.Enlace;
import net.jaumebalmes.grincon17.futchamp.models.Equipo;
import net.jaumebalmes.grincon17.futchamp.models.Jugador;
import net.jaumebalmes.grincon17.futchamp.repositoryApi.EquipoRepositoryApi;
import net.jaumebalmes.grincon17.futchamp.repositoryApi.JugadorRepositoryApi;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddJugadorActivity extends AppCompatActivity {

    private static final int GALLERY_CODE = 0;
    private static final int CAMERA_CODE = 1;
    private Uri filePath;
    private ImageView playerImg;
    private EditText nombre;
    private EditText apellido;
    private EditText dorsal;
    private EditText posicion;
    private EditText email;
    private EditText dni;
    private EditText equipoNombre;
    private String imageFilePath;
    private Jugador jugador;
    Enlace enlace;
    Api api;
    Retrofit retrofit;
    Equipo equipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jugador);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.new_player);
        setSupportActionBar(toolbar);
        enlace = new Enlace();
        api = new Api();

        jugador = new Jugador();
        playerImg = findViewById(R.id.imageViewAddPlayer);
        nombre = findViewById(R.id.textEditPlayerName);
        apellido = findViewById(R.id.textEditPlayerSurname);
        dorsal = findViewById(R.id.textEditDorsal);
        posicion = findViewById(R.id.textEditPosicion);
        email = findViewById(R.id.textEditMail);
        dni = findViewById(R.id.textEditDni);
        equipoNombre = findViewById(R.id.textEditPlayerTeamName);

        playerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseCameraOrGallery();
            }
        });
        // TODO: falta crear un botón en la toolbar para subir el jugador
        if(!TextUtils.isEmpty(nombre.getText()) && !TextUtils.isEmpty(apellido.getText()) && !TextUtils.isEmpty(dorsal.getText()) &&
            !TextUtils.isEmpty(posicion.getText()) && !TextUtils.isEmpty(email.getText()) && TextUtils.isEmpty(dni.getText()) &&
            !TextUtils.isEmpty(equipoNombre.getText()) && filePath != null) {
            jugador.setNombre(String.valueOf(nombre.getText()));
            jugador.setApellidos(String.valueOf(apellido.getText()));
            jugador.setPosicion(String.valueOf(posicion.getText()));
            jugador.setDorsal(String.valueOf(dorsal.getText()));
            jugador.setDni(String.valueOf(dni.getText()));
            jugador.setEmail(String.valueOf(email.getText()));
            obtenerEquipo(String.valueOf(equipoNombre.getText()));
        } else {

        }
    }

    private void chooseCameraOrGallery() {
        final String[] chooseDialogItems = getResources().getStringArray(R.array.chooseImageDialog);
        AlertDialog.Builder builder = new AlertDialog.Builder(AddJugadorActivity.this);
        builder.setTitle(R.string.chooseImageDialogTittle);
        builder.setItems(chooseDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (chooseDialogItems[which].equals(getResources().getString(R.string.chooseImageDialogCamera))) {
                    openCamera();
                } else if (chooseDialogItems[which].equals(getResources().getString(R.string.chooseImageDialogImage))) {
                    openGallery();
                } else if (chooseDialogItems[which].equals(getResources().getString(R.string.chooseImageDialogCancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private void openCamera() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(getPackageManager()) != null){

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e("ERROR", Objects.requireNonNull(ex.getMessage()));

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "net.jaumebalmes.grincon17.futchamp.provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                startActivityForResult(pictureIntent,
                        CAMERA_CODE);
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_CODE:
                    filePath = Uri.fromFile(new File(imageFilePath));
                    playerImg.setImageURI(filePath);
                    break;
                case GALLERY_CODE:
                    if (data != null) {
                        filePath = data.getData();
                        playerImg.setImageURI(filePath);
                        playerImg.setTag(String.valueOf(filePath));
                    }
                    break;
            }
        }
    }

    /**
     * Llamada a la Api para añadir una liga
     *
     */
    private void postJugador(final Jugador jugador) {
        if (filePath != null) {

            retrofit = api.getConexion(enlace.getLink(enlace.JUGADOR));
            final JugadorRepositoryApi JugadorRepositoryApi = retrofit.create(JugadorRepositoryApi.class);

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
                            jugador.setImagen(url);
                            Call<Jugador> addNewJugador = JugadorRepositoryApi.postJugador(jugador);
                            addNewJugador.enqueue(new Callback<Jugador>() {
                                @Override
                                public void onResponse(Call<Jugador> call, Response<Jugador> response) {
                                    if(response.isSuccessful()) {
                                        Log.i("JUGADOR", " RESPUESTA: " + response.body());
                                        Toast.makeText(AddJugadorActivity.this, "Jugador añadido", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e("JUGADOR", "ERROR: " + response.errorBody());
                                    }
                                }

                                @Override
                                public void onFailure(Call<Jugador> call, Throwable t) {
                                    Log.e("JUGADOR", " => ERROR  => onFailure: " + t.getMessage());
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


    private void obtenerEquipo(String nombreEquipo) {
        retrofit = api.getConexion(enlace.getLink(enlace.EQUIPO));
        EquipoRepositoryApi equipoRepositoryApi = retrofit.create(EquipoRepositoryApi.class);
        Call<Equipo> equipoAnswerCall = equipoRepositoryApi.obtenerEquipoPorNombre(nombreEquipo);

        equipoAnswerCall.enqueue(new Callback<Equipo>() {
            // Aqui nos indicara si se realiza una conexion, y esta puede tener 2 tipos de ella
            @Override
            public void onResponse(Call<Equipo> call, Response<Equipo> response) {
                if (response.isSuccessful()) {
                    equipo = response.body();
                    jugador.setEquipo(equipo);
                    postJugador(jugador);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error en la descarga.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 500);
                    toast.show();
                    Log.e("TAG", " ERROR AL CARGAR EQUIPOS: onResponse" + response.errorBody());
                }
            }

            // Aqui, se mostrara si la conexion a la API falla.
            @Override
            public void onFailure(Call<Equipo> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error en la conexion a la red.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 500);
                toast.show();
                Log.e("TAG", " => ERROR LISTA EQUIPOS => onFailure: " + t.getMessage());
            }
        });
    }

}
