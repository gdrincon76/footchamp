package net.jaumebalmes.grincon17.futchamp.conexion;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Firebase {

    /**
     * Autenticación anónima en Firebase para subir imágenes al servidor.
     */
    public void authFirebaseUser() {
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("OK", "signInAnonymously:success");
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("ERROR", "signInAnonymously:failure", task.getException());

                }
            }
        });
    }
}
