package net.jaumebalmes.grincon17.futchamp.conexion;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Esta clase Se encargara de realizar la conexion a la API usando la clase Retrofit.
 */
public class Api {
    private Retrofit retrofit;

    // Builder
    public Api() {
    }

    // Method

    /**
     * Este metodo devolvera un objeto de tipo Retrofit con los datos de conexion a la API usando
     * un enlace.
     *
     * @param urlEnlace Sera la direccion o enlace ejm => http://192.168.0.23:8080/api/futchamp/jugador/
     * @return Devuelve el objeto Retrofit con la conexion.
     */
    public Retrofit getConexion(String urlEnlace) {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlEnlace)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;  // Devuelve un objeto con la conexion a la api
    }

}
