package net.jaumebalmes.grincon17.futchamp.repositoryApi;

import net.jaumebalmes.grincon17.futchamp.models.Equipo;
import net.jaumebalmes.grincon17.futchamp.models.Jugador;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EquipoRepositoryApi {

    // Obtiene la lista de equipos de la BBDD por medio de la API
    @GET("mostrar")
    Call<ArrayList<Equipo>> obtenerListaequipos();

    @GET("mostrar/leagues/{nombreLiga}")
    Call<ArrayList<Equipo>> obtenerListaEquiposPorLiga(@Path("nombreLiga") String nombreLiga);

    @GET("mostrar/nombre/{nombreEquipo}")
    Call<Equipo> obtenerEquipoPorNombre(@Path("nombreEquipo") String nombreEquipo);

    @POST("agregar")
    Call<Equipo> postEquipo(@Body Equipo equipo);

}
