package net.jaumebalmes.grincon17.futchamp.repositoryApi;

import net.jaumebalmes.grincon17.futchamp.models.League;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LeagueRepositoryApi {

    // Obtiene la lista de leagues de la BBDD por medio de la API
    @GET("mostrar")
    Call<ArrayList<League>> obtenerListaLeagues();

    @POST("agregar")
    Call<League> postLeague(@Body League league);
}
