package net.jaumebalmes.grincon17.futchamp.repositoryApi;

import net.jaumebalmes.grincon17.futchamp.models.Jugador;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JugadorRepositoryApi {

    // Obtiene la lista de jugadores de la BBDD por medio de la API
    @GET("mostrar")
    Call<ArrayList<Jugador>> obtenerListaJugadores();
}
