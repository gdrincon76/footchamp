package net.jaumebalmes.grincon17.futchamp.repositoryApi;

import net.jaumebalmes.grincon17.futchamp.models.Calendario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CalendarioReposirotyApi {

    @GET("verificarAutorizacion")
    Call<Calendario> obtenerCalendario(@Query("usuario")String usuario, @Query("clave") String clave);

    @POST("agregar")
    Call<Calendario> postCalendario(@Body Calendario calendario);

}
