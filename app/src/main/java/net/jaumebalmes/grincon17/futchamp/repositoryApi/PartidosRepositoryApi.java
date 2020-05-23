package net.jaumebalmes.grincon17.futchamp.repositoryApi;

import net.jaumebalmes.grincon17.futchamp.models.Partido;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PartidosRepositoryApi {

    // Devuelve una lista de partidos locales de un equipo
    @GET("mostrar/locales/{nombreEquipoLocal}")
    Call<ArrayList<Partido>> obtenerListaPartidosLocales(@Path("nombreEquipoLocal") String nombreEquipoLocal);


    // Devuelve una lista de partidos vistantes de un equipo
    @GET("mostrar/visitantes/{nombreEquipoVisitante}")
    Call<ArrayList<Partido>> obtenerListaPartidosVisitantes(@Path("nombreEquipoVisitante") String nombreEquipoVisitante);

}
