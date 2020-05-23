package net.jaumebalmes.grincon17.futchamp.repositoryApi;

import net.jaumebalmes.grincon17.futchamp.models.Jugador;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JugadorRepositoryApi {

    // Obtiene la lista de jugadores de la BBDD por medio de la API
    @GET("mostrar")
    Call<ArrayList<Jugador>> obtenerListaJugadores();


    // Obtiene la lista de jugadores de un mismo equipo por medio del nombre de este
    @GET("mostrar/equipo/{nombreEquipo}")
    Call<ArrayList<Jugador>> obtenerListaJugadoresEquipo(@Path("nombreEquipo") String nombreEquipo);



}
