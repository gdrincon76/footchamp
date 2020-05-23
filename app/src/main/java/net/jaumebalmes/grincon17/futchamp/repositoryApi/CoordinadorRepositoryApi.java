package net.jaumebalmes.grincon17.futchamp.repositoryApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CoordinadorRepositoryApi {

    // Este metodo se encarga de verificar si un coordinador tiene acceso.
    // http://localhost:8080/api/futchamp/coordinador/verificarAutorizacion?usuario=tomas&clave=x9893913a
    @GET("verificarAutorizacion")
    Call<Boolean> verificarAutorizacion(@Query("usuario")String usuario, @Query("clave") String clave);
}
