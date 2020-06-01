package net.jaumebalmes.grincon17.futchamp.repositoryApi;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import net.jaumebalmes.grincon17.futchamp.models.League;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LeagueRepositoryApi {

    // Obtiene la lista de leagues de la BBDD por medio de la API
    @GET("mostrar")
    Call<ArrayList<League>> obtenerListaLeagues();

    @GET("mostrar/nombre/{nombreLeague}")
    Call<League> obtenerLeagueByName(@Path("nombreLeague") String nombreLeague);

    @POST("agregar")
    Call<League> postLeague(@Body League league);

    @DELETE("eliminar/{idleague}")
    Call<League> deleteLeague(@Path("idleague") long id);

}
